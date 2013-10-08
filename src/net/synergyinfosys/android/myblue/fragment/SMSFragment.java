package net.synergyinfosys.android.myblue.fragment;


import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.SMSHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SMSFragment extends Fragment  implements ITitle{
	
	private SMSHelper mHelper = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHelper = new SMSHelper(getActivity());
		mHelper.onCreate(R.layout.fragment_sms_main);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return mHelper.getView();
	}
	
	@Override
	public void onActivityCreated(
			Bundle savedInstanceState) {
		mHelper.onPostCreate();
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getTitle() {
		return "短信";
	}
}
