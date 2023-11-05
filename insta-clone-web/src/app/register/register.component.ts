import { Component } from '@angular/core';
import { LoginModel } from '../login/login.model';
import { RegisterModel } from './register.model';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'insta-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(
    private authService: AuthService,
    private router: Router
  ){}

  onSubmit(registerModel: RegisterModel) {
    //TODO: handle cases when an error occurs
    this.authService.register(registerModel).subscribe(res => {
      this.navigateToLogin();
    });
  }

  navigateToLogin(){
    this.router.navigate(['/login']); 
  }

}
