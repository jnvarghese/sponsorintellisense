package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;

@RestController
@RequestMapping("/api/generatepdf")
public class PDFController {

	@GetMapping("/download")
	public void doenloadFile(HttpServletResponse resonse) {
		   // File file = new File("C:\\Users\\Jinu\\test.pdf");

	   
	    	  try {
				String htmlstring = VelocityTemplateParser.generateHTML();

					InputStream is = new ByteArrayInputStream(htmlstring.getBytes());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					// step 1
					Document document = new Document();

					// step 2
					PdfWriter writer = PdfWriter.getInstance(document, baos);

					writer.setInitialLeading(12.5f);

					// step 3
					document.open();

					HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);

					htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

					// CSS
					CSSResolver cssResolver = new StyleAttrCSSResolver();
					InputStream csspathtest = Thread.currentThread()
							.getContextClassLoader()
							.getResourceAsStream("input/index.css");
					CssFile cssfiletest = XMLWorkerHelper.getCSS(csspathtest);
					cssResolver.addCss(cssfiletest);

					Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
							new HtmlPipeline(htmlContext, new PdfWriterPipeline(
									document, writer)));

					XMLWorker worker = new XMLWorker(pipeline, true);
					XMLParser p = new XMLParser(worker);
					p.parse(is);//new FileInputStream("results/demo2/walden.html"));

					// step
					document.close();
					
				  resonse.setContentType("application/pdf");
				  resonse.setHeader("Content-Disposition", "attachment;filename=" + "file");
				  resonse.setContentLength(baos.size());
				  // write ByteArrayOutputStream to the ServletOutputStream
		            OutputStream os = resonse.getOutputStream();
		            baos.writeTo(os);
		            os.flush();
		            os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

}
