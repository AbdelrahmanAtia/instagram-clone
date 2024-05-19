export interface User {
    id: string;
    name: string;
    username: string;
    email: string;
    profileImageName: string;
    profileImage: string; //base64 string
    password: string;  //TODO: Ideally, you shouldn't be retrieving passwords. it shall be not sent from the backend
    postsCount: number;
    followersCount: number;
    followingCount: number;
    userUuid: string;
    serviceAddress: string;
    followedByCurrentUser: boolean;
}