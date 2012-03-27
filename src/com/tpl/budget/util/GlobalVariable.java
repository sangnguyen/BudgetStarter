package com.tpl.budget.util;

import java.io.File;

import android.R.string;
import android.os.Environment;

import com.itextpdf.text.Image;


public class GlobalVariable
{
	
	public static final int kCHARITY=0;
	public static final int kSAVINGS=1;
	public static final int kHOUSING=2;
	public static final int kUTILITIES=3;
	public static final int kFOOD=4;
	public static final int kTRANS=5;
	public static final int kCLOTHING=6;
	public static final int kMEDICAL=7;
	public static final int kPERSONAL=8;
	public static final int kRECREATION=9;
	public static final int kDEBTS=10;
	public static final int kREMAINING=11;	
	public static final int kMonthlyInput=12;
	public static final int kInput=13;
	
	public static final int kBudgetPage1=0;
	public static final int kBudgetPage2=1;
	public static final int kBudgetPage3=2;
	
	public static final int kResourcePage1=3;
	public static final int kResourcePage2=4;
	public static final int kResourcePage3=5;
	public static final int kInfoPage=6;
	
	public static final int kCLOSED_ALL =-2;
	
	//#define kSubmitEmailAPIUrl @"http://www.google.com/?q=%@"

	//#define kAdAPIUrl @"https://api.drtlgi.com/budget-starter/ads.json?ad_height=%d&ad_width=%d&ppi=%d&platform=ios"
	//#define kArticleArchivesAPIUrl @"https://api.drtlgi.com/budget-starter/article-archives"
	
	//public static final String kMONEY_ARTICLES_LINK="https://www.mytotalmoneymakeover.com/index.cfm?event=displayArticleListFeed";
	public static final String kMONEY_ARTICLES_LINK="https://api.drtlgi.com/budget-starter/article-archives";
	public static final int kBudgetPercentNum = 11;
	public static final int kCharityPer=10; //int[] arrayBudgetPercent={10,5,25,5,5,10,2,5,5,5,0};
	public static final int kSavingsPer=5;
	public static final int kHousingPer=25;	
	public static final int kUtilitiesPer=5;
	public static final int kFoodPer=5;
	public static final int kTransPer=10;
	public static final int kClosthingPer=2;
	public static final int kMedicalPer=5;
	public static final int kPesonalPer=5;
	public static final int kRecreationPer=5;
	public static final int kDebtsPer=0;
	
	public static final int[]kArrayBudgetPercentDefault={10,5,25,5,5,10,2,5,5,5,0};
	public static final int kPercentDefaultTotal = 77;
	public static double mIncome;
	public static double mTotal;
	public static double[] mArray;
	public static int[] mArrayPercent;
	public static Image mPdfTemlete = null;
	public static File mRoot = Environment.getExternalStorageDirectory();      
	public static File mPdfFileName = new File(mRoot,"budget.pdf");  
	
	
	public static boolean Fix_bug_dont_back_one_time = true;
	public static boolean Fix_bug_color_red_green_text = true;
	public static boolean Popup_web  = true;
	public static String temp_link ;
	public static boolean Enable_type_number_december = true;
	
}
