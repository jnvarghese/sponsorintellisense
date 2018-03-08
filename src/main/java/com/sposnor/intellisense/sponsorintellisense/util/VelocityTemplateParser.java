package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityTemplateParser {

	public static String generateHTML() throws Exception {
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

		context.put("sponsorId", "NE-02-EBN-1001");
		context.put("sponsorName", "Jacob Varughese");
		context.put("sponsorParish", "Ebenezer Mar Thoma Church - New York");
		context.put("sponsorParishRegion", "Northeast - B");
		context.put("sponsorAddress", "52 Frederick Place, Mount Vernon, New York - 10552");
		context.put("sponsorPhone", "(914) 610-6360");
		context.put("sponsorEmail", "sajielzy@aol.com");
		context.put("childId", "CARD-SNKMP-5191");
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
		context.put("totalChildrenSposored", "1");
		context.put("spnStartDate","December 1, 2018");
		context.put("totalPaymentReceived","$260");
		context.put("paymentMethod","[Yearly] [Check] [$20/Child/Month]");
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}

}
