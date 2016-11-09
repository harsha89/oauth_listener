package org.wso2.carbon.sample.oauth.operation.event.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.StringUtils;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsageDataBridgeDataPublisher;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsageDataPublisher;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils;
import org.wso2.carbon.identity.oauth.event.OAuthEventListener;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AuthorizeRespDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuthRevocationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuthRevocationResponseDTO;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.model.RefreshTokenValidationDataDO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SampleOAuthOperationListener implements OAuthEventListener {
    public static final Log log = LogFactory.getLog(SampleOAuthOperationListener.class);
    private APIMgtUsageDataPublisher publisher = new APIMgtUsageDataBridgeDataPublisher();

    public void onPreTokenIssue(OAuth2AccessTokenReqDTO tokenReqDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenIssue 1");
    }

    public void onPostTokenIssue(OAuth2AccessTokenReqDTO tokenReqDTO, OAuth2AccessTokenRespDTO tokenRespDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        TokenData tokenData = new TokenData();

        AuthenticatedUser authorizedUser = tokReqMsgCtx.getAuthorizedUser();
        String[] publishingTenantDomains = null;

        if (authorizedUser != null) {
            tokenData.setIsSuccess(true);
            tokenData.setUser(authorizedUser.getUserName());
            tokenData.setUserStoreDomain(authorizedUser.getUserStoreDomain());
            tokenData.setTenantDomain(authorizedUser.getTenantDomain());
            publishingTenantDomains = OAuthDataPublisherUtils.getTenantDomains(tokenReqDTO.getTenantDomain(),
                   authorizedUser.getTenantDomain());
        }

        if (tokReqMsgCtx != null) {
            tokenData.setIssuedTime(tokReqMsgCtx.getAccessTokenIssuedTime());
            tokenData.setRefreshTokenValidityMillis(tokReqMsgCtx.getRefreshTokenvalidityPeriod());
        }
        tokenData.setGrantType(tokenReqDTO.getGrantType());
        tokenData.setClientId(tokenReqDTO.getClientId());
        tokenData.setTokenId(tokenRespDTO.getTokenId());
        StringBuilder unauthzScopes = new StringBuilder();
        List<String> requestedScopes = new LinkedList(Arrays.asList(tokenReqDTO.getScope()));
        List<String> grantedScopes;
        //todo:check why apache commons is not binding
        if (tokenRespDTO.getAuthorizedScopes() != null && StringUtils.isNotBlank(tokenRespDTO.getAuthorizedScopes())) {
            grantedScopes = Arrays.asList(tokenRespDTO.getAuthorizedScopes().split(" "));
        } else {
            grantedScopes = Collections.emptyList();
        }
        requestedScopes.removeAll(grantedScopes);
        for (String scope : requestedScopes) {
            unauthzScopes.append(scope).append(" ");
        }

        // In a case if the authenticated user is not preset, publish event to sp tenant domain
        if (publishingTenantDomains == null) {
            publishingTenantDomains = OAuthDataPublisherUtils.getTenantDomains(tokenReqDTO.getTenantDomain(), null);
        }
        tokenData.setAuthzScopes(tokenRespDTO.getAuthorizedScopes());
        tokenData.setUnAuthzScopes(unauthzScopes.toString());
        tokenData.setAccessTokenValidityMillis(tokenRespDTO.getExpiresInMillis());

        tokenData.addParameter(OAuthDataPublisherConstants.TENANT_ID, publishingTenantDomains);
        tokenData.setErrorMsg(tokenRespDTO.getErrorMsg());
        tokenData.setErrorCode(tokenRespDTO.getErrorCode());
        this.publishTokenIssueEvent(tokenData);

        System.out.println("onPostTokenIssue 2");
    }

    public void onPreTokenIssue(OAuthAuthzReqMessageContext oauthAuthzMsgCtx) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenIssue 3");
    }

    public void onPostTokenIssue(OAuthAuthzReqMessageContext oauthAuthzMsgCtx, AccessTokenDO tokenDO, OAuth2AuthorizeRespDTO respDTO) throws IdentityOAuth2Exception {
        System.out.println("onPostTokenIssue 4");
    }

    public void onPreTokenRenewal(OAuth2AccessTokenReqDTO tokenReqDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenRenewal 5");
    }

    public void onPostTokenRenewal(OAuth2AccessTokenReqDTO tokenReqDTO, OAuth2AccessTokenRespDTO tokenRespDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        System.out.println("onPostTokenRenewal 6");
    }

    public void onPreTokenRevocationByClient(OAuthRevocationRequestDTO revokeRequestDTO) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenRevocationByClient 7");
    }

    public void onPostTokenRevocationByClient(OAuthRevocationRequestDTO revokeRequestDTO, OAuthRevocationResponseDTO revokeResponseDTO, AccessTokenDO accessTokenDO, RefreshTokenValidationDataDO refreshTokenDO) throws IdentityOAuth2Exception {
        System.out.println("onPostTokenRevocationByClient 8");
    }

    public void onPreTokenRevocationByResourceOwner(org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO revokeRequestDTO) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenRevocationByResourceOwner 9");
    }

    public void onPostTokenRevocationByResourceOwner(org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO revokeRequestDTO, org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO revokeRespDTO, AccessTokenDO accessTokenDO) throws IdentityOAuth2Exception {
        System.out.println("onPostTokenRevocationByResourceOwner 10");
    }

    public void onPreTokenValidation(OAuth2TokenValidationRequestDTO validationReqDTO) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenValidation 11");
    }

    public void onPostTokenValidation(OAuth2TokenValidationRequestDTO validationReqDTO, OAuth2TokenValidationResponseDTO validationResponseDTO) throws IdentityOAuth2Exception {
        System.out.println("onPostTokenValidation 12");
    }


    private void publishTokenIssueEvent(TokenData tokenData) {
        Object[] payloadData = new Object[14];
        payloadData[0] = tokenData.getUser();
        payloadData[1] = tokenData.getTenantDomain();
        payloadData[2] = tokenData.getUserStoreDomain();
        payloadData[3] = tokenData.getClientId();
        payloadData[4] = tokenData.getGrantType();
        payloadData[5] = tokenData.getTokenId();
        payloadData[6] = tokenData.getAuthzScopes();
        payloadData[7] = tokenData.getUnAuthzScopes();
        payloadData[8] = tokenData.isSuccess();
        payloadData[9] = tokenData.getErrorCode();
        payloadData[10] = tokenData.getErrorMsg();
        payloadData[11] = tokenData.getAccessTokenValidityMillis();
        payloadData[12] = tokenData.getRefreshTokenValidityMillis();
        payloadData[13] = tokenData.getIssuedTime();

        String[] publishingDomains = (String[]) tokenData.getParameter(OAuthDataPublisherConstants.TENANT_ID);
        if (publishingDomains != null && publishingDomains.length > 0) {
            try {
                FrameworkUtils.startTenantFlow(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME);
                for (String publishingDomain : publishingDomains) {
                    Object[] metadataArray = OAuthDataPublisherUtils.getMetaDataArray(publishingDomain);
                    Event event = new Event(OAuthDataPublisherConstants.TOKEN_ISSUE_EVENT_STREAM_NAME, System
                            .currentTimeMillis(), metadataArray, null, payloadData);
                    EventPublisher.getInstance().publish(event);
                    if (log.isDebugEnabled() && event != null) {
                        log.debug("Sending out event : " + event.toString());
                    }
                }
            } finally {
                FrameworkUtils.endTenantFlow();
            }
        }
    }
}
