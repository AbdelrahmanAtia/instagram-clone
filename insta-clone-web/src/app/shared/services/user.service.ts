import { User } from '../models/user.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { API_CONFIG } from '../models/api.config';
@Injectable()
export class UserService {

  constructor(private http: HttpClient) {}

  getUser(userUuid: string): Observable<User> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getUserEndPoint}`;
    let params = new HttpParams().set('userUuid', userUuid);
    return this.http.get<User>(reqUrl, { params: params });
  }

}
 