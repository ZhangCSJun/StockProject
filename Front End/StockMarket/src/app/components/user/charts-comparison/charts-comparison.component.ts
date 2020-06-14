import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import * as echarts  from 'echarts';
import * as $ from 'jquery';

@Component({
  selector: 'app-charts-comparison',
  templateUrl: './charts-comparison.component.html',
  styleUrls: ['./charts-comparison.component.scss']
})
export class ChartsComparisonComponent implements OnInit {
  // month array
  month:Array<string>=['01','02','03','04','05','06','07','08','09','10','11','12'];
  monthDict:Object={
    '01':'Jun',
    '02':'Feb',
    '03':'Mar',
    '04':'Apr',
    '05':'May',
    '06':'Jun',
    '07':'Jul',
    '08':'Aug',
    '09':'Sep',
    '10':'Oct',
    '11':'Nov',
    '12':'Dec'
  };
  chartType:any = {type: 'bar'};
  public dispFlg:boolean=false;

  public alertMsg:string="";

  public chartsTypes:Array<any>=[
    {
      value:'0',
      type:'single company over different periods of time',
    }, {
      value:'1',
      type:'different companies over a specific period',
    }];

  public selectedType:string;

  public years:Array<any>=[
    {
      value:'2019',
      year:'2019'
    },{
      value:'2018',
      year:'2018'
    },{
      value:'2017',
      year:'2017'
    }];
    public period:Array<any>=[
      {
        value:'1',
        period:'1Q'
      },{
        value:'2',
        period:'2Q'
      },{
        value:'3',
        period:'3Q'
      },{
        value:'4',
        period:'4Q'
      }];
  public company:any;



  constructor(private reqService:RequestService) { }

  ngOnInit(): void {
    this.selectedType = "0";
    this.reqService.getAllCompanyName().subscribe((response:any)=>{
      console.log(response);
      this.company = response.body.business.data;
      console.log(this.company);
    },(errorResponse)=>{
      console.log(errorResponse);
    })
  }

  selType(evt:any){
    this.selectedType = evt.target.value;
  }

  /**
   * 
   */
  showChart(){
    // check query condition
    if(!this.checkSelectedValue()){
      return;
    }

    // chart option 
    let chartOption3:any = {};
    // Chart query condition
    let queryContion:any;
    // specified company name
    let companyName:Array<any>=[];
    // single company chart
    if(this.selectedType=='0'){
      queryContion={
        'companyCode':[$('#company').val()],
        'year':$('#year').val(),
        'quater':$('#period').val()
      };
      companyName.push($('#company').find('option:selected').text())
      // two companies comparison chart
    } else if(this.selectedType =='1'){
      queryContion={
        'companyCode':$('#mutilcompany').val(),
        'year':$('#year').val(),
        'quater':$('#period').val()
      };
      let selectedItem = $('#mutilcompany').find('option:selected');
      console.log(selectedItem);
      for(let idx=0; idx<selectedItem.length; idx++){
        companyName.push(selectedItem[idx].text);
      }
    }

    console.log(JSON.stringify(queryContion));
    
    let dataSetInfo:any;
    let sources:Array<any>=[];
    let series:Array<any>=[];
    let grids:any;
    let xAxiss:any;
    let yAxiss:any;

    // Get DataSet
    this.reqService.getDataSet(queryContion).subscribe((response:any)=>{
      console.log(response);
      console.log(companyName);
      companyName.forEach( cpname =>{
        if (response.body.business.data.hasOwnProperty(cpname)) {
          let dataSet:any = response.body.business.data[cpname];
          if(dataSet!=null && dataSet.length>0){
            sources.push(dataSet);
          }
        }
      });
      console.log(sources);
      if(sources.length>0){
        if(sources.length == 1){
          grids={};
          xAxiss = {type: 'category'};
          yAxiss = {type: 'value'};
          dataSetInfo={
            source:sources[0]
          }
          series= [
            {type: 'bar'},
            {type: 'bar'},
            {type: 'bar'}
          ]
        } else if(sources.length == 2){
          grids=[{bottom: '60%'},{top: '60%'}];
          xAxiss = [{type: 'category', gridIndex: 0}, {type: 'category', gridIndex: 1}];
          yAxiss = [{type: 'value', gridIndex: 0}, {type: 'value', gridIndex: 1}]
          dataSetInfo=[{
            source:sources[0]
          },{
            source:sources[1]
          }]
          series= [
            {type: 'bar',datasetIndex: 0},
            {type: 'bar',datasetIndex: 0},
            {type: 'bar',datasetIndex: 0},
            {type: 'bar',xAxisIndex: 1, yAxisIndex: 1,datasetIndex: 1},
            {type: 'bar',xAxisIndex: 1, yAxisIndex: 1,datasetIndex: 1},
            {type: 'bar',xAxisIndex: 1, yAxisIndex: 1,datasetIndex: 1}]
        }

        // fill chart dataset
        chartOption3 = {
          legend: {},
          tooltip: {},
          dataset: dataSetInfo,
          grid: grids,
          xAxis: xAxiss,
          yAxis: yAxiss,
          series: series,
        };
        let chart = echarts.init(document.getElementById('echarts'));
        chart.clear();
        chart.setOption(chartOption3);
      } else {
        alert(response.body.message);
      }
    },(errorResponse)=>{
      console.log(errorResponse);
    })
  }

  /**
   * check query condition
   */
  checkSelectedValue():boolean{
    let checkResult = true;

    // unknow select type, do nothing
    if(this.selectedType != "0" && this.selectedType != "1"){
      checkResult = false;
    }
    // verify options
    if(this.selectedType=='1'){
        this.alertMsg = "";
  
        let companyCodeList:Array<string>=$('#mutilcompany').val();
        if(companyCodeList==undefined || companyCodeList.length==0||companyCodeList.length==1){
          this.alertMsg="at least select tow companies";
          checkResult = false;
        }else if(companyCodeList.length>2){
          this.alertMsg="no more than two companies to be selected";
          checkResult = false;
        }
      }

    return checkResult;
  }
}
