import { User } from '../models/user.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { API_CONFIG } from '../models/api.config';

export type EntityResponseType = HttpResponse<User>;
export type PartialUpdateUser = Partial<User> & Pick<User, 'id'>;

@Injectable()
export class UserService {
  
  constructor(private http: HttpClient) {}

  getUser(userUuid: string): Observable<User> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getUserEndPoint}`;
    let params = new HttpParams().set('userUuid', userUuid);
    return this.http.get<User>(reqUrl, { params: params });
  }

  partialUpdate(
    partialUpdateUser: PartialUpdateUser
  ): Observable<EntityResponseType> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getUserEndPoint}`;
    return this.http.patch<User>(reqUrl,
      partialUpdateUser,
      {
        observe: 'response'
      }
    );
  }

}
 