export const API_CONFIG = {

    baseUrl: 'https://localhost:8443',
    authEndpoint: '/oauth2/token',

    //-------------------------- user-ms ------------------------------//
    registerEndpoint: '/services/user-ms/users/',
    getUserEndPoint: '/services/user-ms/users/',
    getSuggestedUsersEndPoint: '/services/user-ms/users/suggested',
    followUsersEndPoint: '/services/user-ms/users/follow',

    //-------------------------- post-ms ------------------------------//
    uploadFileEndpoint: '/services/post-ms/files/upload',
    downloadFileEndpoint: '/services/post-ms/files/download',
    deleteFileEndpoint: '/services/post-ms/files/delete',
    createPostEndoint: '/services/post-ms/posts/',
    getPostsEndpoint: '/services/post-ms/posts/'

};
  