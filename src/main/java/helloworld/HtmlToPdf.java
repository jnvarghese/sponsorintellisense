package helloworld;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
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

public class HtmlToPdf {
	public static void main(String[] args) {
		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();
		try {
			String htmlstring = VelocityTemplateParser.generateHTML();

			InputStream is = new ByteArrayInputStream(htmlstring.getBytes());
			//document = new Document();
			// document header attributes
			document.addAuthor("Sandeep Bhardwaj");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("sandeepbhardwaj.github.io");
			document.addTitle("HTML to PDF using itext");
			document.setPageSize(PageSize.LETTER);

			OutputStream file = new FileOutputStream(new File("Test1.pdf"));
			pdfWriter = PdfWriter.getInstance(document, file);
			pdfWriter.setInitialLeading(12);
			// open document
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
							document, pdfWriter)));

			XMLWorker worker = new XMLWorker(pipeline, true);
			XMLParser p = new XMLParser(worker);
			p.parse(is);//new FileInputStream("results/demo2/walden.html"));

			
			document.close();
			// close the writer
			pdfWriter.close();

			System.out.println("PDF generated successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
