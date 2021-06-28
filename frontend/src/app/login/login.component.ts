import { Component, OnInit } from '@angular/core';
import {LoginModel} from "./login.model";
import {LoginService} from "./login.service";

@Component({
  selector: 'login-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginModel: LoginModel = {
    login: "",
    password: ""
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    console.log("onInit");
    if (this.loginService.getToken()) {
      console.log(this.loginService.getToken());
      this.isLoggedIn = true;
      // this.roles = this.loginService.getUser().roles;
    }
  }

  onSubmit(): void {
    this.loginService.login(this.loginModel).subscribe(
      data => {
        this.loginService.saveToken(data.accessToken);
        // this.loginService.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        // this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
      },
      err => {
        console.log(err)
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

}
