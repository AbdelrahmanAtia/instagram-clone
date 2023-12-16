import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { StateService } from './state.service';
import { API_CONFIG } from '../models/api.config';
import { Post } from 'src/app/shared/models/post.model';

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

  sharePost(post: Post): Observable<any> {

    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.createPostEndoint}`;

    const headers = new HttpHeaders({
      'Authorization': this.stateService.getAccessToken(),
    });

    return this.http.post<any>(reqUrl, post, { headers: headers });
  }

  getPosts(userUuid: string, page: number, pageSize: number): Observable<Post[]>  {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getPostsEndpoint}`;

    const headers = new HttpHeaders({
      'Authorization': this.stateService.getAccessToken(),
    });

    let params = new HttpParams()
        .set('userUuid', userUuid)
        .set('page', page.toString())
        .set('pageSize', pageSize.toString());

    return this.http.get<Post[]>(reqUrl, { headers: headers, params: params });
  }


}
