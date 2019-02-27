package com.by.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.by.core.exception.BusinessException;
import com.by.core.startup.AppConfig;

public class ExcelUtil {
	static Log logger = LogFactory.getLog(ExcelUtil.class);
	/**
	 * Excel导出
	 * @param template    模板文件名，如test.xlsx，没有则传null。请将模板文件放WebContent/template目录下。
	 * @param list        需要导出的数据，可以是List<实体类>或List<Map<String,Object>>。
	 * @param excelName   导出的Excel文件保存名，无需后缀，如：导出的excel
	 * @param sheetName   导出的Excel文件工作表名
	 * @param columns     需要导出的字段，如{"userName","loginName","birhday"}
	 * @param columnNames 需要导出字段的显示名，如{"用户姓名","登录名","生日"}
	 * @param rowStart    起始行下标，0表示第一行。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T, K, V> void export(String template, List<T> list, String excelName, String sheetName,
			String[] columns, String[] columnNames, int rowStart,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		Workbook wb = null;
		ServletOutputStream os = null;
		try {
			if (rowStart < 0) {
				rowStart = 0;
			}
			if (StringUtils.isEmpty(excelName)) {
				excelName = "exportExcel";
			}
			if (StringUtils.isEmpty(sheetName)) {
				sheetName = "Sheet1";
			}
			if (StringUtils.isEmpty(template)) {
				template = "exceltemplate.xlsx";
			}
			int maxCount=Integer.parseInt(AppConfig.get("excel.export.maxCount"));
			if(list.size()>maxCount){
				throw new BusinessException("导出总条数不能大于"+maxCount);
			}
			
			fis = new FileInputStream(
					new File(request.getSession().getServletContext().getRealPath("/").replace("\\", "/") + "/template/" + template));
			wb = WorkbookFactory.create(fis);
			wb.setSheetName(0, sheetName);
			Sheet sheet = wb.getSheetAt(0);

			XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

			if (columnNames != null && columnNames.length > 0) {
				Row row = sheet.createRow(rowStart);
				for (int j = 0; j < columnNames.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(columnNames[j]);
				}
				rowStart++;
			}

			for (int i = 0; i < list.size(); i++) {
				Row row = sheet.createRow(rowStart + i);
				Object obj = list.get(i);
				for (int j = 0; j < columns.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					if (obj instanceof Map) {
						HashMap<K, V> map = (HashMap<K, V>) obj;
						cell.setCellValue((String) map.get(columns[j]));
					} else {
						Field field = obj.getClass().getDeclaredField(columns[j]);
						field.setAccessible(true);
						cell.setCellValue((String) field.get(obj));
					}
				}
			}
			for (int j = 0; j < columns.length; j++) {
				sheet.autoSizeColumn(j);
			}
			response.reset();
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(excelName, "utf-8") + ".xlsx");
			os = response.getOutputStream();
			wb.write(os);
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (os != null) {
				os.flush();
				os.close();
			}
		}
	}
}
