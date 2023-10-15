import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { LoginService } from './login.service';
import { StateService } from './state.service';

@Injectable()
export class UserService {

  private apiEndpoint = 'https://localhost:8443/services/user-ms/users/?userUuid=e58ed763-928c-4155-bee9-fdbaaadc15f3';  // Replace with your API endpoint.

  constructor(
    private http: HttpClient, 
    private stateService: StateService
  ) {}

  getUser(userUuid: string): Observable<User> {
    
    const url = this.apiEndpoint;

    const headers = new HttpHeaders({
      'Authorization': this.stateService.getAccessToken()
    });

    return this.http.get<User>(url, { headers: headers });
  }
}
 