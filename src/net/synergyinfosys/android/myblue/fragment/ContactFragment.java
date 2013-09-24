package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.ContactHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactFragment extends Fragment  implements ITitle{

	public static final String TAG = "ContactFragment";
	private static ContactHelper mHelper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new ContactHelper(getActivity());
		mHelper.onCreate( R.layout.activity_contact );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mHelper.onPostCreate();
		return mHelper.getView();
	}
	
	public static void refresh(){
		ContactHelper.refresh();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getTitle() {
		return "联系人";
	}
}
