package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.SMSContactAdapter;
import net.synergyinfosys.android.myblue.adapter.SMSListAdapter;
import net.synergyinfosys.android.myblue.dao.SMSDao;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public class SMSHelper extends MyHelper {

	public static final String TAG = "SMSHelper";

	private ViewFlow viewFlow;

	public SMSHelper(Activity act) {
		super(act);
	}

	@Override
	public void onCreate(
			int layoutId) throws IllegalStateException {
		super.onCreate(layoutId);
	}

	@Override
	public void onPostCreate() throws IllegalStateException {
		super.onPostCreate();

		TitleFlowIndicator indicator = (TitleFlowIndicator) getView().findViewById(R.id.viewflowindic);
		SMSContactAdapter adapter = new SMSContactAdapter(mActivity);
		indicator.setTitleProvider(adapter);
		
		viewFlow = (ViewFlow) getView().findViewById(R.id.viewflow);
		viewFlow.setAdapter(adapter);
		viewFlow.setFlowIndicator(indicator);
		viewFlow.setSelection(0);
		viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void onSwitched(
					View view,
					int position) {
				Log.i(TAG,
						"position:" + position);

			}
		});
	}

	public static void refresh() {
		SMSListAdapter.setData(SMSDao.getInstance().getAll());
	}
}
