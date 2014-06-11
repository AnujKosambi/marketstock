package com.marketstock.adapter.inflaters;

import java.sql.Date;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemTitleNData;
import com.marketstock.adapter.IAdapterViewInflater;
import com.marketstock.sebiapplication.MainActivity;
import com.marketstock.sebiapplication.R;

public class CardInflaterQuote implements IAdapterViewInflater<CardItemTitleNData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemTitleNData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		final CardItemTitleNData item = adapter.getTItem(pos);
		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.news_quote_item, parent, false);
			holder = new ViewHolder(convertView,item);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private ToggleButton stock_news_title ;
		private Button explainButton;
		private TextView stock_news_data ;
		private TextView dateView;
		

		public ViewHolder(final View rootView,final CardItemTitleNData data)
		{
			m_rootView = rootView;
			dateView=(TextView)m_rootView.findViewById(R.id.dateView);
			stock_news_title = (ToggleButton) m_rootView.findViewById(R.id.NewsTitle);
			explainButton=(Button) m_rootView.findViewById(R.id.explainButton);
			stock_news_title.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(stock_news_title.isChecked())
					{
						stock_news_data.setMaxLines(1);
					}else
					{
						stock_news_data.setMaxLines(200);
					
					}
					
				}
			});
			stock_news_data = (TextView) m_rootView.findViewById(R.id.NewsData);
			explainButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final Dialog dialog = new Dialog(rootView.getContext());
					dialog.setTitle("Explanation:");
					LinearLayout linearLayout=new LinearLayout(rootView.getContext()); 
					linearLayout.setPadding(25, 25, 25,25);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					ScrollView scrollView = new ScrollView(rootView.getContext());
					
					TextView desc=new  TextView(rootView.getContext());
					desc.setText(data.getLearning().replaceAll("//", "'"));
					desc.setTextSize(18);
					Button ok=new Button(rootView.getContext());
					ok.setText("OK");
					ok.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							
						}
					});
					scrollView.addView(desc,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
					linearLayout.addView(scrollView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
					linearLayout.addView(ok);						
					dialog.setContentView(linearLayout);
					dialog.show();
				}
			});
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemTitleNData item)
		{
			stock_news_title.setTextOn(item.getTitle());
			stock_news_title.setTextOff(item.getTitle());
			if(item.getLearning().equals(""))
				explainButton.setVisibility(View.GONE);
			stock_news_title.setText(item.getTitle());
			stock_news_data.setText(item.getData());
			stock_news_data.setMaxLines(1);
			stock_news_title.setChecked(true);
			Date date=new Date(MainActivity.installed);
			dateView.setText(new Date(date.getTime()+(item.getDay()-1)*24*60*60*1000).toString());
			
		}
	}
}
