package net.synergyinfosys.android.myblue.adapter;

import net.synergyinfosys.android.myblue.R;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TutorialAdapter extends BaseAdapter implements TitleProvider {
	private final int VIEW_COUNT = 4;
	private final String[] names = { "第一步", "第二步", "第三步", "开始咯" };

	private LayoutInflater mInflater;

	public TutorialAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_COUNT;
	}

	@Override
	public int getCount() {
		return VIEW_COUNT;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * no idea why it is reversed???
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int view = getItemViewType(position);
		if (convertView == null) {
			switch (view) {
			case 3:
				convertView = mInflater.inflate(R.layout.fragment_tutorial_step_1, null);
				break;
			case 2:
				convertView = mInflater.inflate(R.layout.fragment_tutorial_step_2, null);
				break;
			case 1:
				convertView = mInflater.inflate(R.layout.fragment_tutorial_step_3, null);
				break;
			case 0:
				convertView = mInflater.inflate(R.layout.fragment_tutorial_step_4, null);
			}
		}
		return convertView;
	}

	public String getTitle(int position) {
		return names[position];
	}

}
