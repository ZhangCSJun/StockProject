import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { Router, NavigationEnd } from "@angular/router";
import { StockExchange } from '../../../entity/stockexchange';

@Component({
  selector: 'app-managestockinfo',
  templateUrl: './managestockinfo.component.html',
  styleUrls: ['./managestockinfo.component.scss']
})
export class ManagestockinfoComponent implements OnInit {

  public stockExchangeInfo:any;
  navigationSubscription;
  constructor(private reqServices:RequestService, private router:Router) { 
        this.navigationSubscription = this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        this.ngOnInit();
      }
    });
  }

  ngOnInit(): void {
    this.reqServices.getStockExchangeInfo().subscribe((response:any)=>{
      console.log(response);
      if(response.body.status==200 && response.body.code=="001")
        this.stockExchangeInfo = response.body.business.data;
        console.log(this.stockExchangeInfo);
        this.stockExchangeInfo.forEach(element => {
          element.checked = false;
      });
    },(errorReponse:any)=>{
      console.log(errorReponse);
    })
  }

  delStockEx(){
    let delStockExList:Array<any>=[];
    // if there is no data in list, do nothing
    if(this.stockExchangeInfo.length == 0){
      return;
    }

    this.stockExchangeInfo.forEach(element => {
      if(element.checked == true){
        let stockEx:any = {};
        stockEx.id = element.id;
        delStockExList.push(stockEx);
      }
    });

    // alert(JSON.stringify(delStockExList));
    this.reqServices.delStockExchange(delStockExList).subscribe((response:any)=>{
      // alert(`msg:${response.body.message}`);
      this.router.navigate(['/admin/stockExchange']);
    }, (errorResponse)=>{
      console.log(errorResponse);
    });
  }


}
