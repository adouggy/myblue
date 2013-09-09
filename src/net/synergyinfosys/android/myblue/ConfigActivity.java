package net.synergyinfosys.android.myblue;

import net.synergyinfosys.android.myblue.command.HideSMSCommand;
import net.synergyinfosys.android.myblue.command.LockDeviceCommand;
import net.synergyinfosys.android.myblue.util.MDMUtil;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConfigActivity extends Activity implements OnClickListener{
    public static final String PREF_IS_NEAR = "isNear";
    
    private SharedPreferences pref = null; 
    private SharedPreferences.Editor editor = null;
    
    Button btnStartService; 
    Button btnStopService;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        btnStartService =(Button) findViewById(R.id.button_start_service);
        btnStartService.setOnClickListener(this);
        
        btnStopService =(Button) findViewById(R.id.button_stop_service);
        btnStopService.setOnClickListener(this);
        
        pref = getSharedPreferences("myblue.xml", MODE_PRIVATE);
        editor = pref.edit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch( v.getId()){
		case R.id.button_start_service:
			near();
			break;
		case R.id.button_stop_service:
			far();
			break;
		}
	}
	
	private void near(){
		if( !pref.getBoolean(PREF_IS_NEAR, true) ){
//			MDMUtil.INSTANCE.hideContact("李雅子", false);
//			MDMUtil.INSTANCE.hidePhoto(false);
			
			LockDeviceCommand uCmd = new LockDeviceCommand(false);
        	MDMUtil.INSTANCE.addCommand(uCmd);
        	
//        	HideIconCommand hideCmd = new HideIconCommand(false);
//        	MDMUtil.INSTANCE.addCommand(hideCmd);
        	
        	HideSMSCommand hideSMSCmd = new HideSMSCommand(false, "10086");
        	MDMUtil.INSTANCE.addCommand(hideSMSCmd);
        	
        	MDMUtil.INSTANCE.runAll();
            MDMUtil.INSTANCE.clearCommand();
		}
		editor.putBoolean(PREF_IS_NEAR, true);
		editor.commit();
	}
	
	private void far(){
		if( pref.getBoolean(PREF_IS_NEAR, false) ){
//			MDMUtil.INSTANCE.hideContact("李雅子", true);
//			MDMUtil.INSTANCE.hidePhoto(true);
			
			LockDeviceCommand lCmd = new LockDeviceCommand(true);
        	MDMUtil.INSTANCE.addCommand(lCmd);
        	
//        	HideIconCommand hideCmd = new HideIconCommand(true);
//        	MDMUtil.INSTANCE.addCommand(hideCmd);
        	
        	HideSMSCommand hideSMSCmd = new HideSMSCommand(true, "10086");
        	MDMUtil.INSTANCE.addCommand(hideSMSCmd);
        	
        	MDMUtil.INSTANCE.runAll();
            MDMUtil.INSTANCE.clearCommand();
		}
		editor.putBoolean(PREF_IS_NEAR, false);
		editor.commit();
	}

}
