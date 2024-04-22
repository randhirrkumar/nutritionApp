import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  public loginData = {
    username: '',
    password: '',
  }

  constructor(private login: LoginService, private snack: MatSnackBar, private router:Router) {}
  @ViewChild('loginform', { static: false })
  loginform!: NgForm;

  async formSubmit() {
    if (this.loginData.username.trim() == '' || this.loginData.username == null) {
      this.snack.open('Username is required !!', '', {
        duration: 3000,
        verticalPosition: 'top'
      })
      return;
    }
    if (this.loginData.password.trim() == '' || this.loginData.password == null) {
      this.snack.open('Password is required !!', '', {
        duration: 3000,
        verticalPosition: 'top'
      })
      return;
    }

    // request to server to generate token
    this.login.generateToken(this.loginData).subscribe(async (data:any)=>{
      console.log(data);

      //login
      // this.login.loginUser(data.jwtToken);
      await localStorage.setItem("jwtToken",data.token);
      this.login.getCurrentUser().subscribe(data=>{
        this.login.setUser(data);
        this.router.navigate(['home']);
      });
      
          


    },(error)=>{
      console.log("Error");
      Swal.fire('Could not login', 'Invalid Credentials!!', 'error');
    })
  }  
}
