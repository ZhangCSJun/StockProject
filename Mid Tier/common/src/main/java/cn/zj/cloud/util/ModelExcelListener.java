package cn.zj.cloud.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

public class ModelExcelListener extends AnalysisEventListener<BaseRowModel>  {
	private List<BaseRowModel> dataList = new ArrayList<BaseRowModel>();

	@Override
	public void invoke(BaseRowModel object, AnalysisContext context) {
		dataList.add(object);
		
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		// TODO Auto-generated method stub
		
	}
	
	
	public List<? extends BaseRowModel> getDataList() {
		return dataList;
	}
	
	public void setDataList(List<BaseRowModel> dataList) {
		this.dataList = dataList;
	}
	
	public void clear() {
		dataList.clear();
	}
}
