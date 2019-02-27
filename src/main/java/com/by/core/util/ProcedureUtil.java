package com.by.core.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.by.core.dao.ABaseDao;
import com.by.core.exception.AccessException;

public class ProcedureUtil {
	/**
	 * 调用向下递归存储过程
	 * 查询操作返回id数组，删除操作返回null
	 * @param tableName 表名
	 * @param id        起始节点的id值
	 * @param pidName   该表的父节点字段名(大写)
	 * @param oper      select表示查询 ,delete删除
	 * @param dao
	 * @return String[] select返回节点id数组，包括起始节点id; delete返回null
	 * @example 调用示例：
	 * String[] orgIds=ProcedureUtil.callCHILDREN("A_SYS_ORG", id, "PARENT_ID","select", daoMysql);
	 */
	public static String[] callCHILDREN(String tableName,String id,String pidName,String oper,ABaseDao dao){
		Connection con = null;
		try{
			con=SessionFactoryUtils.getDataSource(dao.getSessionFactory()).getConnection();
			CallableStatement cst=con.prepareCall("{call proc_a_sys_org_children(?,?,?,?,?,?)}");
			cst.setString(1, tableName);
			cst.setString(2, id);
			cst.setString(3, pidName);
			cst.setString(4, oper);
			cst.registerOutParameter(5, java.sql.Types.VARCHAR);
			cst.registerOutParameter(6, java.sql.Types.VARCHAR);
			cst.execute();
			if(!"1".equals(cst.getString(5))){
				throw new AccessException("存储过程proc_a_sys_org_children执行失败","130002");
			}
			if("select".equals(oper)){
				String out=cst.getString(6);
				return out.split(",");
			}
			return null;
		}catch(SQLException e){
			throw new AccessException(e.getMessage(),"130002");
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
