import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserProfile } from '../UserProfile';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public addUser(user: UserProfile): Observable<Object> {
    return this.http.post(`http://52.52.255.37:8081/userProfile/register`,user,{responseType:'text'});
    // return this.http.post(`https://7nvnxizds3.execute-api.us-east-1.amazonaws.com/registerdeployment/nutritionapp`,user,{responseType:'text'});
    // return this.http.post(`http://localhost:8081/userProfile/register`,user,{responseType:'text'});
  }
}
