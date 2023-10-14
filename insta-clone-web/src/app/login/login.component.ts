import { Component } from '@angular/core';
import { LoginService } from '../shared/login.service';
import { LoginModel } from './login.model';

@Component({
  selector: 'insta-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  

  constructor(private loginService: LoginService){

  }

  onSubmit(loginModel: LoginModel) {

    this.loginService.login(loginModel.username, loginModel.password).subscribe(response => {
      const accessToken = response.access_token;
      console.log(accessToken);
      this.loginService.setAccessToken(accessToken);
    });
    
  }
    
}
