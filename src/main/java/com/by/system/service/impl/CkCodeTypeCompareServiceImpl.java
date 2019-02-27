package com.by.system.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.by.core.service.BaseService;
import com.by.system.model.CkCodeTypeCompare;
import com.by.system.mapper.CkCodeTypeCompareMapper;
import com.by.system.service.ICkCodeTypeCompareService;

@Service
public class CkCodeTypeCompareServiceImpl extends BaseService implements ICkCodeTypeCompareService{
	@Autowired
	private CkCodeTypeCompareMapper ckCodeTypeCompareMapper;

	@Override
	public CkCodeTypeCompare get(Map<String,Object> map){
		return ckCodeTypeCompareMapper.get(map);
	}
	@Override
	public List<CkCodeTypeCompare> getList(Map<String,Object> map){
		return ckCodeTypeCompareMapper.getList(map);
	}
	@Override
	public void save(CkCodeTypeCompare obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(CkCodeTypeCompare obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, CkCodeTypeCompare.class);
	}
}

