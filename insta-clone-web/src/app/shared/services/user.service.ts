import { User } from '../models/user.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { API_CONFIG } from '../models/api.config';

export type EntityResponseType = HttpResponse<User>;
export type EntityArrayResponseType = HttpResponse<User[]>;

export type PartialUpdateUser = Partial<User> & Pick<User, 'userUuid'>;

@Injectable() 
export class UserService {
  
  constructor(private http: HttpClient) {}

  getUser(userUuid: string): Observable<User> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.usersEntityUrl}`;
    let params = new HttpParams().set('userUuid', userUuid);
    return this.http.get<User>(reqUrl, { params: params });
  }

  querySuggestedUsers(size: string): Observable<EntityArrayResponseType> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getSuggestedUsersEndPoint}`;
    let params = new HttpParams().set('size', size);
    return this.http.get<User[]>(reqUrl, {
        params: params,
        observe: 'response'
    });
  }

  partialUpdate(
    partialUpdateUser: PartialUpdateUser
  ): Observable<EntityResponseType> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.usersEntityUrl}`;
    return this.http.patch<User>(reqUrl,
      partialUpdateUser,
      {
        observe: 'response'
      }
    );
  }

  followUser(
    followUserRequest: FollowUserRequest
  ): Observable<HttpResponse<GenericResponse>> {
    
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.usersEntityUrl}`;

    return this.http.post<GenericResponse>(reqUrl,
      followUserRequest,
      {
        observe: 'response'
      }
    );
  }  

  getUserFollowers(
    userUuid: string
  ): Observable<EntityArrayResponseType> {
    
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.usersEntityUrl}${userUuid}/followers`;

    let params = new HttpParams()
      .set('page', 0)
      .set('size', 10);

    return this.http.get<User[]>(reqUrl, {
      params: params,
      observe: 'response'
    });

  }  

}
 
export interface FollowUserRequest {
  followedId: string;
}

export interface GenericResponse {
  message: string;
}