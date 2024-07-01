export const API_CONFIG = {

    baseUrl: 'https://localhost:8443',

    
    authEndpoint: '/oauth2/token',
    usersEntityUrl: '/services/user-ms/users/',

    //-------------------------- user-ms ------------------------------//
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
  