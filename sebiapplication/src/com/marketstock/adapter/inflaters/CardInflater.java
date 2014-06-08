package com.marketstock.adapter.inflaters;

import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.IAdapterViewInflater;
import com.marketstock.sebiapplication.R;
import com.marketstock.adapter.BaseInflaterAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardInflater implements IAdapterViewInflater<CardItemData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView stock_news ;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			stock_news = (TextView) m_rootView.findViewById(R.id.stock_news);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item)
		{
			stock_news.setText(item.getText1());
			
		}
	}
}
