import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public loginStatusSubject = new Subject<boolean>();
  
  constructor(private http: HttpClient) { }

  public getCurrentUser(){
    return this.http.get('http://52.52.255.37:8082/auth/getUsername',{responseType:'text'});
    // return this.http.get('https://7nvnxizds3.execute-api.us-east-1.amazonaws.com/logindeployment/login',{responseType:'text'});
    // return this.http.get('http://localhost:8082/auth/getUsername',{responseType:'text'});
    
  }
  
  
  //generate token
  public generateToken(loginData:any){
    return this.http.post('http://52.52.255.37:8082/auth/login',loginData);
    // return this.http.post('http://localhost:8082/auth/login',loginData);
  }


  //isLogin : user is logged in or not
  public isLoggedIn(){
    const tokenStr = localStorage.getItem('jwtToken');
    return !!tokenStr;
  }

  //logout : remove token from local storage
  public logout(){
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
    return true;
  }

  // getToken
  public getToken(){
    return localStorage.getItem('jwtToken');
  }

  //set userDetail
  public setUser(user:any){
    console.log("user set")
    localStorage.setItem("user",JSON.stringify(user));
  }

  //getUser
  public getUser(){
    const userStr = localStorage.getItem("user");
    console.log(userStr);
    if(userStr!=null){
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }


}
