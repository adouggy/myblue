package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.LocationListAdapter;
import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.service.LocationService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LocationActivity extends Activity implements OnClickListener {

	public static final String TAG = "LocateActivity";
	private Button mBtnAdd = null;
	private static LocationListAdapter mLocationListAdapter;
	private static TextView mTxtCurrentLocation = null;
	private static Location mLocation = null;
	private static ListView mList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_locate);

		mBtnAdd = (Button) findViewById(R.id.btn_locate_add_current);
		mBtnAdd.setOnClickListener(this);

		mTxtCurrentLocation = (TextView) findViewById(R.id.txt_locate_current);

		mList = (ListView) findViewById(R.id.list_locate_location);
		mLocationListAdapter = new LocationListAdapter(this.getApplicationContext(), LocationService.INSTANCE.getAllLocation());
		mList.setAdapter(mLocationListAdapter);
	}

	public static void refreshCurrentLocation(String locationDesc) {
		if (mTxtCurrentLocation != null)
			mTxtCurrentLocation.setText(locationDesc);
	}

	public static void setCurrentLocation(Location loc) {
		mLocation = loc;
	}

	public static void refresh() {
		LocationListAdapter.setData(LocationService.INSTANCE.getAllLocation());
		mLocationListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locate, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_locate_add_current:
			if (mLocation != null) {
				LocationService.INSTANCE.addLocation(mLocation);
				refresh();
			}
			break;
		}
	}

}
