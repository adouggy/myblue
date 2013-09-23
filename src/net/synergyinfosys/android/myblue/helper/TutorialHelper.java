package net.synergyinfosys.android.myblue.helper;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.adapter.TutorialAdapter;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public class TutorialHelper extends MyHelper{
	
	public static final String TAG = "TutorialHelper";
	private ViewFlow viewFlow;
	private static SlidingMenu sSlidingMenu = null;
	
	public TutorialHelper(Activity act){
		super( act );
	}
	
	public static void setSlidingMenu( SlidingMenu menu ){
		sSlidingMenu = menu;
	}
	
	@Override
	public void onCreate(int layoutId){
		super.onCreate( layoutId );
	}
	
	@Override
	public void onPostCreate(){
		super.onPostCreate();
		
		viewFlow = (ViewFlow)getView().findViewById(R.id.viewflow);
		TutorialAdapter adapter = new TutorialAdapter(mActivity);
		viewFlow.setAdapter(adapter);
		TitleFlowIndicator indicator = (TitleFlowIndicator) getView().findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);
		
		viewFlow.setSelection(0);
		if( sSlidingMenu != null  ){
			sSlidingMenu.setTouchModeAbove( SlidingMenu.TOUCHMODE_FULLSCREEN );
		}
		
		viewFlow.setOnViewSwitchListener(new ViewSwitchListener(){

			@Override
			public void onSwitched(View view, int position) {
				Log.i(TAG, "position:" + position);
				if( sSlidingMenu != null ){
					if( position == 0 ){
						sSlidingMenu.setTouchModeAbove( SlidingMenu.TOUCHMODE_FULLSCREEN );
					}else{
						sSlidingMenu.setTouchModeAbove( SlidingMenu.TOUCHMODE_NONE );
					}
				}else{
					Log.i(TAG, "sliding menu is null.." );
				}
			}}
		);
	}
}
