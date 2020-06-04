import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';


const httpOptions:any={
  headers: new HttpHeaders({'Content-Type':'application/json', 'tkn':`${localStorage.getItem('token')}`}),
  observe: 'response' // able to get full response info include header
};
const downloadOptions:any={
  headers: new HttpHeaders({'Content-Type':'application/vnd.ms-excel', 'tkn':`${localStorage.getItem('token')}`}),
  responseType: 'blob',
  observe: 'response' // able to get full response info include header
};
const uploadOptions:any={
  headers: new HttpHeaders({'tkn':`${localStorage.getItem('token')}`}),
  observe: 'response' // able to get full response info include header
};


@Injectable({
  providedIn: 'root'
})
export class RequestService {

  // API Server URL
  private baseUrl:string = environment.baseUrl;

  public data:Array<any>;

  constructor(private http:HttpClient) { }

  // User login
  doSignIn(form:any){
    return this.http.post(`${this.baseUrl}/login`, JSON.stringify(form), httpOptions);
  }

  // User Register
  doSignUp(form:any){
    console.log(JSON.stringify(form));
    return this.http.post(`${this.baseUrl}/user`, JSON.stringify(form), httpOptions);
  }

  // User Password update
  doUpdatePwd(pwdInfo){
    return this.http.put(`${this.baseUrl}/user`, JSON.stringify(pwdInfo), httpOptions);
  }

  // Get user info by id
  getUserProfile(id:string){
    return this.http.get(`${this.baseUrl}/user/${id}`, httpOptions);
  }

  // Get Company Name List which matched keyword
  getCompanyNameByKeyword(str:string){
    return this.http.get(`${this.baseUrl}/company/ajax/${str}`, httpOptions);
  }

  // Get all Company Info
  getCompaniesInfo(){
    return this.http.get(`${this.baseUrl}/company`,  httpOptions);
  }

  // Get a Company Info which matched code
  getCompanyInfoByCode(code:string){
    return this.http.get(`${this.baseUrl}/company/${code}`, httpOptions);
  }

  // Get all Company Info which matched keyword
  getCompanyInfoByKeyword(keyword:string){
    return this.http.get(`${this.baseUrl}/company/ajax/${keyword}`, httpOptions);
  }


  /*
  * url path('/admin//ipodetail')
  * Get IPOs Data which matched company name
  */
  getIposDataByCompanyName(companyname:string){
    return this.http.get(`${this.baseUrl}/admin/ipodetail/${companyname}`, httpOptions);
  }

  /*
  * url path('/admin//ipodetail')
  * Get all IPOs data
  */
  getIposData(){
    return this.http.get(`${this.baseUrl}/admin/ipodetail`, httpOptions);
  }

  /*
  * url path('/admin//ipodetail')
  * register iposplan data
  */
 addIPOsPlan(data:any){
  return this.http.post(`${this.baseUrl}/admin/ipodetail`, JSON.stringify(data), httpOptions);
}

  /*
  * url path('/admin//ipodetail')
  * update iposplan data
  */
  updateIPOsPlan(data:any){
    return this.http.put(`${this.baseUrl}/admin/ipodetail`, JSON.stringify(data), httpOptions);
  }


  // getIPOsPlannedByKeyword(keyword:string){
  //   return this.http.get(`${this.baseUrl}/getIPOsPlannedByKeyword`, {params:{keyword:`${keyword}`}});
  // }

  // getIPOsPlannedByCode(code:string){
  //   return this.http.get(`${this.baseUrl}/getIPOsPlannedByCode`, {params:{companycode:`${code}`}});
  // }


  getStockPriceData(){
    return this.http.get(`${this.baseUrl}/stockprice`);
  }

//   /*
//   * url path('/getCompanyNameByCode')
//   * get company name from server
//   * condition is company code
//   */
//  getCompanyNameByCode(companyCode){
//   return this.http.get(`${this.baseUrl}/getCompanyNameByCode`, {params:{code:`${companyCode}`}});
// }

  /*
  * url path('/uploadstockprice')
  * upload Stock Price Info to server
  */
  uploadCompayStockPriceData(data:any){
    return this.http.post(`${this.baseUrl}/admin/upload`, data, uploadOptions);
  }


  /*
  * url path('/stockexchange')
  * get Stock Exchange Info from server
  */
  getStockExchangeInfo(){
    return this.http.get(`${this.baseUrl}/admin/stockexchange`, httpOptions);
  }

  /*
  * url path('/addstockexchange')
  * post Stock Exchange List which to be added to server
  */
  addNewStockExchange(data:any){
    return this.http.post(`${this.baseUrl}/admin/stockexchange`, JSON.stringify(data), httpOptions);
  }

  /*
  * url path('/admin/stockexchange/del')
  * post Stock Exchange List which to be deleted to server
  */
  delStockExchange(data:any){
    return this.http.post(`${this.baseUrl}/admin/stockexchange/del`, JSON.stringify(data), httpOptions);
  }



  getDataSet(data:any){
    return this.http.post(`${this.baseUrl}/admin/stockprice`, JSON.stringify(data), httpOptions);
  }

    /*
  * url path('/admin/template/download')
  * download template excel
  */
  downloadTemplate(){
    return this.http.get(`${this.baseUrl}/admin/template/download`, downloadOptions);
  }
}
