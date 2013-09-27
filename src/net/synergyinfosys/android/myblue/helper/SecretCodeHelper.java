package net.synergyinfosys.android.myblue.helper;

import java.util.ArrayList;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.SecretCode;
import net.synergyinfosys.android.myblue.dao.SecretCodeDao;
import net.synergyinfosys.android.myblue.util.StringUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecretCodeHelper extends MyHelper implements OnClickListener {

	public static final String TAG = "SecretCodeHelper";

	private static TextView mTxtCode = null;
	private Button mBtn = null;

	public SecretCodeHelper(Activity act) {
		super(act);
	}

	@Override
	public void onCreate(int layoutId) {
		super.onCreate(layoutId);
	}
	
	@Override
	public void onPostCreate() {
		super.onPostCreate();

		mTxtCode = (TextView) getView().findViewById(R.id.txt_secret_code);
		mBtn = (Button) getView().findViewById(R.id.btn_secret_code);
		mBtn.setOnClickListener(this);

		refresh();
	}

	public static void refresh() {
		ArrayList<SecretCode> list = SecretCodeDao.getInstance().getAll();
		if (list != null && list.size() > 0) {
			mTxtCode.setText(list.get(0).getCode());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_secret_code:
			final EditText input = new EditText(mActivity);
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setTitle("输入新的密码").setIcon(android.R.drawable.ic_dialog_info).setView(input).setNegativeButton("取消", null);

			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String str = input.getText().toString();
					if (StringUtil.INSTACE.isNoneBlank(str)) {
						SecretCode s = new SecretCode();
						s.setCode(str);
						SecretCodeDao.getInstance().insert(s);
						SecretCodeHelper.refresh();
					}
				}
			});

			builder.show();

			break;
		}
	}
}
