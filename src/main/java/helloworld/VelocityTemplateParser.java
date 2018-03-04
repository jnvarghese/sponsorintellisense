package helloworld;

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

		context.put("name", "World");
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		return writer.toString();
	}

}
