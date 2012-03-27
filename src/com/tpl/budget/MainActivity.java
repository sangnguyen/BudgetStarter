package com.tpl.budget;
import com.tpl.budget.util.DataManager;
import com.tpl.budget.util.GlobalVariable;
import java.util.regex.Pattern;
import com.tpl.budget.util.ResourceMoneyArticle;
import com.tpl.budget.util.ResourceListAdapter;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class MainActivity extends Activity
{
	
	
	private Context mContext;
	private Button btnGuide;
	private Button btnBack;
	private Button btnNext;
	private Button btnSubmit;
	private RelativeLayout view_budget_1;
	
	private RelativeLayout info_page;
	private RelativeLayout resource_page_1;
	private RelativeLayout resource_page_2;
	private ProgressBar resource_progress_bar;
	private ProgressBar resource_pdf_bar;
	
	private RelativeLayout chaoticmoon_link;	
	private RelativeLayout resource_show_app_link;
	private RelativeLayout resource_money_link;
	
	private TextView txtInput;
	private EditText txtEmail;
	private AlertDialog.Builder alert;
	private AlertDialog dialog;	
	private TabHost tabs;
	private WebView mWebView = null;
	//private Button btnEnterIncome;
	private final int BUDGET_TAB=0;
	private final int RESOURCE_TAB=1;
	private final int INFO_TAB=2;
	
	
	//Budget page detail
	//private TextView txtMonthlyInput;
	private TextView txtBudgeted;
	private RelativeLayout view_budget_2;
	//private TextView txtRemaining;
	private TextView txtRemainingInput;
	
	private TextView txtWarning;
	private Button btnReviewBudget;
	
	private int mArrowDownId;// = getResources().getIdentifier("arrow_down", "drawable", getPackageName() );	
 	private int mArrowUpId;// = getResources().getIdentifier("arrow_up", "drawable", getPackageName() );	
 	//anim
	private Animation animRightToLeft;
    private Animation animLeftToRight;

    private int mPreviousPage=GlobalVariable.kBudgetPage1;
    private int mCurrentPage=GlobalVariable.kBudgetPage1;
    private int mCurrentExpanded=GlobalVariable.kREMAINING;
    private int mResourceSubScreen=GlobalVariable.kResourcePage1;
    private int mBudgetSubScreen =GlobalVariable.kBudgetPage1;
    
    private double mIncome=-1;;
    private double[] mArrayBudgetValue=new double[13];
    private int[]mArrayBudgetPercent;//=new int[GlobalVariable.kBudgetPercentNum];
    private double mBudgetTed=0.00;
    private double mRemaining=0.00;
    private ListView mResourceListView;
    private  AlertDialog alertDialog;
    //private int[]	mArrayBudgetPercent={10,5,25,5,5,10,2,5,5,5,0};
    private ResourceListAdapter adapter = null;
    private DataManager dataManager = new DataManager();
    private ArrayList<TextView> mTxtReviewList = new ArrayList<TextView>();
    private ArrayList<RelativeLayout> mTxtBgReviewList = new ArrayList<RelativeLayout>();
    private ArrayList<TextView> mTxtDescription = new ArrayList<TextView>();
    private ArrayList<ImageView>mImgArrows = new ArrayList<ImageView>();
    private ArrayList<TextView>mTxtFieldList = new ArrayList<TextView>();
    private ArrayList<TextView>mTxtInput = new ArrayList<TextView>();
    private ArrayList<TextView>mTxtInputBg = new ArrayList<TextView>();
    private ArrayList<TextView>mTxtPercent = new ArrayList<TextView>();
    private CreatePDF mCreatePdf;
    private boolean isPdfProcessing =false;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );
	private void showInput(String text,final int index)
	{
		if(mCurrentPage == GlobalVariable.kBudgetPage3)
			return;
		alert = new AlertDialog.Builder(this); 
		final RelativeLayout inputView = new RelativeLayout(this);	
				
		inputView.setBackgroundColor(color.white);
		
		final EditText input = new EditText(this);
        input.setWidth(7 * (view_budget_1.getWidth()/10) );
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(text);
        input.setHeight(txtInput.getHeight());
        if(GlobalVariable.Enable_type_number_december)
        {
        	input.setInputType(InputType.TYPE_CLASS_PHONE);
        
        }
        
        final Button btn_dialog_enter = new Button(this);       
        btn_dialog_enter.setText("Enter");       
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        btn_dialog_enter.setLayoutParams(params);
        btn_dialog_enter.setHeight(btnNext.getHeight());
        btn_dialog_enter.setWidth((3 * (view_budget_1.getWidth())/10 )-20);        
        inputView.addView(input);
        inputView.addView(btn_dialog_enter); 
       
       

        
        alert.setView(inputView);   
        
  
        //sinput.setFocusable(true);    
        
        
        if(GlobalVariable.Fix_bug_dont_back_one_time)
        {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))  
        .showSoftInput(input, 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
       
        
        btn_dialog_enter.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				btn_dialog_enter.setEnabled(false);
				String temp = input.getText().toString();
				Double num=0.0;
	        	try {
	        		num = Double.parseDouble(temp);
				} catch(NumberFormatException nfe) {
					//mIncome =-1;
					showDialog("Budget Starter", "Please enter a valid number","OK");
					alertDialog.dismiss();
					alertDialog = null;
					getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
					return;
				}
				if(num <= 0.0)
				{
					//mIncome =-1;
					showDialog("Budget Starter", "Please enter a valid number","OK");
					alertDialog.dismiss();
					alertDialog = null;
					getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
					return;
				}
				DecimalFormat format = new DecimalFormat("#,##0.00"); 
				int percent =0;
				if(num > 99999)
					num = 99999.0;
							
				if(index ==  GlobalVariable.kInput)
				{
					mIncome = num;						
		        	mIncome = (double)(Math.round(mIncome*100))/100;				        		        
		    		txtInput.setText(format.format(mIncome));			    		
				}
				else if(index == (mTxtInput.size()-1))
				{
						mIncome = num;						
			        	mIncome = (double)(Math.round(mIncome*100))/100;
			        	mTxtInput.get(mTxtInput.size() -1).setText(format.format(mIncome));	
			    		//txtMonthlyInput.setText(format.format(mIncome));			    		
			    		SetBudgetValue() ;
						SetBudgetedValue();						
						SetBudgetText();
						SetBudgetPercent();
				}
				else
				{
					mBudgetTed = mBudgetTed - mArrayBudgetValue[index];
					mArrayBudgetValue[index]=num;
					mBudgetTed = mBudgetTed + num;
					txtBudgeted.setText(format.format(mBudgetTed));
					
					mRemaining = mIncome - mBudgetTed;
					txtRemainingInput.setText(format.format(mRemaining));
					if(GlobalVariable.Fix_bug_color_red_green_text)
					{
						if(mRemaining == 0)
							txtRemainingInput.setTextColor(Color.GREEN);
						else
							txtRemainingInput.setTextColor(Color.RED);
					}
					
					percent = (int)((num * 100) / mIncome);
					if(mArrayBudgetPercent == null)
						mArrayBudgetPercent = GlobalVariable.kArrayBudgetPercentDefault;
					mArrayBudgetPercent[index]= percent;
					mArrayBudgetValue[index] = num;
					
					
					mTxtInput.get(index).setText(format.format(num));
					mTxtPercent.get(index).setText(percent + "%");
					if(percent != GlobalVariable.kArrayBudgetPercentDefault[index])
						mTxtPercent.get(index).setTextColor(Color.RED);
					else
						mTxtPercent.get(index).setTextColor(Color.BLACK);
										
				}
				if(alertDialog != null)
				{	
					btn_dialog_enter.setEnabled(true);
		    		alertDialog.dismiss();
		    		alertDialog = null;
		    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				}
			}
        });
        //inputView.startAnimation(animLeftToRight);
        alertDialog = alert.create();
        alertDialog.getWindow().setGravity(0x00000053);

       
       
        WindowManager.LayoutParams WMLP = alertDialog.getWindow().getAttributes();
  
    WMLP.x = 0;   //x position
    WMLP.y = -30;   //y position
   
    alertDialog.getWindow().setAttributes(WMLP);

	alertDialog.show();           
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        
     
		
	//IMMResult theReceiver = new IMMResult();
	//theReceiver.ex
    //theReceiver.setParentContext(this);     
	}	
	private void GoBackBudgetPage2()
	{
		txtWarning.setVisibility(View.GONE);
		mCurrentPage = GlobalVariable.kBudgetPage2;	
		btnReviewBudget.setEnabled(true);
		btnReviewBudget.setText("Review Budget");
		//txtWarning.setVisibility(View.VISIBLE);
		resource_pdf_bar.setVisibility(View.GONE);
		mBudgetSubScreen = GlobalVariable.kBudgetPage2;
		for(int i=0;i<mTxtBgReviewList.size();i++)
		{
			mTxtBgReviewList.get(i).setVisibility(View.GONE);
		}
		for(int i=0;i<mImgArrows.size();i++)
		{
			mImgArrows.get(i).setVisibility(View.VISIBLE);					
		}
		if(mCurrentExpanded >-1 )
			mTxtDescription.get(mCurrentExpanded).setVisibility(View.VISIBLE);
		
	}
	private void showDialog(String title, String message,String buttonName)
	{
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(title);
	        builder.setMessage(message)
	               .setCancelable(false)
	               .setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        //MyActivity.this.finish();
	                   }
	               });               
	        dialog = builder.create();
	        dialog.show();
	}
	private void InitTxtInputBg()
	{		
		mTxtInputBg.add((TextView)findViewById(R.id.txtCharityInputBg));    	
		mTxtInputBg.add((TextView)findViewById(R.id.txtSavingsInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtHousingInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtUtilitiesInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtFoodInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtTransInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtClothingInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtMedicalInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtPersonalInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtRecreationInputBg));
		mTxtInputBg.add((TextView)findViewById(R.id.txtDebtsInputBg));	
		mTxtInputBg.add((TextView)findViewById(R.id.txtMonthlyInputBg));
		for(int i=0;i<mTxtInputBg.size();i++)
		{			
			final int index =i;
			mTxtInputBg.get(i).setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v)
	        	{        		        		   	
	        		showInput("" + mArrayBudgetValue[index],index);
	        	}
	        });
		}			
	}
	private void InitTxtInput()
	{		
		mTxtInput.add((TextView)findViewById(R.id.txtCharityInput));    	
		mTxtInput.add((TextView)findViewById(R.id.txtSavingsInput));
		mTxtInput.add((TextView)findViewById(R.id.txtHousingInput));
		mTxtInput.add((TextView)findViewById(R.id.txtUtilitiesInput));
		mTxtInput.add((TextView)findViewById(R.id.txtFoodInput));
		mTxtInput.add((TextView)findViewById(R.id.txtTransInput));
		mTxtInput.add((TextView)findViewById(R.id.txtClothingInput));
		mTxtInput.add((TextView)findViewById(R.id.txtMedicalInput));
		mTxtInput.add((TextView)findViewById(R.id.txtPersonalInput));
		mTxtInput.add((TextView)findViewById(R.id.txtRecreationInput));
		mTxtInput.add((TextView)findViewById(R.id.txtDebtsInput));	
		mTxtInput.add((TextView)findViewById(R.id.txtMonthlyInput));
		
		
	}
	private void InitTxtPercent()
	{		
		mTxtPercent.add((TextView)findViewById(R.id.txtCharityPercent));    	
		mTxtPercent.add((TextView)findViewById(R.id.txtSavingsPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtHousingPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtUtilitiesPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtFoodPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtTransPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtClothingPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtMedicalPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtPersonalPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtRecreationPercent));
		mTxtPercent.add((TextView)findViewById(R.id.txtDebtsPercent));						
	}
	private void InitTxtFieldList()
	{		
		mTxtFieldList.add((TextView)findViewById(R.id.txtCharity));		
		mTxtFieldList.add((TextView)findViewById(R.id.txtSavings));
		mTxtFieldList.add((TextView)findViewById(R.id.txtHousing));
		mTxtFieldList.add((TextView)findViewById(R.id.txtUtilities));
		mTxtFieldList.add((TextView)findViewById(R.id.txtFood));
		mTxtFieldList.add((TextView)findViewById(R.id.txtTrans));
		mTxtFieldList.add((TextView)findViewById(R.id.txtClothing));
		mTxtFieldList.add((TextView)findViewById(R.id.txtMedical));
		mTxtFieldList.add((TextView)findViewById(R.id.txtPersonal));
		mTxtFieldList.add((TextView)findViewById(R.id.txtRecreation));
		mTxtFieldList.add((TextView)findViewById(R.id.txtDebts));
		mTxtFieldList.add((TextView)findViewById(R.id.txtRemaining));
		for(int i=0;i<mTxtFieldList.size();i++)
	    {
	    	final int index = i;
	    	mTxtFieldList.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCurrentPage == GlobalVariable.kBudgetPage3)
						return;
					ExpandClicked(index,true);
				}
			});  
	    }
	}
	private void InitImgArrowList()
	{		
		mImgArrows.add((ImageView)findViewById(R.id.imgCharityArrow)); 		
		mImgArrows.add((ImageView)findViewById(R.id.imgSavingsArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgHousingArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgUtilitiesArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgFoodArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgTransArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgClothingArrow));
		mImgArrows.add((ImageView)findViewById(R.id.imgMedicalArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgPersonalArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgRecreationArrow)); 
		mImgArrows.add((ImageView)findViewById(R.id.imgDebtsArrow));
		mImgArrows.add((ImageView)findViewById(R.id.imgRemainingArrow));
	}
	private void InitTxtDescriptionList()
	{
		
		mTxtDescription.add((TextView)findViewById(R.id.txtCharityDes));		
		mTxtDescription.add((TextView)findViewById(R.id.txtSavingsDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtHousingDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtUtilitiesDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtFoodDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtTransDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtClothingDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtMedicalDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtPersonalDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtRecreationDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtDebtsDes));
		mTxtDescription.add((TextView)findViewById(R.id.txtRemainingDes));
	}
	private void InitTxtBudgetPreview()
	{		
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtCharityReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtSavingReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtHousingReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtUtilitiesReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtFoodReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtTransReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtClothingReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtMedicalReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtPersionalReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtRecreationReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtDebtsReBG));
        mTxtBgReviewList.add((RelativeLayout)findViewById(R.id.txtMonthlyInputReBG)); 
        
        mTxtReviewList.add((TextView)findViewById(R.id.txtCharityRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtSavingRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtHousingRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtUtilitiesRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtFoodRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtTransRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtClothingRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtMedicalRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtPersionalRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtRecreationRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtDebtsRe));
        mTxtReviewList.add((TextView)findViewById(R.id.txtMonthlyInputRe));
        
        
	}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);       
        mContext 				= this;
        view_budget_1 = (RelativeLayout)findViewById(R.id.view_budget_1);        
        info_page = (RelativeLayout)findViewById(R.id.about_page);
        resource_page_1 = (RelativeLayout)findViewById(R.id.resource_page);
        resource_page_2 = (RelativeLayout)findViewById(R.id.resource_page_1);
        resource_progress_bar = (ProgressBar)findViewById(R.id.resource_progress_bar);
        resource_pdf_bar =(ProgressBar)findViewById(R.id.resource_pdf_bar);
        resource_page_1.setVisibility(View.GONE);
        chaoticmoon_link =(RelativeLayout)findViewById(R.id.chaoticmoon_link);
        resource_show_app_link =(RelativeLayout)findViewById(R.id.resource_show_app);
        resource_money_link =(RelativeLayout)findViewById(R.id.resource_money);
        mResourceListView = (ListView)findViewById(R.id.resource_money_list);
        btnGuide = (Button)findViewById(R.id.btn_guide_action);
        btnBack = (Button)findViewById(R.id.btn_back_action);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        txtInput = (TextView)findViewById(R.id.txtInput);
        txtEmail = (EditText)findViewById(R.id.resource_email);
        //budget detail page
        mArrowDownId = getResources().getIdentifier("arrow_down", "drawable", getPackageName() );	
    	mArrowUpId = getResources().getIdentifier("arrow_up", "drawable", getPackageName() );
    	//txtMonthlyInput = (TextView)findViewById(R.id.txtMonthlyInput);
    	txtBudgeted =(TextView)findViewById(R.id.txtBudgetedInput);
        view_budget_2 = (RelativeLayout)findViewById(R.id.view_budget_2);  
        
        view_budget_2.setVisibility(View.GONE);
       // txtRemaining = (TextView)findViewById(R.id.txtRemaining);
        txtRemainingInput = (TextView)findViewById(R.id.txtRemainingInput);
    	InitImgArrowList();
        

    	btnReviewBudget = (Button)findViewById(R.id.btnReviewBudget);
    	txtWarning = (TextView)findViewById(R.id.txtWarning);

    	    	
    	
    	InitTxtDescriptionList();
    	InitTxtFieldList();
    	InitTxtInputBg();
    	InitTxtInput();
    	InitTxtPercent();
    	

       
        
        //anim
        animRightToLeft  		= AnimationUtils.loadAnimation(mContext, R.anim.popup_right_to_left);
        animLeftToRight  		= AnimationUtils.loadAnimation(mContext, R.anim.popup_left_to_right);
        //BudgetReview screen
        InitTxtBudgetPreview();
        
        
        //create OnClickListener
       

        
        //Guide button
        
                                
        btnGuide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//dialog.show();
				showDialog("Budget Starter Guide", "Take the first step toward making a zero-based budget. Start by entering your monthly income.The next screen will show you Dave's recommended percentages for each category. Adjust the numbers until you've spent every dollar on screen before the month begins.", "Get Started");
			}
		});           
        btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isBackPressed();							
			}
		});          
        btnBack.setVisibility(View.GONE);
        btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
					if(mIncome <0)
					{
						showDialog("Budget Starter", "Please enter a valid number","OK");
						return;
					}
					else
					{		
						InitBudgetPercent();				
						SetBudgetValue() ;
						SetBudgetedValue();
						
						SetBudgetText();
						SetBudgetPercent();					
					}
					
					btnBack.setVisibility(View.VISIBLE);
					btnGuide.setVisibility(View.GONE);
					view_budget_1.setVisibility(View.GONE);
					//view_budget_1.startAnimation(animRightToLeft);							
					view_budget_2.setVisibility(View.VISIBLE);
					view_budget_2.startAnimation(animRightToLeft);
					mBudgetSubScreen =GlobalVariable.kBudgetPage2;
					//mCurrentPage = GlobalVariable.kBudgetPage2;
					GoBackBudgetPage2();
					
				
			}
		});  
        
        btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = txtEmail.getText().toString();
				String emailDefault = getString(R.string.txt_enter_email);
				if(email.compareTo("")==0 || email.compareTo(emailDefault)==0)
				{
					showDialog("Budget Starter", "Please enter your email!","OK");
					//resource_progress_bar.setVisibility(View.GONE);
				}
				else
				{
					if( !isValidEmail(email))
					{
						showDialog("Budget Starter", "Please enter valid email!","OK");
					}
					else
					{
						txtEmail.clearFocus();
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
						//resource_progress_bar.setVisibility(View.VISIBLE);
					}

					//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				}
			}
		});  
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            	if( txtEmail.getText().toString().compareTo(getString(R.string.txt_enter_email))==0)
            	{
            		txtEmail.setTextColor(Color.GRAY);            		
            	}
            	else
            		txtEmail.setTextColor(Color.BLACK);
                // Your validation code goes here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        btnReviewBudget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mCurrentPage == GlobalVariable.kBudgetPage2)
				{
					GoToBudgetPreviewPage();
				}
				else
				{
					//showDialog("Budget Starter", "This function is coming soon.","OK");					
						sendMailAsyncTask sendMail = new sendMailAsyncTask();
						sendMail.execute();
					
				}								
			}
		});  
        txtInput.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{        		
        		String temp;
        		if(mIncome <0)
        			temp ="";
        		else
        			temp = "" + Math.round(mIncome);        		
        		showInput(temp,GlobalVariable.kInput);
        	}
        });
        chaoticmoon_link.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{        		
        		if(GlobalVariable.Popup_web)
        		{
        			popup_webview("http://www.chaoticmoon.com/");
        		}else
        		{
        	    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chaoticmoon.com/"));
        	    startActivity(myIntent);
        		}
        	}
        });
        resource_show_app_link.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{        
        		if(GlobalVariable.Popup_web)
        		{
        			popup_webview("http://itunes.apple.com/us/app/ask-dave-ramsey/id380709887?mt=8");
        		}else
        		{
        	    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itunes.apple.com/us/app/ask-dave-ramsey/id380709887?mt=8"));
        	    startActivity(myIntent);
        		}
        	}
        });
        resource_money_link.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v)
        	{     
        		resource_page_1.setVisibility(View.GONE);
        		mCurrentPage = GlobalVariable.kResourcePage2;
        		mResourceSubScreen = GlobalVariable.kResourcePage2;
        		GoToResourceMoneyPage(animRightToLeft);
        	}
        });
        
        mWebView = (WebView) findViewById(R.id.about_webview);  
        mWebView.getSettings().setJavaScriptEnabled(true);
       // btnHome =(ImageButton)findViewById(R.id.home_button);
       // btnMap =(ImageButton)findViewById(R.id.map_button);
        //btnInfo =(ImageButton)findViewById(R.id.info_button);
       // btnHome.setImageDrawable(getResources().getDrawable(R.drawable.home_hl));
        mWebView.loadUrl("file:///android_asset/info.html");
        
        

        info_page.setVisibility(View.GONE);
      //------------------------------------------------------------
		tabs = (TabHost) this.findViewById(R.id.my_tabhost);
		tabs.setup();
		TabSpec tspec1 = tabs.newTabSpec("First Tab");
		tspec1.setIndicator("Budget", getResources().getDrawable(R.drawable.icon_budget));
		tspec1.setContent(R.id.content);
		tabs.addTab(tspec1);
		TabSpec tspec2 = tabs.newTabSpec("Second Tab");
		tspec2.setIndicator("Resource",getResources().getDrawable(R.drawable.icon_resources));
		tspec2.setContent(R.id.content);
		tabs.addTab(tspec2);
		TabSpec tspec3 = tabs.newTabSpec("Third Tab");
		tspec3.setIndicator("Info",getResources().getDrawable(R.drawable.icon_about));
		tspec3.setContent(R.id.content);
		tabs.addTab(tspec3);
		tabs.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.compareTo("Third Tab")==0)
				{
					TabChange(INFO_TAB);					
				}
				else
				{										
					if(tabId.compareTo("Second Tab")==0)
					{
						TabChange(RESOURCE_TAB);					
					}
					else
					{
						TabChange(BUDGET_TAB);						
					}
					
				}
				
			}
		});
		tabs.setCurrentTab(0);
		LoadImageAsyncTask pdfImge = new LoadImageAsyncTask();
		pdfImge.execute();
		mCreatePdf = new CreatePDF();		
		
		
