export class IPODetails {
    private _companyName: string;
    private _stockExchange: string;
    private _pricePerShare: string;
    private _totalNumberOfShares: string;
    private _openDatetime: string;
    private _remark: string;

    get companyName():string{
        return this._companyName;
    }
    set companyName(companyName:string){
        this._companyName = companyName;
    }

    get stockExchange():string{
        return this._stockExchange;
    }
    set stockExchange(stockExchange:string){
        this._stockExchange = stockExchange;
    }

    get pricePerShare():string{
        return this._pricePerShare;
    }
    set pricePerShare(pricePerShare:string){
        this._pricePerShare = pricePerShare;
    }

    get totalNumberOfShares():string{
        return this._totalNumberOfShares;
    }
    set totalNumberOfShares(totalNumberOfShares:string){
        this._totalNumberOfShares = totalNumberOfShares;
    }

    get openDatetime():string{
        return this._openDatetime;
    }
    set openDatetime(openDatetime:string){
        this._openDatetime = openDatetime;
    }

    get remark():string{
        return this._remark;
    }
    set remark(remark:string){
        this._remark = remark;
    }
}