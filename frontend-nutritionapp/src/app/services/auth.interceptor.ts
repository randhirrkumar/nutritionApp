import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "./login.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private login:LoginService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add jwt token stored in localStorage in request
        let authReq = req;
        const jwtToken = localStorage.getItem("jwtToken");
        console.log("Inside interceptor");
        console.log(jwtToken);
        if(jwtToken!=null){            
            console.log("Checking token");
            authReq = authReq.clone({
                setHeaders: {
                    'Authorization': jwtToken,
                  }
            });
           console.log(authReq);
           
            
        }
        return next.handle(authReq);
    } 
}

export const authInterceptorProviders = [
    {
        provide: HTTP_INTERCEPTORS,
        useClass:AuthInterceptor,
        multi: true
    },
];