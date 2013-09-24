package net.synergyinfosys.android.myblue.fragment;


import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.CallRecordHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallRecordFragment extends Fragment  implements ITitle{
	
	private CallRecordHelper mHelper = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHelper = new CallRecordHelper(getActivity());
		mHelper.onCreate(R.layout.activity_call_record);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mHelper.onPostCreate();
		
		return mHelper.getView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getTitle() {
		return "通话记录";
	}
}
