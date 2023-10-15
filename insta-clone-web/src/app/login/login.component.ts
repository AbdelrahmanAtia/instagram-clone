import { Component } from '@angular/core';
import { LoginService } from '../shared/services/login.service';
import { LoginModel } from './login.model';
import { Router } from '@angular/router';
import { StateService } from '../shared/services/state.service';

@Component({
  selector: 'insta-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  constructor(
    private loginService: LoginService,     
    private router: Router,
    private stateService: StateService
  ){}

  onSubmit(loginModel: LoginModel) {

    this.loginService.login(loginModel.username, loginModel.password).subscribe(response => {
      const accessToken = response.access_token;
      this.stateService.setAccessToken('Bearer ' + accessToken);
      this.router.navigate(['/home']);
    });
    
  }
    
}
