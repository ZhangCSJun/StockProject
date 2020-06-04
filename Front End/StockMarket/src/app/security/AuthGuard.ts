import { CanActivate} from "@angular/router";

export class AuthGuard implements CanActivate{
    canActivate(){
        // if token is present, user could access any path
        if(localStorage.getItem("token")!='null' && localStorage.getItem("token")!=''){
            return true;
            // if not, forward to error page
        }else{
            // TODO
            // alert("Error Redict")
            return false;
        }
    }
}
