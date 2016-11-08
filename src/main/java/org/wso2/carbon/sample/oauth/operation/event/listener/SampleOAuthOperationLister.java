package org.wso2.carbon.sample.oauth.operation.event.listener;

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

public class SampleOAuthOperationLister implements OAuthEventListener {

    public void onPreTokenIssue(OAuth2AccessTokenReqDTO tokenReqDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        System.out.println("onPreTokenIssue 1");
    }

    public void onPostTokenIssue(OAuth2AccessTokenReqDTO tokenReqDTO, OAuth2AccessTokenRespDTO tokenRespDTO, OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
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
}
