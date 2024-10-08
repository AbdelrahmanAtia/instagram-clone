import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { EMPTY, Observable, of, throwError } from 'rxjs';
import { StateService } from '../services/state.service';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../models/api.config';
import { AuthService } from '../services/auth.service';
import { catchError, tap } from 'rxjs/operators';
import { JwtService } from '../services/jwt.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
      private stateService: StateService,
      private authService: AuthService,
      private jwtService: JwtService,
      private router: Router
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("starting interceptor to add token");

        if (!this.isPublicEndPoint(req)) {
            const accessToken = this.stateService.getAccessToken();

            if(!accessToken){
              this.router.navigate(['/login']);
              return EMPTY;  // Return an empty observable to stop the request
            }

            if(this.jwtService.isTokenExpired(accessToken)){
              console.log('token expired..logging out')
              this.authService.logout();
              return EMPTY;  // Return an empty observable to stop the request
            }
            
            req = req.clone({
                setHeaders: { 
                    'Authorization': `${accessToken}`
                }
            });
            
        }


        return next.handle(req);
        //strangely when the request is not authorized, we got a response 
        // with zero response status code


        /*
        return next.handle(req).pipe(
            tap({
              error: (err: HttpErrorResponse) => {
                console.log(err);
                if (err.status === 401) {
                }
              },
            })
          );
          */
    }

    private isPublicEndPoint(req: HttpRequest<any>): boolean {

        // TODO: instead of checking the method is post or not to avoid the conflict
        // between it and between the getUser endpoint , i think 
        // it's better to make the register user url more specific  for example
        // /users/register

        let isRegisterRequest = req.url.endsWith(API_CONFIG.usersEntityUrl) && req.method === 'POST';
        return req.url.includes(API_CONFIG.authEndpoint) || isRegisterRequest;
    }
}
