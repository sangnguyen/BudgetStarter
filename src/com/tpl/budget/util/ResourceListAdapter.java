package com.tpl.budget.util;
import java.util.ArrayList;
import java.util.List;


import com.tpl.budget.MainActivity;
import com.tpl.budget.R;
import com.tpl.budget.WebInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResourceListAdapter extends BaseAdapter
{
	
	private LayoutInflater mInflater;
	private List<ResourceMoneyArticle> mList = null;
	private Activity mActivity;
	private Context mContext;
	public ResourceListAdapter(Activity activity, Context context,ArrayList<ResourceMoneyArticle>list)
	{		
		mActivity = activity;
		mList   = list;	
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	
	public class ViewHolder
	{
		RelativeLayout item;
		TextView txtTile;
		TextView txtDate;
		TextView txtDes;		
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
		final ViewHolder holder;
		if (view == null)
		{
			holder 				= new ViewHolder();
			view 				= mInflater.inflate(R.layout.resource_money_articles_item, null);
			holder.txtTile 	= (TextView)view.findViewById(R.id.txtTile);
			holder.txtDate  = (TextView)view.findViewById(R.id.txtDate);
			holder.txtDes  = (TextView)view.findViewById(R.id.txtDes);
			holder.item =  (RelativeLayout)view.findViewById(R.id.resource_money_articles_cell);
			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}
		holder.item.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				GlobalVariable.temp_link = mList.get(position).getLink();
				Intent intent = new Intent(mContext, WebInfo.class);
				mContext.startActivity(intent);
//				try{
//					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(position).getLink()));
//					mActivity.startActivity(myIntent);
//				}catch(Exception ex)
//				{
//					
//				}
				
			}
		});		
		holder.txtTile.setTag(position);
		holder.txtDate.setTag(position);
		holder.txtDes.setTag(position);
		holder.txtTile.setText(mList.get(position).getTitle());
		holder.txtDate.setText(mList.get(position).getDate());
		holder.txtDes.setText(mList.get(position).getDes());
		return view;
	}
	
	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	 
}