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
      'Authorization': 'Bearer eyJraWQiOiJjZmM4YWRmOC04YmEyLTQyYTMtYjkyNy0xNTlhNGM5ZTAzMzUiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3cml0ZXIiLCJhdWQiOiJ3cml0ZXIiLCJuYmYiOjE2OTYxMjc4NzUsInNjb3BlIjpbIm9wZW5pZCIsInBvc3Q6d3JpdGUiLCJ1c2VyOnJlYWQiLCJ1c2VyOndyaXRlIiwicG9zdDpyZWFkIl0sImlzcyI6Imh0dHA6XC9cL2F1dGgtc2VydmVyOjk5OTkiLCJleHAiOjE2OTYxMzE0NzUsImlhdCI6MTY5NjEyNzg3NSwianRpIjoiYmI4ZGM0ZDUtNzBjMC00Njk0LWE5ODctMzQzYmViMjgwOTYzIn0.KE6tptSewA45alw-C4V-FZ4eM6F6nK4lkw19kJwy5t7_EaQsmFbivPtAHGY9AWod6t5QRIiGwhcqqKyaZsaUiaOMSMT4Qk3xZCp2wU_7Bg2LlGq2BqN5Yd21tUul-BNbSTUsxotOkcX6t0u5QyqKwTUlIdesI4H8E0cUQ7mZ1FUJV-FrwI1wKMommXeuREBBTFmcEgOHJPEaauq9v7Gbt8PJCj2zchbxEGC9atjhDVb4Jfil3JRjfWVkbpZjqBcEvpe0__xZY6LUJfcMLZwYgH98oiDGVIDu1xHJ7ktEZ_eIQDL6E_zke3gs76ydpJGp-Cuew34foND6fKqDausb8g'
    });

    return this.http.get<User>(url, { headers: headers });
  }
}
