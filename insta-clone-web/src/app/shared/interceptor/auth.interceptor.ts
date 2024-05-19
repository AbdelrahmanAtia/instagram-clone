import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StateService } from '../services/state.service';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../models/api.config';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private stateService: StateService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("starting interceptor to add token");

        if (!this.isPublicEndPoint(req)) {
            const accessToken = this.stateService.getAccessToken();
            console.log(accessToken)
            if (accessToken) {
                req = req.clone({
                    setHeaders: { 
                        'Authorization': `${accessToken}`
                    }
                });
            }
        }

        return next.handle(req);
    }

    private isPublicEndPoint(req: HttpRequest<any>): boolean {

        // TODO: instead of checking the method is post or not to avoid the conflict
        // between it and between the getUser endpoint , i think 
        // it's better to make the register user url more specific  for example
        // /users/register

        let isRegisterRequest = req.url.endsWith(API_CONFIG.registerEndpoint) && req.method === 'POST';
        return req.url.includes(API_CONFIG.authEndpoint) || isRegisterRequest;
    }
}
