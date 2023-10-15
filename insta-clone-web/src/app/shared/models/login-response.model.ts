
//TODO: use this model as a response for the login instead of <any> type in the login service method
export interface LoginResponse {
    
    access_token: string;
    scope: string;
    token_type: string;
    expires_in: number;

}