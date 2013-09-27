package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.HomeViewFlowAdapter;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;

@Deprecated
public class HomeFlowActivity extends Activity {
	private ViewFlow viewFlow;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.setTitle(R.string.title_main);
		setContentView(R.layout.activity_home_flow);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		HomeViewFlowAdapter adapter = new HomeViewFlowAdapter(this);
		viewFlow.setAdapter(adapter);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);
	}

	/*
	 * If your min SDK version is < 8 you need to trigger the
	 * onConfigurationChanged in ViewFlow manually, like this
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}

}
