import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiEndpoint = 'https://localhost:8443/services/user-ms/users/?userUuid=e58ed763-928c-4155-bee9-fdbaaadc15f3';  // Replace with your API endpoint.

  constructor(private http: HttpClient) {}

  getUser(userUuid: string): Observable<User> {
    //const url = `${this.apiEndpoint}/users/${userUuid}`;
    const url = this.apiEndpoint;
    return this.http.get<User>(url);
  }

}


