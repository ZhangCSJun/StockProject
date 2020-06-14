import { CanActivate} from "@angular/router";
import { Router } from '@angular/router';
import { Injectable } from "@angular/core";

@Injectable()
export class AuthGuard implements CanActivate{
    constructor(private router: Router){}
    canActivate(){
        // if token is present, user could access any path
        if(localStorage.getItem("token")!='null' && localStorage.getItem("token")!=''){
            return true;
            // if not, forward to error page
        } else {
            // TODO
            alert("Error Redict");
            localStorage.clear();
            this.router.navigate(['error']);
        }
    }
}
