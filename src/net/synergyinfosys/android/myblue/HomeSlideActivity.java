package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.androidservice.LongLiveService;
import net.synergyinfosys.android.myblue.fragment.ITitle;
import net.synergyinfosys.android.myblue.fragment.MenuFragment;
import net.synergyinfosys.android.myblue.helper.TutorialHelper;
import net.synergyinfosys.android.myblue.util.WebUtil;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

public class HomeSlideActivity extends SlidingFragmentActivity {
	protected ListFragment mFrag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_slide);
		setTitle(R.string.title_main);
		setBehindContentView(R.layout.menu_frame);
		
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new MenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setSlidingActionBarEnabled(false);
		
		this.startService(new Intent(this, LongLiveService.class));
		
		TutorialHelper.setSlidingMenu(sm);
	}

	public void switchContent(Fragment fragment) {
		if( fragment instanceof ITitle ){
			setTitle( ((ITitle) fragment).getTitle() );
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.home_slide_behind, fragment).commit();
		getSlidingMenu().showContent();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.title_bar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_home_page:
			WebUtil.goToHomePage(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
