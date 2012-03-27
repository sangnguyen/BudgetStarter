package com.tpl.budget;
import com.tpl.budget.util.DataManager;
import com.tpl.budget.util.GlobalVariable;
import com.tpl.budget.util.ResourceMoneyArticle;
import com.tpl.budget.util.ResourceListAdapter;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class BudgetPreviewActivity extends Activity
{	
	private static String FILE = "c:/temp/FirstPdf.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_preview_activity);
        
        CratePdf2(GlobalVariable.mIncome,GlobalVariable.mTotal,GlobalVariable.mArray,GlobalVariable.mArrayPercent);
       // captureScreen();
    }
    private void captureScreen() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.budget_preview);
                  File root = Environment.getExternalStorageDirectory();
                  File file = new File(root,"androidlife.jpg");
        Bitmap b = Bitmap.createBitmap(800, 1000, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        mainLayout.draw(c);
        FileOutputStream fos = null;
        try {
           fos = new FileOutputStream(file);

           if (fos != null) {
              b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
              fos.close();
           }
        } catch (Exception e) {
           e.printStackTrace();
        }
        CreatePdf();
    }
    public void CreatePdf() 
    {
        Document document = new Document();
        
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root,"androidlife.jpg");
        File pdfFile = new File(root,"androidlife.pdf");
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(pdfFile));
            document.open();

            Image image1 = Image.getInstance(file.getPath());
            document.add(image1);

            
            String imageUrl = "http://jenkov.com/images/" +
                    "20081123-20081123-3E1W7902-small-portrait.jpg";

            Image image2 = Image.getInstance(new URL(imageUrl));
            document.add(image2);

            document.close();
        } catch(Exception e){
          e.printStackTrace();
        }
  }
    private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
    private PdfPTable createTable()
	{
    	PdfPTable table = new PdfPTable(3);
    	float[] widths = new float[] { 8f, 2f,1f };
    	try{
    		table.setWidths(widths);
    	}
    	catch(Exception ex)
    	{
    		
    	}
		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);
    	Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
    			Font.NORMAL, BaseColor.BLUE);
    	Font headerFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
    			Font.NORMAL, BaseColor.BLUE);
    	
    	Phrase phrase = new Phrase("My Budget", headerFont);    	
		PdfPCell c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);	
		c1.setBorder(Rectangle.NO_BORDER);		
		table.addCell(c1);
		
				
    	phrase = new Phrase("Income:$99,999", headerFont);
		c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
		c1.setBorder(Rectangle.NO_BORDER);
		table.addCell(c1);
		
		
    	phrase = new Phrase(" ", headerFont);
		c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
		c1.setBorder(Rectangle.NO_BORDER);
		table.addCell(c1);
		
		///////////////////////////////////////////////////////////
		phrase = new Phrase("Name", headerFont2);    	
		c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);	
		c1.setBorder(Rectangle.NO_BORDER);		
		table.addCell(c1);
		
		
		
    	phrase = new Phrase("Budgeted", headerFont2);
		c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
		c1.setBorder(Rectangle.NO_BORDER);
		table.addCell(c1);
		
    	phrase = new Phrase(" ", headerFont2);
		c1 = new PdfPCell(phrase);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
		c1.setBorder(Rectangle.NO_BORDER);
		table.addCell(c1);
		
			
		
		table.setHeaderRows(1);
		
		table.addCell("1.0");
		table.addCell("1.1");
		table.addCell("1.2");
		table.addCell("2.1");
		table.addCell("2.2");
		table.addCell("2.3");
		
		return table;

	}
    private void CratePdf2(Double incomeValue,Double totalValue, double[] array,int[] arrayPercent)
    {
    	 
        Document document = new Document();
        File root = Environment.getExternalStorageDirectory();      
        File pdfFile = new File(root,"androidlife.pdf");      
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(pdfFile));
            document.open();
            Rectangle rec =  document.getPageSize();
            //float a = rec.getWidth();
           //float b= rec.getHeight();
            Image image3 = Image.getInstance("/mnt/sdcard/pdfbottom.png");
            image3.scaleAbsolute(590f, 200f);
            image3.setAbsolutePosition(0f, 0f);
            document.add(image3);

            Image image2 = Image.getInstance("/mnt/sdcard/pdf_right.png");
            image2.scaleAbsolute(250f, 500f);
            image2.setAbsolutePosition(345f, 300f);
            document.add(image2);
            
            Image image1 = Image.getInstance("/mnt/sdcard/pdf_top_bar.png");                    
            image1.scaleAbsolute(591f, 10f);
            image1.setAbsolutePosition(2f, 810f);
            document.add(image1);
            
            image1 = Image.getInstance("/mnt/sdcard/pdf_logo.png");            
            image1.scaleAbsolute(250f, 50f);
            image1.setAbsolutePosition(30f, 760f);
            document.add(image1);
                 
            /*
    		Paragraph subPara = new Paragraph("Subcategory 1", subFont);
    		subPara.add(createTable());
    		subPara.setAlignment(Element.ALIGN_LEFT);
    		
    		
    		
    		
    		document.add(subPara);
    		*/
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
        			Font.NORMAL, BaseColor.BLUE);
            Font totalFont= new Font(Font.FontFamily.TIMES_ROMAN, 18,
        			Font.NORMAL, BaseColor.BLUE);
            Font headerFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
        			Font.NORMAL, BaseColor.BLUE);
            Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 4,
        			Font.NORMAL, BaseColor.BLUE);
            Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
        			Font.NORMAL, BaseColor.BLACK);
         // Left
            DecimalFormat format = new DecimalFormat("#,##0.00");
            
            Paragraph paragraph = new Paragraph(" ");
            paragraph.add(new Paragraph(" "));
            paragraph.add(new Paragraph(" "));     
            paragraph.add(new Paragraph(" "));     
            paragraph.add(new Paragraph(" "));     
            paragraph.add(new Paragraph("My Budget                        InCome: "+ format.format(incomeValue),headerFont));
            paragraph.add(new Paragraph("Name                                                      Budgeted: ",headerFont2));
           // String strVulue = format.format(array[GlobalVariable.kCHARITY]);
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Charity(10%-15%)............................" + format.format(array[GlobalVariable.kCHARITY])+"(" + arrayPercent[GlobalVariable.kCHARITY]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Savings(5%-10%)............................." +format.format(array[GlobalVariable.kSAVINGS]) +"(" + arrayPercent[GlobalVariable.kSAVINGS]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Housing(25%-30%)..........................." +format.format(array[GlobalVariable.kHOUSING]) +"(" + arrayPercent[GlobalVariable.kHOUSING]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Utilities(5%-10%)..............................." +format.format(array[GlobalVariable.kUTILITIES]) +"(" + arrayPercent[GlobalVariable.kUTILITIES]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Food(5%-15%).................................." +format.format(array[GlobalVariable.kFOOD]) +"(" + arrayPercent[GlobalVariable.kFOOD]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Transportation(10%-15%)................." +format.format(array[GlobalVariable.kTRANS]) +"(" + arrayPercent[GlobalVariable.kTRANS]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Clothing(2%-7%)..............................." +format.format(array[GlobalVariable.kCLOTHING]) +"(" + arrayPercent[GlobalVariable.kCLOTHING]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Medical/Health(5%-10%).................." +format.format(array[GlobalVariable.kMEDICAL]) +"(" + arrayPercent[GlobalVariable.kMEDICAL]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Personal(5%-10%)............................" +format.format(array[GlobalVariable.kPERSONAL]) +"(" + arrayPercent[GlobalVariable.kPERSONAL]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Recreation(5%-10%)........................." +format.format(array[GlobalVariable.kRECREATION]) +"(" + arrayPercent[GlobalVariable.kRECREATION]+ "%)" ));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("Debts(Hopefully 0%).........................." +format.format(array[GlobalVariable.kDEBTS]) +"(" + arrayPercent[GlobalVariable.kDEBTS]+ "%)" ));
			
			int totalPer=0;
			for(int i=0;i< arrayPercent.length;i++)
			{
				totalPer += arrayPercent[i];
			}
			paragraph.add(new Paragraph(" ",smallFont));	
			paragraph.add(new Paragraph("TOTAL:                             " + format.format(totalValue)+ "(" + totalPer + "%)" ,totalFont));
			paragraph.add(new Paragraph(" ",smallFont));
			paragraph.add(new Paragraph("You have not created a zero-based budget. Name every dollar!",font3));
			paragraph.add(new Paragraph("Go back and adjust your budget to fit your planned income.",font3));
			paragraph.setAlignment(Element.ALIGN_LEFT);
			
			document.add(paragraph);	
			// Centered
                     
            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    	   
       
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();       
        System.gc();
    }  
    
}