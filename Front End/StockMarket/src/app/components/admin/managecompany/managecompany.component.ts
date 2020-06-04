import { Component, OnInit, Compiler, NgModuleFactory } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { Editstatus } from '../../../util/editstatus.enum';
import { Router } from "@angular/router";
import * as $ from 'jquery';


@Component({
  selector: 'app-managecompany',
  templateUrl: './managecompany.component.html',
  styleUrls: ['./managecompany.component.scss']
})
export class ManagecompanyComponent implements OnInit {
  isDisplay:boolean=false;
  public companyInfo:any;

  public companyNameList:any;

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
      // let keyword:string = $('#company').val();
      // // get company names by using ajax
      // this.reqService.getCompanyNameByKeyword(keyword).subscribe((response)=>{
      //   this.companyNameList = response;
      // })
      // this.isDisplay = true;


      let keyword:string = $('#company').val();
      // get company names by using ajax
      this.reqService.getCompanyNameByKeyword(keyword).subscribe((response:any)=>{
        console.log(response);
        if(response.body.status == 200 && response.body.code === "001"){
          if(response.body.message == "No data"){
            this.isDisplay = false;
          } else {
            this.companyNameList = response.body.business.data;
            console.log(this.companyNameList);
            this.isDisplay = true;
          }
        } else {
          this.isDisplay = false;
        }
      }, (errorResponse:any)=>{
        if(errorResponse.error.status == 401){
          this.router.navigate(['error']);
        } else{
          console.log(errorResponse);
        }
        
      })
      
    }
  }


  selectCompany(evt:any){
    let attr = evt.target.attributes;
    // clear old code
    this.searchCode = "";
    // save new one code
    this.searchCode = attr.companyCode.value;
    // display company name on input text
    $('#company').val(attr.companyName.value);
    this.isDisplay = false;
  }

  seletItem(evt:any){
    $('#company').val(evt.target.value);
  }

  getCompanyInfo(){
    // when user click search button after select item from list,
    // get companylist by specified company code
    if(this.searchCode!==""){
      $('#company').val('');
      this.reqService.getCompanyInfoByCode(this.searchCode).subscribe((response:any)=>{
        console.log(response);
        this.companyInfo = response.body.business.data;
      })
      this.searchCode =""
     
    } else {
      let keyword:string =  $('#company').val();
      // when user input nothing into text, get companylist
      if(keyword == ""){
        this.reqService.getCompaniesInfo().subscribe((response:any)=>{
          console.log(response);
          this.companyInfo = response.body.business.data;
        })
        // when user input keyword into text, get companylist by key word
      } else {
        this.reqService.getCompanyInfoByKeyword(keyword).subscribe((response:any)=>{
          console.log(response);
          this.companyInfo = response.body.business.data;
        })
      }
    }

  }
}
