package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.TutorialHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialFragment extends Fragment {

	public static final String TAG = "TutorialFragment";
	private TutorialHelper mHelper = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new TutorialHelper(getActivity());
		mHelper.onCreate( R.layout.fragment_tutorial_main );
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
}
