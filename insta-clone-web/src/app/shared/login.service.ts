import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl: string = 'https://localhost:8443';
  authEndpoint = this.baseUrl + '/oauth2/token';

  private accessToken: string = "";

  constructor(private http: HttpClient) { 

  }

  //TODO: Replace type any with an Object that represents the actual login response
  login(username: string, password: string): Observable<any> {

    const tokenUrl = `${this.authEndpoint}?grant_type=client_credentials`;
    const headers = new HttpHeaders({
      //'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa(username + ':' + password) // Encode the username and password.
    });

    return this.http.post(tokenUrl, null, { headers: headers });

  }

  setAccessToken(token: string) {
    this.accessToken = token;
  }

  getAccessToken() {
    return this.accessToken;
  }

}
