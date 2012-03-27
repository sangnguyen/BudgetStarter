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
import android.webkit.WebViewClient;
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

public class WebInfo extends Activity
{	
	WebView webinfo;
	Button back;
	ProgressBar loading;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_info);    
        
        loading = (ProgressBar)findViewById(R.id.progressbar);
        loading.setVisibility(View.VISIBLE);
        
        webinfo = (WebView) findViewById(R.id.web_info);
        webinfo.getSettings().setJavaScriptEnabled(true);
        webinfo.getSettings().setLoadWithOverviewMode(true);
        webinfo.getSettings().setUseWideViewPort(true);
        webinfo.getSettings().setSupportZoom(true); 
        webinfo.getSettings().setBuiltInZoomControls(true);
        
        
        webinfo.setWebViewClient(new WebViewClient() {
        	public void onPageFinished(WebView view, String url) {
        		loading.setVisibility(View.GONE);
        		}
        	 });
        
        loadWeb loadweb = new loadWeb();
        loadweb.execute(0);
        
        back = (Button)findViewById(R.id.btn_back_web);       
        back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();							
			}
		});          
    }
    
    protected class loadWeb extends AsyncTask<Integer,String,File> 
    {		
		@Override
		protected void onPreExecute() {
			//loading.setVisibility(View.VISIBLE);
			//webinfo.setVisibility(View.GONE);
		}
		@Override
		protected File doInBackground(Integer... params) { //sang
			
			webinfo.loadUrl(GlobalVariable.temp_link);
				return null;
			
		}
		@Override
		protected void onPostExecute(File file) {
			//loading.setVisibility(View.GONE);
			//webinfo.setVisibility(View.VISIBLE);
		}
    }       
 
    @Override
    protected void onDestroy()
    {
        super.onDestroy();       
        System.gc();
    }  
    
}