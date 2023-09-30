import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiEndpoint = 'https://localhost:8443/services/user-ms/users/?userUuid=e58ed763-928c-4155-bee9-fdbaaadc15f3';  // Replace with your API endpoint.

  private authEndpoint = 'https://localhost:8443/oauth2/token'; // Replace with your actual protocol, host, and port.
  
  private token: string = "";

  constructor(private http: HttpClient) {
    this.getToken().subscribe(response => {
      console.log("======== authorization response =============");
      console.log(response);
      console.log("=============================================");
      this.token = response.access_token;
    });
  }

  private getToken(): Observable<any> {
    const tokenUrl = `${this.authEndpoint}?grant_type=client_credentials`;

    const headers = new HttpHeaders({
      //'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa('writer:secret') // Encode the username and password.
    });

    return this.http.post(tokenUrl, null, { headers: headers });
}

  getUser(userUuid: string): Observable<User> {
    const url = this.apiEndpoint;
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.token
    });

    return this.http.get<User>(url, { headers: headers });
  }
}
