import { Component } from '@angular/core';
import { LoginModel } from '../login/login.model';
import { RegisterModel } from './register.model';

@Component({
  selector: 'insta-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {


  onSubmit(registerModel: RegisterModel) {
    console.log(registerModel);    
    
  }

  navigateToSignUp(){
  }

}
