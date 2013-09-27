package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.DebugHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DebugFragment extends Fragment implements ITitle {

	public static final String TAG = "DebugFragment";
	private DebugHelper mHelper = null;

	@Override
	public void onCreate(
			Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new DebugHelper(getActivity());
		mHelper.onCreate(R.layout.fragment_about); // just re-use it.
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		mHelper.onPostCreate();
		return mHelper.getView();
	}

	@Override
	public void onSaveInstanceState(
			Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getTitle() {
		return "Debug";
	};
}
