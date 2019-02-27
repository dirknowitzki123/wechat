package com.by.bussiness.wechat.mapper;
import java.util.List;
import java.util.Map;
import com.by.core.mybatis.MyBatisRepositoryMysql;
import com.by.core.mybatis.MyBatisRepositoryOracle;
import com.by.core.mapper.BaseMapper;
import com.by.bussiness.wechat.model.TCustLoginInfo;

@MyBatisRepositoryMysql
public interface TCustLoginInfoMapper extends BaseMapper<TCustLoginInfo>{
}

