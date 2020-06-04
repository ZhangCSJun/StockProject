import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { StockExchange } from '../../../entity/stockexchange';
import { Router, ActivatedRoute, NavigationEnd} from '@angular/router';
import * as $ from 'jquery';

@Component({
  selector: 'app-stockexchangeinfo',
  templateUrl: './stockexchangeinfo.component.html',
  styleUrls: ['./stockexchangeinfo.component.scss']
})
export class StockexchangeinfoComponent implements OnInit {

  public stockEx:StockExchange;

  public pageStatus:string;

  navigationSubscription;

  public validMsg:any={
    id:"",
    abbr:"",
    fullname:"",
    addr:""
  }

  // public stockEx:any;

  constructor(private routerinfo:ActivatedRoute, private reqServices:RequestService, private router:Router) { 
    // this.navigationSubscription = this.router.events.subscribe((event: any) => {
    //   if (event instanceof NavigationEnd) {
    //     this.ngOnInit();
    //   }
    // });
  }

  ngOnInit(): void {

    this.pageStatus =this.routerinfo.snapshot.params['status'];
    if(this.pageStatus=="2"){
      $("input[type='text']").attr("disabled","true");
      let objMap = this.routerinfo.snapshot.queryParamMap;
      this.stockEx = new StockExchange(
        objMap.get('id'), 
        objMap.get('abbrname'), 
        objMap.get('fullname'), 
        objMap.get('brief'),
        objMap.get('contactaddress'),
        objMap.get('remark'), 
        );
      console.log(this.stockEx);
    } else {
      this.stockEx = new StockExchange("","","","","","");
    }

  }

  saveStockExInfo(){
    if(this.valid()){
      console.log(this.stockEx);
      this.reqServices.addNewStockExchange(this.stockEx).subscribe((response:any)=>{
        // alert(`code:${response.body.code} msg:${response.body.message}`);
        this.router.navigate(['/admin/stockExchange']);
      });
    }
  }

  valid(){
    let checkResult=true;

    this.validMsg.abbr = "";
    this.validMsg.fullname = "";
    this.validMsg.addr = "";


    if(this.isBlank(this.stockEx.abbrname)){
      this.validMsg.abbr="abbrname must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.stockEx.fullname)){
      this.validMsg.fullname="fullname must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.stockEx.contactaddress)){
      this.validMsg.addr="Contact Address must to be input";
      checkResult = false;
    }
    return checkResult;
  }

  isBlank(data:any):boolean{
    let checkResult = false;
    if(data==undefined||data==""){
      checkResult = true;
    }
    console.log(`data:${data}`);
    return checkResult;
  }

}
