import { Injectable } from '@angular/core';

@Injectable()
export class StateService {

  constructor() { }

  setAccessToken(token: string) {
    localStorage.setItem('accessToken', token);
  }

  getAccessToken(): string {
    return localStorage.getItem('accessToken') || '';
  }

}
