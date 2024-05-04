import { Component } from '@angular/core';
import { LoginModel } from './login.model';
import { Router } from '@angular/router';
import { StateService } from '../shared/services/state.service';
import { AuthService } from '../shared/services/auth.service';
import * as jwt_decode from "jwt-decode";


@Component({
  selector: 'insta-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  constructor(
    private authService: AuthService,     
    private router: Router,
    private stateService: StateService
  ){}

  onSubmit(loginModel: LoginModel) {

    //TODO: handle cases when an error occurs
    this.authService.login(loginModel.username, loginModel.password).subscribe(response => {
      const accessToken = response.access_token;
      const decodedToken: any = jwt_decode.jwtDecode(accessToken);

      this.stateService.setAccessToken('Bearer ' + accessToken);
      this.stateService.setCurrentUserUuid(decodedToken.user_uuid);
      
      this.router.navigate(['/home']);
    });
  }

  navigateToSignUp(){
    this.router.navigate(['/register']); 
  }
    
}
