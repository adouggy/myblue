package net.synergyinfosys.android.myblue.fragment;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.helper.SecretCodeHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SecretCodeFragment extends Fragment implements ITitle {

	public static final String TAG = "SecretCodeFragment";
	private SecretCodeHelper mHelper = null;

	@Override
	public void onCreate(
			Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHelper = new SecretCodeHelper(getActivity());
		mHelper.onCreate(R.layout.fragment_secret_code);
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
		return "Secret Code";
	}
}
