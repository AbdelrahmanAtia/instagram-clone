import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { StateService } from './state.service';
import { API_CONFIG } from '../models/api.config';

@Injectable()
export class PostService {

  constructor(
    private http: HttpClient, 
    private stateService: StateService
  ) {}

  uploadFile(file: File): Observable<any> {

    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.uploadFileEndpoint}`;

    const formData: FormData = new FormData();
    formData.append('file', file); 

    const headers = new HttpHeaders({
      'Authorization': this.stateService.getAccessToken(),
    });

    return this.http.post<any>(reqUrl, formData, { headers: headers });
  }
}
