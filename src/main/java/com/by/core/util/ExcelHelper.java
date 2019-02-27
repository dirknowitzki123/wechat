package com.by.core.util;

import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;
import com.by.core.startup.ContextInit;
import com.by.frame.intfc.crypto.BASE64Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel工具类
 * 
 * @author HeJian
 *
 */
public class ExcelHelper {
	private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);
  
	/**
	 * excel导出
	 * @param template 模板名称
	 * @param list	导出的数据
	 * @param excelName	导出的excel文件名称
	 * @param sheetName	表格名称
	 * @param columns		列属性
	 * @param columnNames	类属性对应的名称
	 * @param rowStart	写入行开始的行数  , 第一行 为1  即最小的行数是1
	 * @param request
	 * @param response
	 */
	public static <T> void export(String template, List<T> list, String excelName, String sheetName, String[] columns, 
		  String[] columnNames, int rowStart, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream os = null;
		try {
			if (StringUtils.isEmpty(excelName)) { excelName = "exportExcel";  }
			Workbook wb = export(template, list,sheetName, columns, columnNames, rowStart);
			//写到浏览器
			response.reset();
			response.setHeader("Set-Cookie", "fileDownload=true; path=/"); //前端使用jquery.fileDownloa.js下载时,这句代码必不可少!
			response.setContentType("application/octet-stream; charset=utf-8");
			String filename = excelName;
			String agent = request.getHeader("USER-AGENT");  
            if(agent != null && agent.trim().length()>0 && agent.toLowerCase().indexOf("firefox") > 0){
            	filename = "=?UTF-8?B?" + BASE64Util.encode(excelName)  + "?=";    
            }else{
            	filename =  URLEncoder.encode(excelName, "utf-8");
            }
			response.addHeader("Content-Disposition","attachment;filename=" + filename + ".xlsx");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出excel失败!");
			throw new BusinessException(e);
		}finally{
			try {if (os != null) {  os.close(); } } catch (IOException e) { e.printStackTrace(); }
		}
	}

	/**
	 * excel导出
	 * @param template 模板名称
	 * @param list	导出的数据
	 * @param excelName	导出的excel文件名称
	 * @param sheetName	表格名称
	 * @param columns		列属性
	 * @param columnNames	类属性对应的名称
	 * @param rowStart	写入行开始的行数  , 第一行 为1  即最小的行数是1
	 */
	public static <T> Workbook export(String template,
			List<T> list, String sheetName, String[] columns,  String[] columnNames, int rowStart) {
		rowStart = rowStart -1;
		FileInputStream fis = null;
		Workbook wb = null;
		try {
			if (rowStart < 0) { rowStart = 0;  }
			
			if (StringUtils.isEmpty(sheetName)) { sheetName = "Sheet1"; }
			if (StringUtils.isEmpty(template)) { template = "exceltemplate.xlsx"; }
			int maxCount = Integer.parseInt(AppConfig.get("excel.export.maxCount"));
			if (list.size() > maxCount) { throw new BusinessException("导出总条数不能大于" + maxCount); }
			
			fis = new FileInputStream(new File(ContextInit.getCurrentWebApplicationContext().getServletContext().getRealPath("/").replace("\\", "/") + "/template/" + template));
			wb = WorkbookFactory.create(fis);
			wb.setSheetName(0, sheetName);
			Sheet sheet = wb.getSheetAt(0);
			
			XSSFCellStyle cellStyle = (XSSFCellStyle)wb.createCellStyle();
			cellStyle.setBorderBottom((short)1);
			cellStyle.setBorderLeft((short)1);
			cellStyle.setBorderRight((short)1);
			cellStyle.setBorderTop((short)1);
			//写表头
			if ((columnNames != null) && (columnNames.length > 0)) {
				Row row = sheet.createRow(rowStart);
				for (int j = 0; j < columnNames.length; j++){
					Cell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(columnNames[j]);
				}
				rowStart++;
			}
			//写内容
			for (int i = 0; i < list.size(); i++){
				Row row = sheet.createRow(rowStart + i);
				Object obj = list.get(i);
				for (int j = 0; j < columns.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					Object value = null;
					if ((obj instanceof Map)){
						value = ((Map)obj).get(columns[j].trim());
					}else{
						Field field = null;
						try {
							field = obj.getClass().getDeclaredField(columns[j].trim());
						} catch (Exception e) {
							field = obj.getClass().getSuperclass().getDeclaredField(columns[j].trim());
						}
						field.setAccessible(true);
						value = field.get(obj);
						//导出用户的注册来源
						if (columns[j].trim().equals("imei")){
							if (StringUtils.isEmpty((String) value)){
								value = "微信";
							}else{
								value = "APP";
							}
						}
					}
					if(value!=null){
						if(value.getClass().isPrimitive()){
							value = String.valueOf(value);
						}else {
							if(value instanceof Date){
								SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								tempDate.setLenient(false);
								value = tempDate.format(value);
							}
						}
					}
					cell.setCellValue(value==null ? null:value.toString());
				}
			}
			
			for (int j = 0; j < columns.length; j++) {
				sheet.autoSizeColumn(j);
			}
		  
			return wb;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出excel失败!");
			throw new BusinessException(e);
		}finally{
			try {if (fis != null) { fis.close(); } } catch (IOException e) { e.printStackTrace(); }
		}
	}

	
	
	
	
}
