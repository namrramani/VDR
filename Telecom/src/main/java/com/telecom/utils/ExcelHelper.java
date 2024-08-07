package com.telecom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.Payment;

@Service
public class ExcelHelper {
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADEROfPayments = { "Id", "Customer Name", "Amount", "Bank", "UTR No.", "City", "Date", "Approved By", "Comments" };
	  static String[] LedgerTransaction = { "Id", "Customer Name", "Email", "FirmName", "Credit", "Debit", "Date Time","Comments" };
	  static String SHEET = "Tutorials";

	  public static ByteArrayInputStream paymentsToExcel(List<Payment> payments) {

	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);

	      // Header
	      Row headerRow = sheet.createRow(0);

	      for (int col = 0; col < HEADEROfPayments.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADEROfPayments[col]);
	      }

	      int rowIdx = 1;
	      for (Payment payment : payments) {
	        Row row = sheet.createRow(rowIdx++);
	        System.out.println("approved by:"+payment.getDate());

	        row.createCell(0).setCellValue(payment.getID());
	        row.createCell(1).setCellValue(payment.getCustomer().getFirmName());
	        row.createCell(2).setCellValue(payment.getAmount());
	        row.createCell(3).setCellValue(payment.getBank());
	        row.createCell(4).setCellValue(payment.getUtrno());
	        row.createCell(5).setCellValue(payment.getCity());
	        row.createCell(6).setCellValue(payment.getDate()+"");
	        row.createCell(7).setCellValue(payment.getApprovedBy());
	        row.createCell(8).setCellValue(payment.getRemarks());
	      }

	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
	  
	  public static ByteArrayInputStream ledgerToExcel(List<LedgerTransaction> ledgerTransactions) {

		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
		      Sheet sheet = workbook.createSheet(SHEET);

		      // Header
		      Row headerRow = sheet.createRow(0);

		      for (int col = 0; col < LedgerTransaction.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(LedgerTransaction[col]);
		      }

		      int rowIdx = 1;
		      for (LedgerTransaction LedgerTransaction : ledgerTransactions) {
		        Row row = sheet.createRow(rowIdx++);
		        System.out.println("approved by:"+LedgerTransaction.getCreatedDate());

		        row.createCell(0).setCellValue(LedgerTransaction.getID());
		        row.createCell(1).setCellValue(LedgerTransaction.getCustomer().getName());
		        row.createCell(2).setCellValue(LedgerTransaction.getCustomer().getEmail());
		        row.createCell(3).setCellValue(LedgerTransaction.getCustomer().getFirmName());
		        row.createCell(4).setCellValue(LedgerTransaction.getCredit());
		        row.createCell(5).setCellValue(LedgerTransaction.getDebit());
		        row.createCell(6).setCellValue(LedgerTransaction.getCreatedDate()+"");
		        row.createCell(7).setCellValue(LedgerTransaction.getRemarks());
		      }

		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		    }
		  }

}
