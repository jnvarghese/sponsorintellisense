package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.util.StringUtils;

import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;

public class VelocityTemplateParser {
  
	public static String generateReceipt(Receipts receipt) throws Exception {
		final Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		final VelocityEngine engine = new VelocityEngine(props);
		final VelocityContext context = new VelocityContext();
		engine.init();

		StringBuilder sb = new StringBuilder();
		/* next, get the Template */
		Template t = engine.getTemplate("templates/receipt.vm");
		/* create a context and add data */
		context.put("receiptNo", receipt.getReceiptId());
		context.put("receiptDate", receipt.getRdate());
		if(!StringUtils.isEmpty(receipt.getFullName())) {
			context.put("receivedfrom", receipt.getFullName());
		}else {
			context.put("receivedfrom", receipt.getFirstName() +" "+ receipt.getLastName());
		}
		if(!StringUtils.isEmpty(receipt.getStreetAddress())) {
			sb.append(receipt.getStreetAddress());
			sb.append(", ");
		}
		if(!StringUtils.isEmpty(receipt.getCity())) {
			sb.append(receipt.getCity().trim());
			sb.append(", ");
		}
		if(!StringUtils.isEmpty(receipt.getState())) {
			sb.append(receipt.getState());
			sb.append(", ");
		}
		if(!StringUtils.isEmpty(receipt.getZipCode())) {
			sb.append(receipt.getZipCode());
		}
		
		context.put("address", sb.toString());
		if(receipt.getReceiptType() == 0) {
			context.put("from", receipt.getParishName());
		}else if(receipt.getReceiptType() == 1) {
			context.put("from", receipt.getOrgName());
		}else {
			context.put("from", receipt.getFirstName() +" "+ receipt.getLastName());
		}
		context.put("missionname", receipt.getInitiativeName());
		
		if(String.valueOf(receipt.getAmount()).contains(".")) {
			String[] totalSplit= String.valueOf(receipt.getAmount()).split("\\.");
			if(totalSplit[1].length()==1) {
				context.put("total", totalSplit[0]+"."+totalSplit[1]+"0");
			}else {
				context.put("total", totalSplit[0]+"."+totalSplit[1]);
			}			
		}else {
			context.put("total", String.valueOf(receipt.getAmount())+"."+00);
		}
		context.put("rType", receipt.getReceiptType());
		context.put("paymentmethod", receipt.getTransaction());
		context.put("waterMark", receipt.getWaterMark());
		 
		context.put("number", new NumberTool());
		context.put("dateUtil", new DateTool());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}
	
	public static String generateCoverLetter(SponsorReport sr, int size) throws Exception {
		final Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		final VelocityEngine engine = new VelocityEngine(props);
		final VelocityContext context = new VelocityContext();
		engine.init();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		
		
		/* next, get the Template */
		Template t = engine.getTemplate("templates/coverletter.vm");
		/* create a context and add data */
		context.put("timeNow", time);
	
		context.put("sponsorName", sr.getSponsorName());
		
		context.put("totalCount", size);
		context.put("seal", sr.getSeal());
		context.put("sign1", sr.getSign1());
		context.put("waterMark", sr.getWaterMark());
		
		
		context.put("numberTool", new NumberTool());
		context.put("date", new DateTool());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}

	
	public static String generateHTML( Map<String,Object> dataMap) throws Exception {
		final Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		final VelocityEngine engine = new VelocityEngine(props);
		final VelocityContext context = new VelocityContext();
		engine.init();

		/* next, get the Template */
		Template t = engine.getTemplate("templates/enrollment.vm");
		/* create a context and add data */
		context.put("timeNow", dataMap.get("timeNow"));
		context.put("sponsorId", dataMap.get("sponsorId"));
		context.put("sponsorName", dataMap.get("sponsorName"));
		context.put("sponsorParish", dataMap.get("sponsorParish"));
		context.put("sponsorParishRegion", dataMap.get("sponsorParishRegion"));
		context.put("sponsorAddress", dataMap.get("sponsorAddress"));
		context.put("sponsorPhone", dataMap.get("sponsorPhone"));
		context.put("sponsorEmail", dataMap.get("sponsorEmail"));
		context.put("renewalDue",  dataMap.get("renewalDue"));
		context.put("sign2", dataMap.get("sign2"));
 
		context.put("waterMark", dataMap.get("waterMark"));
		
		context.put("sponseeList", dataMap.get("sponseeList"));
		
		context.put("totalChildrenSposored", dataMap.get("totalChildrenSposored"));
		context.put("spnStartDate", dataMap.get("spnStartDate"));
		context.put("totalPaymentReceived", dataMap.get("totalPaymentReceived"));
		
		context.put("fundUsed", dataMap.get("fundUsed"));
		context.put("totalBalance", dataMap.get("totalBalance"));
		context.put("totalSponsorshipReceived", dataMap.get("totalSponsorshipReceived"));

		context.put("paymentMethod","[Yearly] [Check] [$20/Child/Month]");
		
		context.put("numberTool", new NumberTool());
		context.put("date", new DateTool());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}

}
