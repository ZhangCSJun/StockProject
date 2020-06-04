import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { User } from '../../../entity/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.scss']
})
export class UserprofileComponent implements OnInit {

  public userProfile:User = new User();
  public userId:string;

  constructor(private routerinfo:ActivatedRoute, private router:Router, public reqService:RequestService) {

  }

  ngOnInit(): void {
    console.log(`userId:${localStorage.getItem("userId")}`);
    // get user id from router param
    this.routerinfo.queryParams.subscribe(queryParam=>{
      this.userId = queryParam.userId;
      console.log(`router passed userId:${this.userId}`);
    });
    // if could not get userId form router param, try to get it from local storage
    if(this.userId==undefined || this.userId==""){
      // if could not get userId form local storage, goto system error page
      if(localStorage.get("userId")==undefined || localStorage.get("userId")==""){
        this.router.navigate(['error']);
      }
    }



    // Get user's profile info
    this.reqService.getUserProfile(this.userId).subscribe((response:any)=>{
      console.log(response);
      // if response status is 0k(200), show user profile info
      if(response.status == 200 && response.body.code == "001"){
        this.userProfile.userId = response.body.business.data.id;
        this.userProfile.userName = response.body.business.data.userName;
        this.userProfile.email = response.body.business.data.email;
        this.userProfile.mobileNumber = response.body.business.data.mobileNumber;
        this.userProfile.userType = response.body.business.data.userType
      } 
    }, (errorResponse:any)=>{
      console.log(errorResponse);
      // alert(errorResponse.error.status);
      this.router.navigate(['error']);
    });
  }

}
