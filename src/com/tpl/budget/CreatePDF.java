package com.tpl.budget;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DecimalFormat;

import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.simpleparser.NewLineHandler;
import com.tpl.budget.util.GlobalVariable;


public class CreatePDF {
		private Font charityFont;
		private Font percentRay ;
		private Font percentGreen ;
		private Font percentRed ;
		private Font headerFont;
		private DecimalFormat format ;
		private PdfPCell cellNull;
		private PdfPCell cellMyBudget;		
		private PdfPCell cellCharity;
		private PdfPCell cellSaving;
		private PdfPCell cellHousing;
		private PdfPCell cellUtilities;
		private PdfPCell cellFood;
		private PdfPCell cellTrans;
		private PdfPCell cellClothes;
		private PdfPCell cellMedical;
		private PdfPCell cellPersonal;
		private PdfPCell cellRecreation;
		private PdfPCell cellDebts;
		private PdfPCell cellTotal;
		public CreatePDF()
		{		
			BaseFont bf1=null;
			try{
			 bf1 = BaseFont.createFont("c:/windows/fonts/arial.ttf",
			            BaseFont.CP1252, BaseFont.EMBEDDED);
			}catch(Exception ex)
			{
				
			}
		        //Font font1 = new Font(bf1, 1);
			charityFont = new Font(bf1, 12,Font.BOLD, BaseColor.BLACK);
			percentRay = new Font(bf1, 9,Font.NORMAL, BaseColor.LIGHT_GRAY);
			percentGreen = new Font(bf1, 9,Font.NORMAL, 
					//BaseColor.GREEN
					new BaseColor(48,154,73)
					);
			percentRed = new Font(bf1, 9,Font.NORMAL, BaseColor.RED);
			headerFont = new Font(bf1, 16,Font.BOLD, new BaseColor(89,149,196));	
			format = new DecimalFormat("#,##0.00");	
			cellNull = new PdfPCell(new Phrase(""));	
			cellNull.setBorder(Rectangle.NO_BORDER);
			
			Paragraph paragrap = new Paragraph();
			
			//Budget
			Phrase phrase = new Phrase("My Budget", headerFont); 
	    	paragrap.add(phrase);
			cellMyBudget=new PdfPCell(paragrap);
			cellMyBudget.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellMyBudget.setBorder(Rectangle.NO_BORDER);
			
	    	//charity
			paragrap = new Paragraph();	
	    	phrase = new Phrase("Charity", charityFont); 
	    	paragrap.add(phrase);
	    	Phrase phrase2 = new Phrase(" (10%-15%) ..................................", percentRay); 
	    	paragrap.add(phrase2);
	    	cellCharity = new PdfPCell(paragrap);			
	    	cellCharity.setHorizontalAlignment(Element.ALIGN_LEFT);	
	    	cellCharity.setBorder(Rectangle.NO_BORDER);
			
			//cellSaving;
	    	paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Savings", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-10%) ...................................", percentRay); 
	    	paragrap.add(phrase2);
			cellSaving = new PdfPCell(paragrap);			
			cellSaving.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellSaving.setBorder(Rectangle.NO_BORDER);	
			