//		 new Thread(new Runnable() {
//			    public void run() {			   
//				   try {
//					while(true)
//					{
//					Thread.sleep(1000);
//					Log.d("log","log thread");
//					}
//				   } catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				   }
//			    }
//			  }).start();
		
		
    }
 
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        recycleData();
        System.gc();
    }  
    private void InitBudgetPercent()
    {
    	int[]array = {10,5,25,5,5,10,2,5,5,5,0};
    	mArrayBudgetPercent = array;
    }
    private void ExpandClicked(int index,boolean isOpen)
    {
    	if(index == mCurrentExpanded)
    	{	mCurrentExpanded = GlobalVariable.kCLOSED_ALL;
    		mImgArrows.get(index).setImageResource(mArrowDownId);
    		mTxtDescription.get(index).setVisibility(View.GONE);
    		mCurrentExpanded = -1;
    	}
    	else
    	{
    		
    		mImgArrows.get(index).setImageResource(mArrowUpId);
    		mTxtDescription.get(index).setVisibility(View.VISIBLE);    		
    		if(mCurrentExpanded >-1 && mCurrentExpanded < mImgArrows.size())
    		{
    			mImgArrows.get(mCurrentExpanded).setImageResource(mArrowDownId);
    			mTxtDescription.get(mCurrentExpanded).setVisibility(View.GONE);
    		}
    		mCurrentExpanded = index;
    	}
    }
   
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           // Log.d(this.getClass().getName(), "back button pressed");
        	if(resource_progress_bar != null)
        		resource_progress_bar.setVisibility(View.GONE);
        	if(isBackPressed())
        		return super.onKeyDown(keyCode, event);
        	return false;
        }
        else
        	return super.onKeyDown(keyCode, event);
    }
    private void GoToBudgetPreviewPage()
    {	    
		if(mTxtBgReviewList != null)
		{
			
			btnReviewBudget.setText("Email a PDF of your budget");
			mCurrentPage = GlobalVariable.kBudgetPage3;
			mBudgetSubScreen = GlobalVariable.kBudgetPage3;
			if(!isPdfProcessing)
			{
				btnReviewBudget.setEnabled(true);
				resource_pdf_bar.setVisibility(View.GONE);
			}
			else
			{
				btnReviewBudget.setEnabled(false);
				resource_pdf_bar.setVisibility(View.VISIBLE);
			}
			for(int i=0;i<mTxtBgReviewList.size();i++)
			{
				mTxtBgReviewList.get(i).setVisibility(View.VISIBLE);
			}
			for(int i=0;i<mImgArrows.size();i++)
			{
				mImgArrows.get(i).setVisibility(View.GONE);					
			}
			if(mCurrentExpanded >-1)
				mTxtDescription.get(mCurrentExpanded).setVisibility(View.GONE);
			
			txtWarning.setVisibility(View.VISIBLE);
			if(mRemaining == 0)
			{
				
				txtWarning.setText("You've successfully created a zero-based budget");
				txtWarning.setTextColor(Color.GREEN);
			}else
			{
			
				txtWarning.setText(R.string.txt_warning);
				txtWarning.setTextColor(Color.RED);
			}
		}		
    }
    public void GoToResourceMoneyPage(Animation anim)
    {
    	if(mResourceListView != null && mResourceListView.getCount()<1)
		{
			LoadingAsyncTask mCurrentLoadingTask = new LoadingAsyncTask();        		        	
			mCurrentLoadingTask.execute();
		}		
		resource_page_2.setVisibility(View.VISIBLE);
		resource_page_2.startAnimation(anim);        				
		btnBack.setVisibility(View.VISIBLE);
		
    }
    public boolean isBackPressed()
    {
    	switch(mCurrentPage)
    	{    	
    		case GlobalVariable.kBudgetPage1:
    			break;
    		case GlobalVariable.kBudgetPage3:    			
    			GoBackBudgetPage2();
    			return false;        			
    		case GlobalVariable.kBudgetPage2:
    			mBudgetSubScreen =GlobalVariable.kBudgetPage1;
				btnBack.setVisibility(View.GONE);
				btnGuide.setVisibility(View.VISIBLE);
				view_budget_2.setVisibility(View.GONE);
				view_budget_1.setVisibility(View.VISIBLE);
				view_budget_1.startAnimation(animLeftToRight);
				mCurrentPage = GlobalVariable.kBudgetPage1;
				DecimalFormat format = new DecimalFormat("#,#00.00");  
				txtInput.setText(format.format(mIncome));
				return false;    			
    		case GlobalVariable.kResourcePage1:    			
    			break;
    		case GlobalVariable.kResourcePage2:
    			resource_page_2.setVisibility(View.GONE);    			
    			mCurrentPage = GlobalVariable.kResourcePage1;
    			btnBack.setVisibility(View.GONE);
    			resource_page_1.setVisibility(View.VISIBLE);
    			resource_page_1.startAnimation(animLeftToRight);
    			mResourceSubScreen = GlobalVariable.kResourcePage1;
    			return false;    			
    		case GlobalVariable.kResourcePage3:
    			break;
    		case GlobalVariable.kInfoPage:
    			break;
    	}
    	return true;
    }
    private void TabChange(int index)
    {
    	resource_progress_bar.setVisibility(View.GONE);
    	switch(index)
    	{
    		case BUDGET_TAB:
    			info_page.setVisibility(View.GONE);	
    			resource_page_1.setVisibility(View.GONE);
    			resource_page_2.setVisibility(View.GONE);
    			if(mBudgetSubScreen == GlobalVariable.kBudgetPage1 )
    			{
    				btnBack.setVisibility(View.GONE);
    				btnGuide.setVisibility(View.VISIBLE);
    				view_budget_2.setVisibility(View.GONE);
    				view_budget_1.setVisibility(View.VISIBLE);
    				view_budget_1.startAnimation(animLeftToRight);
    				mCurrentPage = GlobalVariable.kBudgetPage1;
    				    			
    			}
    			else
    			{
    				if(mBudgetSubScreen == GlobalVariable.kBudgetPage3 )
    				{
    					mCurrentPage = GlobalVariable.kBudgetPage3;
    				}
    				else
    					mCurrentPage = GlobalVariable.kBudgetPage2;
    				btnBack.setVisibility(View.VISIBLE);
    				btnGuide.setVisibility(View.GONE);    				    				
    				view_budget_2.setVisibility(View.VISIBLE);
    				view_budget_2.startAnimation(animLeftToRight);    				
    			}    			
    			break;
    		case RESOURCE_TAB:
    			btnBack.setVisibility(View.GONE);
    			//resource_page_2.setVisibility(View.GONE);
    			btnGuide.setVisibility(View.GONE);
    			switch(mCurrentPage)
    			{
    				case GlobalVariable.kBudgetPage1:
    					view_budget_1.setVisibility(View.GONE);
    					if(mResourceSubScreen == GlobalVariable.kResourcePage1)
    					{    					
    						resource_page_1.setVisibility(View.VISIBLE);
    						resource_page_1.startAnimation(animRightToLeft);
    					}
    					else
    					{
    						
    						GoToResourceMoneyPage(animRightToLeft);
    						//resource_page_2.setVisibility(View.VISIBLE);
    						//resource_page_2.startAnimation(animRightToLeft);
    					}
    					break;
    				case GlobalVariable.kBudgetPage2:
    				case GlobalVariable.kBudgetPage3:
    					view_budget_2.setVisibility(View.GONE);
    					if(mResourceSubScreen == GlobalVariable.kResourcePage1)
    					{
    						resource_page_1.setVisibility(View.VISIBLE);
    						resource_page_1.startAnimation(animRightToLeft);
    					}
    					else
    					{
    						GoToResourceMoneyPage(animRightToLeft);
    						//resource_page_2.setVisibility(View.VISIBLE);
    						//resource_page_2.startAnimation(animRightToLeft);
    					}
    					break;
    				case GlobalVariable.kInfoPage:
    					info_page.setVisibility(View.GONE);
    					if(mResourceSubScreen == GlobalVariable.kResourcePage1)
    					{
    						resource_page_1.setVisibility(View.VISIBLE);
    						resource_page_1.startAnimation(animLeftToRight);
    					}
    					else
    					{
    						GoToResourceMoneyPage(animLeftToRight);
    						//resource_page_2.setVisibility(View.VISIBLE);
    						//resource_page_2.startAnimation(animLeftToRight);
    					}
    					break;    				
    			}    			    			    			    		
    			mCurrentPage = mResourceSubScreen;
    			break;
    		case INFO_TAB:
    			if(mBudgetSubScreen == GlobalVariable.kBudgetPage2 || mBudgetSubScreen == GlobalVariable.kBudgetPage3)
    				view_budget_2.setVisibility(View.GONE);
    			else
    				view_budget_1.setVisibility(View.GONE);
    			resource_page_1.setVisibility(View.GONE);
    			resource_page_2.setVisibility(View.GONE);
    			btnBack.setVisibility(View.GONE);
    			btnGuide.setVisibility(View.GONE);    			
				info_page.setVisibility(View.VISIBLE);
				info_page.startAnimation(animRightToLeft);
				mCurrentPage = GlobalVariable.kInfoPage;
    			break;
    	}
    }
    private void SetBudgetPercent()
    {
    	

		for(int i=0;i<=GlobalVariable.kDEBTS;i++ )
		{
			mTxtPercent.get(i).setText(mArrayBudgetPercent[i]+"%");
			if(mArrayBudgetPercent[i] == GlobalVariable.kArrayBudgetPercentDefault[i])
				mTxtPercent.get(i).setTextColor(Color.BLACK);
			else
				mTxtPercent.get(i).setTextColor(Color.RED);
		}
    }
    private void SetBudgetValue() 
    {
    	//int[] arrayBudgetPercent={10,5,25,5,5,10,2,5,5,5,0};
    	for(int i=0;i<= GlobalVariable.kDEBTS;i++)
    	{
    		mArrayBudgetValue[i] = (double)(Math.round(mIncome * mArrayBudgetPercent[i]))/100;
    	}
    	
		mArrayBudgetValue[GlobalVariable.kDEBTS +1] = mIncome;
	
    }
    private void SetBudgetedValue()
    {
    	
		mBudgetTed = mArrayBudgetValue[0];
		for(int i=1;i<= GlobalVariable.kDEBTS;i++)
		{
			mBudgetTed+= mArrayBudgetValue[i];
		}
		mBudgetTed =(double)(Math.round(mBudgetTed*100))/100;
		mRemaining = mIncome - mBudgetTed;
    }
    private void SetBudgetText()
    {
    	//String str = new DecimalFormat("####0.00").format(mIncome);]
		DecimalFormat format = new DecimalFormat("#,##0.00");
		DecimalFormat format2 = new DecimalFormat("#,##0.00");
		format2.setPositivePrefix("$");
    	//format.setGroupingSize(3);
    	//format.setPositivePrefix("$");
		if(mTxtInput != null)
		{
			for(int i=0;i<mTxtInput.size();i++)
			{
				mTxtInput.get(i).setText(format.format(mArrayBudgetValue[i]));
				mTxtReviewList.get(i).setText(format2.format(mArrayBudgetValue[i]));
			}
		}
		//format.setPositivePrefix("$");
		txtBudgeted.setText(format2.format(mBudgetTed));
		txtRemainingInput.setText(format2.format(mRemaining));
		if(GlobalVariable.Fix_bug_color_red_green_text)
		{
			if(mRemaining == 0)
				txtRemainingInput.setTextColor(Color.GREEN);
			else
				txtRemainingInput.setTextColor(Color.RED);
		}
    }
    private void recycleData()
    {
    	GlobalVariable.mArray=null;
    	GlobalVariable.mArrayPercent=null;
    	GlobalVariable.mPdfTemlete=null;
    	GlobalVariable.mPdfFileName=null;
    	GlobalVariable.mRoot = null;
    	
    }
    private void displayList(ArrayList<ResourceMoneyArticle> list) {	
		
    	if(list != null)
    	{
    		mResourceListView.removeAllViewsInLayout();
    		adapter = new ResourceListAdapter(this,this,list);
    		mResourceListView.setAdapter(adapter);
    		resource_progress_bar.setVisibility(View.GONE);
    	}
				
	}
    protected class LoadingAsyncTask extends AsyncTask<ResourceMoneyArticle, Integer, ArrayList<ResourceMoneyArticle>> 
    {
		@Override
		protected void onPreExecute() {
			resource_progress_bar.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected ArrayList<ResourceMoneyArticle> doInBackground(ResourceMoneyArticle... params) { //sang
			Log.d("budget", "doInBackground");			
			
		 	try
		 	{
		 		if(dataManager == null)
		 			dataManager = new DataManager();
				return dataManager.getResourceMoneyArticles(GlobalVariable.kMONEY_ARTICLES_LINK);				  	
	        }								
			catch (Exception e) 
			{	
				Log.d("budget",e.toString());
				return null;
			}
		}
		@Override
		protected void onPostExecute(ArrayList<ResourceMoneyArticle> list) {			
			onAfterLoading(list);				
		}
    }
	private void onAfterLoading(ArrayList<ResourceMoneyArticle> list)
	{
		if(list != null)
			displayList(list);
		else
		{
			resource_progress_bar.setVisibility(View.GONE);
			showDialog("Budget Starter","Connection error. Can not get data. Please try again!","OK");
		}
	}
	protected class LoadImageAsyncTask extends AsyncTask<Image, Integer, Image> 
    {		
		@Override
		protected Image doInBackground(Image... params) { //sang
			Log.d("budget", "doInBackground");			
			
		 	try
		 	{
		 		Image image = Image.getInstance(this.getClass().getResource("/res/drawable/temlete.png"));
		 		image.scaleAbsolute(595f, 842f);		           
		 		image.setAbsolutePosition(0f, 0f);
				return image;				  	
	        }								
			catch (Exception e) 
			{					
				return null;
			}
		}
		@Override
		protected void onPostExecute(Image image) {	
			GlobalVariable.mPdfTemlete = image;					
		}
    }       
	protected class sendMailAsyncTask extends AsyncTask<Integer,String,File> 
    {		
		@Override
		protected void onPreExecute() {
			isPdfProcessing = true;
			btnReviewBudget.setEnabled(false);
			resource_pdf_bar.setVisibility(View.VISIBLE);
		}
		@Override
		protected File doInBackground(Integer... params) { //sang
			Log.d("budget", "doInBackground");			
			
		 	try
		 	{		 	
				java.net.URL imageURL = this.getClass().getResource("/res/drawable/temlete.png");				
				mCreatePdf.CratePdf(imageURL,mIncome,mBudgetTed,mArrayBudgetValue,mArrayBudgetPercent);	
				return GlobalVariable.mPdfFileName;
	        }								
			catch (Exception e) 
			{					
				return null;
			}
		}
		@Override
		protected void onPostExecute(File file) {
			resource_pdf_bar.setVisibility(View.GONE);
			btnReviewBudget.setEnabled(true);
			isPdfProcessing = false;
			if(mCurrentPage == GlobalVariable.kBudgetPage3)
				sendMail(file.toString());
			//GlobalVariable.mPdfTemlete = image;					
		}
    }       
    private void sendMail(String file)
    {
    	
    	Uri uri = Uri.parse("file://" + file);
    	Intent i = new Intent(Intent.ACTION_SEND);
    	i.putExtra(Intent.EXTRA_SUBJECT, "Email a PDF of your budget");//title
    	//i.putExtra(Intent.EXTRA_TEXT, "Thank you for using Dave Ramsey’s Budget Starter. Your budget is ready to download.\nIf you liked Budget Starter you may also enjoy The Dave Ramsey Show iPhone App. With this app, you can:\n- Listen to the show, LIVE\n- Access the last 30 days of Dave’s show, commercial-free\n- Search and listen to thousands of calls from the show\n- Email Dave on-air\n- And more…\n\nGo to the App Store Now.\nSent from my Android");
    	i.putExtra(Intent.EXTRA_TEXT,
    			 Html.fromHtml(new StringBuilder()
                 .append("<p>Thank you for using Dave Ramsey’s Budget Starter. Your budget is ready to download.<br />" +
                 		"<br />If you liked Budget Starter you may also enjoy <a href=\"http://itunes.apple.com/us/app/ask-dave-ramsey/id380709887?mt=8\">The Dave Ramsey Show Android App</a>. With this app, you can:<br />" +
                 		"<br />- Listen to the show, LIVE<br />- Access the last 30 days of Dave’s show, commercial-free<br />" +
                 		"- Search and listen to thousands of calls from the show<br />" +
                 		"- Email Dave on-air<br />" +
                 		"- And more…<br /></p>")
                 .append("<a href=\"http://itunes.apple.com/us/app/ask-dave-ramsey/id380709887?mt=8\">Go to the Android Market Store Now.</a>")                 
                 .toString())
    	);
    	
    	i.putExtra(Intent.EXTRA_STREAM, uri);
    	i.setType("text/plain");
    	startActivity(Intent.createChooser(i, "Send mail"));
    	
    	//uri = Uri.fromFile(new File(context.getFilesDir(), FILENAME));

    }
    private boolean isValidEmail(String email) 
    {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
//    private boolean isValidEmail(CharSequence target) {
//        try {
//            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//        }
//        catch( NullPointerException exception ) {
//            return false;
//        }
//    }
    
    public void popup_webview(String url)
    {
    	GlobalVariable.temp_link = url;
    	Intent intent = new Intent(getBaseContext(), WebInfo.class);
		startActivity(intent);

    }
 
  

    
}