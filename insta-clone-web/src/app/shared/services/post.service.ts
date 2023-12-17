import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs'; 
import { API_CONFIG } from '../models/api.config';
import { Post } from 'src/app/shared/models/post.model';

@Injectable()
export class PostService {

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<any> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.uploadFileEndpoint}`;
    const formData: FormData = new FormData();
    formData.append('file', file); 
    return this.http.post<any>(reqUrl, formData);
  }

  /*
  downloadFile(fileName: string): Observable<Blob> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.downloadFileEndpoint}/${fileName}`;
    return this.http.get(reqUrl, { responseType: 'blob' });
  }
  */

  sharePost(post: Post): Observable<any> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.createPostEndoint}`;
    return this.http.post<any>(reqUrl, post);
  }

  getPosts(userUuid: string, page: number, pageSize: number): Observable<Post[]>  {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.getPostsEndpoint}`;
    let params = new HttpParams()
        .set('userUuid', userUuid)
        .set('page', page.toString())
        .set('pageSize', pageSize.toString());

    return this.http.get<Post[]>(reqUrl, { params: params });
  }

}
