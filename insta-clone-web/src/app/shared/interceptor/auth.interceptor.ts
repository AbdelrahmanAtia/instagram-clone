import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StateService } from '../services/state.service';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../models/api.config';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private stateService: StateService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const isAuthRequest = this.isAuthRequest(req.url);
        
        if (!isAuthRequest) {
            const accessToken = this.stateService.getAccessToken();
            if (accessToken) {
                req = req.clone({
                    setHeaders: { 
                        'Authorization': accessToken
                    }
                });
            }
        }

        return next.handle(req);
    }

    private isAuthRequest(url: string): boolean {
        return url.includes(API_CONFIG.authEndpoint) || url.includes(API_CONFIG.registerEndpoint);
    }
}
