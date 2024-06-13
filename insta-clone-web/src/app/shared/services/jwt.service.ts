// jwt.service.ts

import { Injectable } from '@angular/core';
import {jwtDecode} from 'jwt-decode';

interface DecodedToken { 
  exp: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

  decodeToken(token: string): DecodedToken | null {
    try {
      return jwtDecode<DecodedToken>(token);
    } catch (error) {
      console.error('Invalid token:', error);
      return null;
    }
  }

  getTokenExpirationDate(token: string): Date | null {
    const decodedToken = this.decodeToken(token);
    if (!decodedToken || !decodedToken.exp) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decodedToken.exp);
    return date;
  }

  isTokenExpired(token: string): boolean {
    const expirationDate = this.getTokenExpirationDate(token);
    if (!expirationDate) { 
      return true;
    }
    return expirationDate < new Date();
  }
}
