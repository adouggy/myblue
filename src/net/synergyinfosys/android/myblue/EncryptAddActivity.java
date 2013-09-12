package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.bean.Encrypt;
import net.synergyinfosys.android.myblue.dao.EncryptDao;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EncryptAddActivity extends Activity implements OnClickListener {

	private Button mBtnDone = null;
	private Button mBtnDel = null;
	private EditText mEdtName = null;
	private EditText mEdtPassword = null;
	private EditText mEdtComment = null;
	private Encrypt mEncrypt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_encrypt_add);

		mBtnDone = (Button) findViewById(R.id.btn_encrypt_add_done);
		mBtnDone.setOnClickListener(this);
		mBtnDel = (Button) findViewById(R.id.btn_encrypt_add_del);
		mBtnDel.setOnClickListener(this);

		mEdtName = (EditText) findViewById(R.id.txt_encrypt_add_name);
		mEdtPassword = (EditText) findViewById(R.id.txt_encrypt_add_password);
		mEdtComment = (EditText) findViewById(R.id.txt_encrypt_add_comment);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			mEncrypt = (Encrypt) bundle.get("encrypt");
		}
		
		if (mEncrypt != null) {
			this.mBtnDel.setVisibility(View.VISIBLE);
			this.mEdtName.setText(mEncrypt.getName());
			this.mEdtComment.setText(mEncrypt.getComment());
			this.mEdtPassword.setText(mEncrypt.getPassword());
		} else {
			this.mBtnDel.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.encrypt_add, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_encrypt_add_done:
			Encrypt e = new Encrypt();
			e.setName(this.mEdtName.getText().toString());
			e.setPassword(this.mEdtPassword.getText().toString());
			e.setComment(this.mEdtComment.getText().toString());

			if (e.isValid()) {
				if (this.mEncrypt == null) {
					EncryptDao.getInstance().insert(e);
				} else {
					e.setId(this.mEncrypt.getId());
					EncryptDao.getInstance().update(e);
				}
			}
			EncryptActivity.refreshEncrypt();
			this.finish();
			break;
		case R.id.btn_encrypt_add_del:
			EncryptDao.getInstance().remove(this.mEncrypt.getId());
			EncryptActivity.refreshEncrypt();
			this.finish();
			break;
		}
	}

}
