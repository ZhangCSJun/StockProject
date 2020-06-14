import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../services/request.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  // Login username
  public username:string;
  // Login password
  public password:string;
  // Error Message
  public errorMsg:any={
    loginFail:"",
    username:"",
    password:"",
  }
  
  constructor(private reqService:RequestService, private router:Router) { }

  ngOnInit(): void {
    
  }

  login(formValue:any){
    console.log(JSON.stringify(formValue));
    // if form value verify successful
    if(this.validInput(formValue)){
      // call service api to get user info
      this.reqService.doSignIn(formValue).subscribe((response:any)=>{
        // if response status is 200(ok)
        if(response.body.status == 200){
          // if login successful
          if(response.body.code == "001"){
            // clear localstorage
            localStorage.clear();
            // save token
            localStorage.setItem("token",response.headers.get('tkn'));
            // save userId
            localStorage.setItem("userId",response.body.business.id);
            // go to home page by role
            if(response.body.business.role=="1"){
              this.router.navigate(['admin/uploadxls']);
            } else { 
              this.router.navigate(['user/profile']);
            }
          } else {
            this.errorMsg.loginFail = "Incorrect username or password.";
          }
        } 
      }, (errorResponse:any)=>{
        if(errorResponse.status == 400){
          this.errorMsg.loginFail = errorResponse.error.message;
        } else {
          this.errorMsg.loginFail = "Connect to API server fail";
        }
      })
    }
  }

  /** 
   * Verify form input infomation
  */
  public validInput(formValue:any):boolean{
    // Clear verify message
    this.errorMsg.loginFail = "";
    this.errorMsg.username = "";
    this.errorMsg.password = "";

    let checkResult:boolean=true;
    // Check whether the user name has been entered
    if(!formValue.username){
      this.errorMsg.username = "username can't be empty!"
      checkResult = false;
    };
    // Check whether the password has been entered
    if(!formValue.password){
      this.errorMsg.password = "password can't be empty!"
      checkResult = false;
    };
    return checkResult;
  }

}
