import { UserService } from "../services/user.service";

export const API_CONFIG = {

    baseUrl: 'https://localhost:8443',
    userServiceBaseUrl: 'http://localhost:7001', // TODO: profile-specific
    authServerUrl: 'http://localhost:9999',

    // OAuth2
    oauthClientId: 'angular_app',
    oauthClientSecret: 'angular_app',
    oauthScope: 'openid',
    oauthRedirectUri: 'http://localhost:4200/auth/callback',
    oauthAuthorizeEndpoint: '/oauth2/authorize',
    tokenEndpoint: '/oauth2/token',

    usersEntityUrl: '/services/user-ms/users/',

    //-------------------------- user-ms ------------------------------//
    createUserUrl: '/api/users/',
    getSuggestedUsersEndPoint: '/services/user-ms/users/suggested',
    followUsersEndPoint: '/services/user-ms/users/follow',
    getUserFollowersEndPoint: '/services/user-ms/users/{}',

    //-------------------------- post-ms ------------------------------//
    uploadFileEndpoint: '/services/post-ms/files/upload',
    downloadFileEndpoint: '/services/post-ms/files/download',
    deleteFileEndpoint: '/services/post-ms/files/delete',
    createPostEndoint: '/services/post-ms/posts/',
    getPostsEndpoint: '/services/post-ms/posts/'
};