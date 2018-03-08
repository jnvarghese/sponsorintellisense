package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityTemplateParser {

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
System.err.println(" dataMap " +dataMap);
		context.put("sponsorId", dataMap.get("sponsorId"));
		context.put("sponsorName", dataMap.get("sponsorName"));
		context.put("sponsorParish", dataMap.get("sponsorParish"));
		context.put("sponsorParishRegion", dataMap.get("sponsorParishRegion"));
		context.put("sponsorAddress", dataMap.get("sponsorAddress"));
		context.put("sponsorPhone", dataMap.get("sponsorPhone"));
		context.put("sponsorEmail", dataMap.get("sponsorEmail"));
		context.put("sponseeList", dataMap.get("sponseeList"));
		
		context.put("totalChildrenSposored", dataMap.get("totalChildrenSposored"));
		context.put("spnStartDate", dataMap.get("spnStartDate"));
		context.put("totalPaymentReceived", dataMap.get("totalPaymentReceived"));
		/*context.put("childId", "CARD-SNKMP-5191");
		context.put("childName", "Neha Chappa");
		context.put("childDOB", "June 13, 2011");
		context.put("childGrade", "2nd Grade");
		context.put("childGender", "Female");
		context.put("childMediumOfStudy", "Hindi");
		context.put("childCluster", "Sarvodaya");
		context.put("childHobby", "Drawing");
		context.put("childFavColor", "Blue");
		context.put("childFavGame", "Hide - Seek");
		context.put("programPartner", "Christian Agency for Rural Development [CARD]");
		context.put("programLocation", "Shanthi Nagar – Kshipra Village – Madhya Pradesh");
		
		context.put("paymentMethod","[Yearly] [Check] [$20/Child/Month]");*/
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}

}
