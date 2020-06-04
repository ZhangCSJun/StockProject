import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { Editstatus } from '../../../util/editstatus.enum';
import { Router } from "@angular/router";
import * as $ from 'jquery';

@Component({
  selector: 'app-update-iposdetail',
  templateUrl: './update-iposdetail.component.html',
  styleUrls: ['./update-iposdetail.component.scss']
})
export class UpdateIposdetailComponent implements OnInit {
  isDisplay:boolean=false;
  public IPOsPlanned:any;

  public companyNameList:any;

  public dropDownItem:string = "";

  public searchCode:string = "";

  constructor(private reqService:RequestService,  private router:Router ) { }

  ngOnInit(): void {
  }
 
  getCompanyNameList(){
    /* 
      when 2 or more characters for a company name
      or company code, display matching company names
    */ 
    if($('#company').val().length>=2){
      let keyword:string = $('#company').val();
      // get company names by using ajax
      this.reqService.getCompanyNameByKeyword(keyword).subscribe((response:any)=>{
        console.log(response);
        if(response.body.status == 200 && response.body.code === "001"){
          this.companyNameList = response.body.business.data;
          console.log(this.companyNameList);
          this.isDisplay = true;
        } else {
          this.isDisplay = false;
        }
      }, (error:any)=>{
        console.log(error);
      })
    }
  }


  selectCompany(evt:any){
    let attr = evt.target.attributes;
    // display company name on input text
    $('#company').val(attr.companyName.value);
    this.isDisplay = false;
  }

  seletItem(evt:any){
    $('#company').val(evt.target.value);
  }

  getIPOsPlanned(){
      let keyword:string =  $('#company').val();
      this.reqService.getIposDataByCompanyName(keyword).subscribe((response:any)=>{
        console.log(response);
        if(response.body.status == 200 && response.body.code === "001"){
          this.IPOsPlanned = response.body.business.data;
        }
      },(errorResponse)=>{
        console.log(errorResponse);
        if(errorResponse.error.status == 401 && errorResponse.error.message === "invliad token"){
          this.router.navigate(['error']);
        } else {
          this.router.navigate(['error']);
        }
      })
  }

  gotoEdit(){
    this.router.navigate(['/admin/iposplan'],{queryParams: {status: Editstatus.edit}});
  }
}
