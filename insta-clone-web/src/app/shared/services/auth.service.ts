import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterModel } from 'src/app/register/register.model';
import { API_CONFIG } from '../models/api.config';
import { User } from '../models/user.model';
import { StateService } from './state.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {

  constructor(
    private http: HttpClient,
    private stateService: StateService,
    private router: Router
  ) {}

  authorizeClient(): void {
    const params = new URLSearchParams({
      response_type: 'code',
      client_id: API_CONFIG.oauthClientId,
      scope: API_CONFIG.oauthScope,
      redirect_uri: API_CONFIG.oauthRedirectUri
    });

    const authorizeUrl = `${API_CONFIG.authServerUrl}${API_CONFIG.oauthAuthorizeEndpoint}?${params.toString()}`;
    window.location.href = authorizeUrl;
  }

  exchangeCodeForToken(code: string): Observable<any> {
    const body = new HttpParams()
      .set('grant_type', 'authorization_code')
      .set('code', code)
      .set('redirect_uri', API_CONFIG.oauthRedirectUri);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa(
        `${API_CONFIG.oauthClientId}:${API_CONFIG.oauthClientSecret}`
      )
    });

    return this.http.post(
      `${API_CONFIG.authServerUrl}${API_CONFIG.tokenEndpoint}`,
      body.toString(),
      { headers }
    );
  }

  //TODO: Replace type any with an Object that represents the actual login response
  login(username: string, password: string): Observable<any> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.tokenEndpoint}?grant_type=client_credentials`;
    const headers = new HttpHeaders({
      //'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa(username + ':' + password) // Encode the username and password.
    });
    return this.http.post(reqUrl, null, { headers: headers });
  }

  register(registerModel: RegisterModel): Observable<User> {
    const reqUrl = `${API_CONFIG.userServiceBaseUrl}${API_CONFIG.createUserUrl}`;
    
    return this.http.post<User>(reqUrl, registerModel);
  }

  logout(): void {
    this.stateService.removeToken();

    //TODO: are there any backend request needed to be done such as deleting
    //token from BE

    this.router.navigate(['/login']);
  }

 
}
