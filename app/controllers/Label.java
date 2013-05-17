package controllers;

import play.templates.TemplateLoader;
import play.templates.Template;
import org.apache.commons.io.IOUtils;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayInputStream;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;   
import com.google.zxing.qrcode.*;
import play.mvc.*;
import java.io.*;

public class Label extends Controller{

    public static void label(){
        renderTemplate("label/drive.html");
    }
	
	public static void test() throws Exception{
		//Template t = TemplateLoader.load("label/drive.html");
		response.contentType = "application/pdf";    
		//String page = t.render();
		//System.out.println( "println that page out " + page );
        Document document = new Document(new Rectangle(217f,187f));
        document.setMargins(18,18,9,18);
        PdfWriter writer = PdfWriter.getInstance(document, response.out );
        document.open();
        
    
        PdfContentByte canvas = writer.getDirectContent();
        Font fontbold = FontFactory.getFont("Arial", 18, Font.BOLD);
        document.add( new Paragraph("Drive Cleaners",fontbold) );
        //Rectangle header = new Rectangle(20,20,70,70);
        //document.add(Chunk.NEWLINE);
        
        // add the barcode
        Image barcode = getBarcode();
        document.add( barcode );
        
        //XMLWorkerHelper.getInstance().parseXHtml(writer, document, IOUtils.toInputStream( page ) );
        document.close();		
		response.out.flush();		
	}
	protected static Image getBarcode() throws Exception{
        BitMatrix matrix = null;
        int h = 100;
            int w = 100;
            com.google.zxing.Writer writer = new QRCodeWriter();
            try {
                matrix = writer.encode("hello world",
                com.google.zxing.BarcodeFormat.QR_CODE, w, h);
            } catch (com.google.zxing.WriterException e) {
                System.out.println(e.getMessage());
            }

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(matrix, "PNG", bos );
                return Image.getInstance( bos.toByteArray() );
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return null;
	}

	
	
	public static void barcode() throws Exception{
        BitMatrix matrix = null;
        int h = 100;
            int w = 100;
            com.google.zxing.Writer writer = new QRCodeWriter();
            try {
                matrix = writer.encode("hello world",
                com.google.zxing.BarcodeFormat.QR_CODE, w, h);
            } catch (com.google.zxing.WriterException e) {
                System.out.println(e.getMessage());
            }

            //String filePath = "C:\\temp\\qr_png.png";
            //File file = new File(filePath);
            try {
                response.contentType = "image/png";
                MatrixToImageWriter.writeToStream(matrix, "PNG", response.out);
                response.out.flush();
                //System.out.println("printing to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }	    
	}
	
}