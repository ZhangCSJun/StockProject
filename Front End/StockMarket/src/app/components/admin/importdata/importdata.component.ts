import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { StockPrice } from '../../../entity/stockprice';
import { Router } from '@angular/router';
import * as $ from 'jquery';

@Component({
  selector: 'app-importdata',
  templateUrl: './importdata.component.html',
  styleUrls: ['./importdata.component.scss']
})
export class ImportdataComponent implements OnInit {

  // file upload target
  private uploadTarget:any;
  // excel data
  excelData:Array<any>;
  // transfer data
  public transferData:Array<StockPrice>=[];
  // excel data checkResult msg
  public xlsCheckResultMsg:Array<any>=[];

  public uploadSuccessMessage:string="";
  public uploadFailMessage:string="";

  public uploadResult:any={
    company:"",
    stockExchange:"",
    num:"",
    datefrom:"",
    dateto:""
  }

  constructor( private router:Router, private reqService:RequestService) { }

  ngOnInit(): void {
  }

  doSelFile(evt:any){
    console.log(evt.target.value);
    $('#location').val(evt.target.value);
    this.uploadTarget = evt.target;
  }


  importExcel(){
    if(this.uploadTarget==null){
      return;
    }
    this.clearUploadReport();
    $('#location').val("");
    let fileList = this.uploadTarget.files;
    console.log(fileList);
    if(fileList.length > 0){
      let formdata:FormData = new FormData();
      formdata.append('uploadFile', fileList[0]);
      this.reqService.uploadCompayStockPriceData(formdata).subscribe((response:any)=>{
      console.log(response);
      if(response.body.status === 200 && response.body.code === "001"){
        this.uploadResult.company = response.body.business.data.company;
        this.uploadResult.stockExchange = response.body.business.data.stockExchange;
        this.uploadResult.num = response.body.business.data.totalnum;
        this.uploadResult.datefrom = response.body.business.data.datefrom;
        this.uploadResult.dateto = response.body.business.data.dateto;
        this.uploadSuccessMessage = response.body.message;
        // alert(response.body.message)
      } else {
        this.uploadFailMessage = response.body.message;
        // alert(response.body.message)
      }

      },(errorResponse)=>{
      console.log(errorResponse);
      this.uploadFailMessage = errorResponse.error.message;
      // alert(errorResponse.error.message)
      })
    }
    $('#i-file').val('');
    setTimeout(() => {this.clearMessage();},2000);
  }

  downloadTemplate(){
    this.reqService.downloadTemplate().subscribe((response:any)=>{
      console.log(response);
      var link = document.createElement('a');
      var blob = new Blob([response.body], {type: 'application/vnd.ms-excel'});
      var objectUrl = URL.createObjectURL(blob);
      link.setAttribute('href', objectUrl);
      link.setAttribute('download', response.headers.get('content-disposition').split('fileName=')[1]);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

    },(error:any)=>{
      console.log(error);
      this.router.navigate(['error']);
    });
  }

  private clearUploadReport(){
    this.uploadResult.company = "";
    this.uploadResult.stockExchange = "";
    this.uploadResult.num = "";
    this.uploadResult.datefrom = "";
    this.uploadResult.dateto = "";
    this.uploadSuccessMessage = "";
  }

  private clearMessage(){
    this.uploadSuccessMessage = "";
    this.uploadFailMessage = "";
  }
}
