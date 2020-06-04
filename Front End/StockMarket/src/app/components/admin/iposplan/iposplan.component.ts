import { Component, OnInit } from '@angular/core';
import { IPODetails } from '../../../entity/iposdetails';
import { RequestService } from '../../../services/request.service';
import { ActivatedRoute} from '@angular/router';
import * as $ from 'jquery';

@Component({
  selector: 'app-iposplan',
  templateUrl: './iposplan.component.html',
  styleUrls: ['./iposplan.component.scss']
})
export class IposplanComponent implements OnInit {

  public ipoId:string="";
  public iposDetail:IPODetails = new IPODetails();
  // page status
  public pageStatus:number;

  public validMsg:any={
    company:"",
    stkExchange:"",
    price:"",
    totalNum:"",
    openDateTime:"",
  }

  constructor(private routerinfo:ActivatedRoute, private reqServices:RequestService) { }

  ngOnInit(): void {
    // keep page status
    this.pageStatus =this.routerinfo.snapshot.params['status'];
    if(this.pageStatus==1){
      let objMap = this.routerinfo.snapshot.queryParamMap;
      this.ipoId = objMap.get('id');
      this.iposDetail.companyName = objMap.get('companyName');
      this.iposDetail.stockExchange = objMap.get('stockExchange');
      this.iposDetail.pricePerShare = objMap.get('pricePerShare');
      this.iposDetail.totalNumberOfShares = objMap.get('totalNumberOfShares');
      this.iposDetail.openDatetime = objMap.get('openDatetime');
      console.log(this.iposDetail);
    }
  }

  saveIPOSplan(){
    // if not register mode or update mode, do nothing
    if(this.pageStatus!=0 && this.pageStatus!=1){
      return;
    }
    if(this.valid()){
      console.log(this.iposDetail);
      let ipoPlan = {
        "id":this.ipoId,
        "companyName": this.iposDetail.companyName,
        "stockExchange": this.iposDetail.stockExchange,
        "pricePerShare": this.iposDetail.pricePerShare,
        "totalNumberOfShares": this.iposDetail.totalNumberOfShares,
        "openDatetime": this.iposDetail.openDatetime
      }
      console.log(`ipoPlan:${JSON.stringify(ipoPlan)}`);
      // when edit model, then register ipoPlan
      if(this.pageStatus==0){
        this.reqServices.addIPOsPlan(ipoPlan).subscribe((response:any)=>{
          // alert(`code:${response.body.code} msg:${response.body.message}`);
        },(errorResponse)=>{
          // TODO
          console.log(errorResponse);
        });
      } else {
        this.reqServices.updateIPOsPlan(ipoPlan).subscribe((response:any)=>{
          // alert(`code:${response.body.code} msg:${response.body.message}`);
        },(errorResponse)=>{
            // TODO
            console.log(errorResponse);
        });
      }

    }
  }

  valid(){
    let checkResult=true;

    this.validMsg.company = "";
    this.validMsg.stkExchange = "";
    this.validMsg.price = "";
    this.validMsg.totalNum = "";
    this.validMsg.openDateTime = "";

    if(this.isBlank(this.iposDetail.companyName)){
      this.validMsg.company="companyName must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.iposDetail.stockExchange)){
      this.validMsg.stkExchange="stockExchange must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.iposDetail.pricePerShare)){
      this.validMsg.price="pricePerShare must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.iposDetail.totalNumberOfShares)){
      this.validMsg.totalNum="totalNumberOfShares must to be input";
      checkResult = false;
    }
    if(this.isBlank(this.iposDetail.openDatetime)){
      this.validMsg.openDateTime="openDatetime must to be input";
      checkResult = false;
    }
    return checkResult;
  }

  isBlank(data:any):boolean{
    let checkResult = false;
    if(data==undefined||data==""){
      checkResult = true;
    }
    return checkResult;
  }
}
