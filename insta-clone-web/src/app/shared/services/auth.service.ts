import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterModel } from 'src/app/register/register.model';
import { API_CONFIG } from '../models/api.config';

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {}

  //TODO: Replace type any with an Object that represents the actual login response
  login(username: string, password: string): Observable<any> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.authEndpoint}?grant_type=client_credentials`;
    const headers = new HttpHeaders({
      //'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa(username + ':' + password) // Encode the username and password.
    });
    return this.http.post(reqUrl, null, { headers: headers });
  }

  register(registerModel: RegisterModel): Observable<any> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.registerEndpoint}`;
    return this.http.post(reqUrl, registerModel);
  }

 
}