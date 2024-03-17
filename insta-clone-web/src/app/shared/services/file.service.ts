import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'; 
import { API_CONFIG } from '../models/api.config';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';

export type EntityResponseType = HttpResponse<GenericResponseApiDto>;


@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {}

  uploadFile(file: File): Observable<any> {  
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.uploadFileEndpoint}`;
    const formData: FormData = new FormData();
    formData.append('file', file); 
    return this.http.post<any>(reqUrl, formData);
  }
  
  downloadFile(fileName: string): Observable<Blob> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.downloadFileEndpoint}`;
    let params = new HttpParams()
      .set('fileName', fileName);

    return this.http.get(reqUrl, { params: params ,responseType: 'blob' });
  }

  deleteFile(fileName: string): Observable<EntityResponseType> {
    const reqUrl = `${API_CONFIG.baseUrl}${API_CONFIG.deleteFileEndpoint}`;
    let params = new HttpParams()
      .set('fileName', fileName);
    return this.http.delete<GenericResponseApiDto>(reqUrl, { params: params, observe: 'response'});
  }


}

export interface GenericResponseApiDto {
  message: string;
}
