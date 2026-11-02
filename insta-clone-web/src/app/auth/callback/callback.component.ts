import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';


@Component({
  selector: 'insta-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  constructor(private route: ActivatedRoute, private authService: AuthService) {}

  ngOnInit(): void {
    const code = this.route.snapshot.queryParamMap.get('code');
    console.log('Authorization Code:', code);
    debugger;
    if (code) {
      this.exchangeCodeForToken(code);
    }
  }

  private exchangeCodeForToken(code: string): void {
    this.authService.exchangeCodeForToken(code)
      .subscribe({
        next: token => {
          console.log(token);
          localStorage.setItem('access_token', token.accessToken);
        },
        error: err => {
          console.error(err);
        }
      });
  }

}