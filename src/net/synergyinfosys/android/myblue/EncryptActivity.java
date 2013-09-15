package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.adapter.EncryptListAdapter;
import net.synergyinfosys.android.myblue.dao.EncryptDao;
import net.synergyinfosys.android.myblue.view.MyListView;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class EncryptActivity extends Activity implements OnClickListener {

	private TextView mBtnAdd = null;
	private MyListView mList = null;
	private static EncryptListAdapter mEncryptAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_encrypt);

		mBtnAdd = (TextView) findViewById(R.id.txt_encrypt_add);
		mBtnAdd.setOnClickListener(this);

		mList = (MyListView) findViewById(R.id.list_encrypt);
		mEncryptAdapter = new EncryptListAdapter(this.getApplicationContext(), EncryptDao.getInstance().getAll());
		mList.setAdapter(mEncryptAdapter);

	}

	public static void refreshEncrypt() {
		EncryptListAdapter.setData(EncryptDao.getInstance().getAll());
		if( mEncryptAdapter!=null )
			mEncryptAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.encrypt, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		Intent startAddIntent = new Intent();
		ComponentName cn = new ComponentName(this.getApplicationContext(), EncryptAddActivity.class);
		startAddIntent.setComponent(cn);
		startActivity(startAddIntent);
	}

}
