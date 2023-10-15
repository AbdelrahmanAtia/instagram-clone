export interface User {
    name: string;
    username: string;
    email: string;
    password: string;  //TODO: Ideally, you shouldn't be retrieving passwords. it shall be not sent from the backend
    postsCount: number;
    followersCount: number;
    followingCount: number;
    userUuid: string;
    serviceAddress: string;
}