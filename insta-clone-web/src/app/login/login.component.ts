import { Component } from '@angular/core';

@Component({
  selector: 'insta-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  onSubmit(formData: any) {
    console.log(formData);
  }
    
}