			//cellHousing;
			paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Housing", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (25%-35%) ................................", percentRay); 
	    	paragrap.add(phrase2);
			cellHousing = new PdfPCell(paragrap);			
			cellHousing.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellHousing.setBorder(Rectangle.NO_BORDER);	
			
			//cellUtilities;
			paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Utilities", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-10%) ....................................", percentRay); 
	    	paragrap.add(phrase2);
			cellUtilities = new PdfPCell(paragrap);			
			cellUtilities.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellUtilities.setBorder(Rectangle.NO_BORDER);		
			
			//Food							
	    	paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Food", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-15%) ........................................", percentRay); 
	    	paragrap.add(phrase2);
			cellFood = new PdfPCell(paragrap);			
			cellFood.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellFood.setBorder(Rectangle.NO_BORDER);					
			//Trans		
	    	paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Transportation", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (10%-15%) ...............", percentRay); 
	    	paragrap.add(phrase2);
			cellTrans = new PdfPCell(paragrap);			
			cellTrans.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellTrans.setBorder(Rectangle.NO_BORDER);			
			
			//Clothes			
	    	paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Clothing", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (2%-7%) .................................", percentRay); 
	    	paragrap.add(phrase2);
			cellClothes = new PdfPCell(paragrap);			
			cellClothes.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellClothes.setBorder(Rectangle.NO_BORDER);					
			
			//Medical			
	    	paragrap = new Paragraph();
	    	phrase = new Phrase("Medical / Health", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-10%) ..............", percentRay); 
	    	paragrap.add(phrase2);
			cellMedical = new PdfPCell(paragrap);			
			cellMedical.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellMedical.setBorder(Rectangle.NO_BORDER);		
			
			//Personal					
	    	paragrap = new Paragraph();
	    	phrase = new Phrase("Personal", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-10%) ................................", percentRay); 
	    	paragrap.add(phrase2);
			cellPersonal = new PdfPCell(paragrap);			
			cellPersonal.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellPersonal.setBorder(Rectangle.NO_BORDER);					
			
			//Recreation				
	    	paragrap = new Paragraph();
	    	phrase = new Phrase("Recreation", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (5%-10%) ..........................", percentRay); 
	    	paragrap.add(phrase2);
			cellRecreation = new PdfPCell(paragrap);			
			cellRecreation.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellRecreation.setBorder(Rectangle.NO_BORDER);		
			
			//Debts									    
	    	paragrap = new Paragraph();	    	
	    	phrase = new Phrase("Debts", charityFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" (Hopefully 0%) ..............................", percentRay); 
	    	paragrap.add(phrase2);
			cellDebts = new PdfPCell(paragrap);			
			cellDebts.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellDebts.setBorder(Rectangle.NO_BORDER);	
			//Total
			paragrap = new Paragraph();	    	
	    	phrase = new Phrase("TOTAL:", headerFont); 
	    	paragrap.add(phrase);
	    	phrase2 = new Phrase(" .............................................", percentRay); 
	    	paragrap.add(phrase2);
			cellTotal = new PdfPCell(paragrap);			
			cellTotal.setHorizontalAlignment(Element.ALIGN_LEFT);	
			cellTotal.setBorder(Rectangle.NO_BORDER);
		}
		public void CratePdf(URL rootPath,Double incomeValue,Double totalValue, double[] array,int[] arrayPercent)
	    {	    	 		 	
	        Document document = new Document();	            
	        try {
	            PdfWriter.getInstance(document,new FileOutputStream(GlobalVariable.mPdfFileName));
	            document.open();
	            //Rectangle rec =  document.getPageSize();//595x842	           
	            if(GlobalVariable.mPdfTemlete != null)
	            {
	            	document.add(GlobalVariable.mPdfTemlete);
	            }
	            else
	            {
	            	GlobalVariable.mPdfTemlete = Image.getInstance(rootPath);
	            	GlobalVariable.mPdfTemlete.scaleAbsolute(595f, 842f);			          
	            	GlobalVariable.mPdfTemlete.setAbsolutePosition(0f, 0f);
		            document.add(GlobalVariable.mPdfTemlete);
	            }
//	            Image image2 = Image.getInstance("/mnt/sdcard/pdf_right.png");
//	            image2.scaleAbsolute(250f, 500f);
//	            image2.setAbsolutePosition(345f, 300f);
//	            document.add(image2);
//	            
          
	         // Left
	                        
	            Paragraph paragraph = new Paragraph("");
	            
	            paragraph.add(new Paragraph(""));
	            paragraph.add(new Paragraph(""));     
	            paragraph.add(new Paragraph(""));   
	            paragraph.add(new Paragraph("")); 
	            paragraph.add(new Paragraph("")); 
	           // paragraph.add(new Paragraph("")); 
	            
	            PdfPTable table = new PdfPTable(3);	
		    	table.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	float[] widths = new float[] { 0.3f, 4f,4.5f };
		    	try{
		    		table.setWidths(widths);
		    		table.setTotalWidth(300f);
		    		table.setWidthPercentage(63.5f);
		    	}
		    	catch(Exception ex)
		    	{	    		
		    	}				    		    	
		    	table.addCell(cellNull);
		    	
		    	
				table.addCell(cellMyBudget);
				Phrase phrase = new Phrase("Income: $" + format.format(incomeValue), headerFont); 		    	
				PdfPCell cellIncome=new PdfPCell(phrase);
				cellIncome.setHorizontalAlignment(Element.ALIGN_RIGHT);	
				cellIncome.setBorder(Rectangle.NO_BORDER);
				table.addCell(cellIncome);
				
	            paragraph.add(table);
	            //paragraph.add(new Paragraph("                                                                                   $"+ format.format(incomeValue),headerFont));
	            
	            paragraph.add(new Paragraph(""));     
	            paragraph.add(new Paragraph(""));       
	            
//	            paragraph.add(new Paragraph("Name                                                      Budgeted: ",headerFont2));
//	           // String strVulue = format.format(array[GlobalVariable.kCHARITY]);
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Charity(10%-15%)............................" + format.format(array[GlobalVariable.kCHARITY])+"(" + arrayPercent[GlobalVariable.kCHARITY]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Savings(5%-10%)............................." +format.format(array[GlobalVariable.kSAVINGS]) +"(" + arrayPercent[GlobalVariable.kSAVINGS]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Housing(25%-30%)..........................." +format.format(array[GlobalVariable.kHOUSING]) +"(" + arrayPercent[GlobalVariable.kHOUSING]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Utilities(5%-10%)..............................." +format.format(array[GlobalVariable.kUTILITIES]) +"(" + arrayPercent[GlobalVariable.kUTILITIES]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Food(5%-15%).................................." +format.format(array[GlobalVariable.kFOOD]) +"(" + arrayPercent[GlobalVariable.kFOOD]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Transportation(10%-15%)................." +format.format(array[GlobalVariable.kTRANS]) +"(" + arrayPercent[GlobalVariable.kTRANS]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Clothing(2%-7%)..............................." +format.format(array[GlobalVariable.kCLOTHING]) +"(" + arrayPercent[GlobalVariable.kCLOTHING]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Medical/Health(5%-10%).................." +format.format(array[GlobalVariable.kMEDICAL]) +"(" + arrayPercent[GlobalVariable.kMEDICAL]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Personal(5%-10%)............................" +format.format(array[GlobalVariable.kPERSONAL]) +"(" + arrayPercent[GlobalVariable.kPERSONAL]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Recreation(5%-10%)........................." +format.format(array[GlobalVariable.kRECREATION]) +"(" + arrayPercent[GlobalVariable.kRECREATION]+ "%)" ));
//				paragraph.add(new Paragraph(" ",smallFont));
//				paragraph.add(new Paragraph("Debts(Hopefully 0%).........................." +format.format(array[GlobalVariable.kDEBTS]) +"(" + arrayPercent[GlobalVariable.kDEBTS]+ "%)" ));
	            int totalPer=0;	           
				for(int i=0;i< arrayPercent.length;i++)
				{
					totalPer += arrayPercent[i];					
				}
	            document.add(paragraph);
	            
	            Paragraph subPara = new Paragraph();
	    		subPara.add(createTable(totalValue,totalPer,array,arrayPercent));
	    		subPara.setAlignment(Element.ALIGN_LEFT);	    			    			    			   
	    		document.add(subPara);
	           
	         
	                     
	            document.close();
	        } catch(Exception e){
	            e.printStackTrace();
	        }
	    	   
	       
	    }	
	 public PdfPTable createTable(Double totalValue,int totalPercent, double[] array,int[] arrayPercent)
		{
		 	
	    	PdfPTable table = new PdfPTable(3);	
	    	table.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	float[] widths = new float[] { 0.5f, 5.5f,4f };
	    	try{
	    		table.setWidths(widths);
	    		table.setTotalWidth(300f);
	    		table.setWidthPercentage(63.5f);
	    	}
	    	catch(Exception ex)
	    	{	    		
	    	}				    		    	
	    	table.addCell(cellNull);	    	
			table.addCell(cellCharity);			
			//charity value
			Paragraph paragrap = new Paragraph();
			DecimalFormat format = new DecimalFormat("#,##0.00");
			Phrase phrase = new Phrase(format.format(array[GlobalVariable.kCHARITY]),charityFont); 
			paragrap = new Paragraph(phrase);
			Phrase phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kCHARITY] +"%)", (arrayPercent[GlobalVariable.kCHARITY] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kCHARITY]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			PdfPCell c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			
			//Savings (5%-10%)
			
	    	table.addCell(cellNull);	
			table.addCell(cellSaving);			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kSAVINGS]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kSAVINGS] +"%)", (arrayPercent[GlobalVariable.kSAVINGS] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kSAVINGS]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Housing
			
			
	    	table.addCell(cellNull);
			table.addCell(cellHousing);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kHOUSING]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kHOUSING] +"%)", (arrayPercent[GlobalVariable.kHOUSING] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kHOUSING]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			
			//Utilities
			
				
	    	table.addCell(cellNull);
			table.addCell(cellUtilities);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kUTILITIES]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kUTILITIES] +"%)", (arrayPercent[GlobalVariable.kUTILITIES] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kUTILITIES]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Food
							
	    	table.addCell(cellNull);
			table.addCell(cellFood);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kFOOD]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kFOOD] +"%)", (arrayPercent[GlobalVariable.kFOOD] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kFOOD]? percentGreen:percentRed)); 
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Trans		
			
	    	table.addCell(cellNull); 			
			table.addCell(cellTrans);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kTRANS]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kTRANS] +"%)", (arrayPercent[GlobalVariable.kTRANS] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kTRANS]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Clothes			
				
	    	table.addCell(cellNull);	    			
			table.addCell(cellClothes);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kCLOTHING]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kCLOTHING] +"%)", (arrayPercent[GlobalVariable.kCLOTHING] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kCLOTHING]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			
			//Medical			
			
	    	table.addCell(cellNull);	    		
			table.addCell(cellMedical);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kMEDICAL]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kMEDICAL] +"%)",(arrayPercent[GlobalVariable.kMEDICAL] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kMEDICAL]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			
			//Personal					
			
	    	table.addCell(cellNull);	    			
			table.addCell(cellPersonal);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kPERSONAL]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kPERSONAL] +"%)", (arrayPercent[GlobalVariable.kPERSONAL] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kPERSONAL]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Recreation				
			
	    	table.addCell(cellNull);	    		
			table.addCell(cellRecreation);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kRECREATION]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kRECREATION] +"%)", (arrayPercent[GlobalVariable.kRECREATION] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kRECREATION]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);
			//Debts					
			
	    	table.addCell(cellNull);	    		
			table.addCell(cellDebts);
			
			//value			
			phrase = new Phrase(format.format(array[GlobalVariable.kDEBTS]),charityFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + arrayPercent[GlobalVariable.kDEBTS] +"%)", (arrayPercent[GlobalVariable.kDEBTS] == GlobalVariable.kArrayBudgetPercentDefault[GlobalVariable.kDEBTS]? percentGreen:percentRed));
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			addLineNull(table);	
			//space
			PdfPCell spaceCell = new PdfPCell(new Phrase(" ",headerFont));	
			spaceCell.setBorder(Rectangle.NO_BORDER);
			
//			table.addCell(spaceCell);
//			table.addCell(spaceCell);
//			table.addCell(spaceCell);
//			
//			table.addCell(spaceCell);
//			table.addCell(spaceCell);
//			table.addCell(spaceCell);
			//Total					
			
	    	table.addCell(cellNull);	    		
			table.addCell(cellTotal);
			
			//value			
			phrase = new Phrase(format.format(totalValue),headerFont); 
			paragrap = new Paragraph(phrase);
	    	phrase2 = new Phrase(" (" + totalPercent +"%)", (totalPercent == GlobalVariable.kPercentDefaultTotal? percentGreen:percentRed)); 
	    	paragrap.add(phrase2);
			c1 = new PdfPCell(paragrap);			
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			c1.setBorder(Rectangle.NO_BORDER);		
			table.addCell(c1);
			
			
			return table;

		}
	 
	 void addLineNull(PdfPTable table)
	 {
		 table.addCell(cellNull);
		 table.addCell(cellNull);
		 table.addCell(cellNull);
	 }

}
