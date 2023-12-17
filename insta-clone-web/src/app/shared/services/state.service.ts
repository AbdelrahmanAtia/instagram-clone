import { Injectable } from '@angular/core';

@Injectable()
export class StateService {

  ACCESS_TOKE: string = "accessToken";
  USER_UUID: string = "userUuid";

  constructor() { }

  setAccessToken(token: string) {
    localStorage.setItem(this.ACCESS_TOKE, token);
  }

  getAccessToken(): string {
    return localStorage.getItem(this.ACCESS_TOKE) || '';
  }

  setCurrentUserUuid(userUuid: string){
    localStorage.setItem(this.USER_UUID, userUuid);
  }

  getCurrentUserUuid(): string {
    return localStorage.getItem(this.USER_UUID) || '';
  }

  printLocalStorageInfo(){
    console.log(this.getCurrentUserUuid());
    console.log(this.getAccessToken());
  }

}
