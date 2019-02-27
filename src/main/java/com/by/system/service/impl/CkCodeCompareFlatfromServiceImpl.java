package com.by.system.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.by.core.service.BaseService;
import com.by.system.model.CkCodeCompareFlatfrom;
import com.by.system.mapper.CkCodeCompareFlatfromMapper;
import com.by.system.service.ICkCodeCompareFlatfromService;

@Service
public class CkCodeCompareFlatfromServiceImpl extends BaseService implements ICkCodeCompareFlatfromService{
	@Autowired
	private CkCodeCompareFlatfromMapper ckCodeCompareFlatfromMapper;

	@Override
	public CkCodeCompareFlatfrom get(Map<String,Object> map){
		return ckCodeCompareFlatfromMapper.get(map);
	}
	@Override
	public List<CkCodeCompareFlatfrom> getList(Map<String,Object> map){
		return ckCodeCompareFlatfromMapper.getList(map);
	}
	@Override
	public void save(CkCodeCompareFlatfrom obj){
		//super.daoMysql.save(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.save(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void update(CkCodeCompareFlatfrom obj){
		//super.daoMysql.update(obj);用hibernate对mysql数据源进行操作
		//super.daoOracle.update(obj);用hibernate对oracle数据源进行操作
	}
	@Override
	public void delete(List<String> ids){
		super.daoMysql.delete(ids, CkCodeCompareFlatfrom.class);
	}
}

