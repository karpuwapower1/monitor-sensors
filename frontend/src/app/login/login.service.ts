import {LoginModel} from "./login.model";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";

const TOKEN_KEY = "TOKEN";
const URL = 'http://localhost:8080/auth/login';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT' })
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  login(loginModel: LoginModel): Observable<any> {
      return this.http.post(URL, JSON.stringify(loginModel), httpOptions);
  }

  saveToken(accessToken: string) {
    localStorage.setItem(TOKEN_KEY, accessToken);
  }
}
