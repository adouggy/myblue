package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.bean.Location;
import net.synergyinfosys.android.myblue.service.LocationService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LocationDetailActivity extends Activity implements OnClickListener {

	private EditText mEdtName = null;
	private EditText mEdtlatitude = null;
	private EditText mEdtLongtitude = null;
	private EditText mEdtDescription = null;
	private Button mBtnSave = null;
	private Button mBtnDelete = null;

	private Location mLocation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location_detail);

		mEdtName = (EditText) findViewById(R.id.txt_location_detail_name);
		mEdtlatitude = (EditText) findViewById(R.id.txt_location_detail_latitude);
		mEdtLongtitude = (EditText) findViewById(R.id.txt_location_detail_longtitude);
		mEdtDescription = (EditText) findViewById(R.id.txt_location_detail_description);

		mBtnSave = (Button) findViewById(R.id.btn_location_detail_save);
		mBtnDelete = (Button) findViewById(R.id.btn_location_detail_delete);

		mBtnSave.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Location loc = bundle.getParcelable("location");
			mEdtName.setText(loc.getName());
			mEdtlatitude.setText(loc.getLatitude() + "");
			mEdtLongtitude.setText(loc.getLongitude() + "");
			mEdtDescription.setText(loc.getDescription());
			this.mLocation = loc;
			this.mBtnDelete.setVisibility(View.VISIBLE);
		} else {
			this.mBtnDelete.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_detail, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_location_detail_save:
			mLocation.setName(mEdtName.getText().toString());
			mLocation.setDescription(mEdtDescription.getText().toString());
			mLocation.setLatitude(Double.parseDouble(mEdtlatitude.getText().toString()));
			mLocation.setLongitude(Double.parseDouble(mEdtLongtitude.getText().toString()));
			if (mLocation.isValid()) {
				LocationService.INSTANCE.updateLocation(mLocation);
			}
			LocateActivity.refresh();
			this.finish();
			break;
		case R.id.btn_location_detail_delete:
			if (mLocation != null)
				LocationService.INSTANCE.delLocation(mLocation.getId());
			
			LocateActivity.refresh();
			this.finish();
			break;
		default:
			break;
		}
	}
}
