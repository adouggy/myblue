package net.synergyinfosys.android.myblue.command;

import net.synergyinfosys.android.myblue.util.MDMUtil;
import android.app.admin.DevicePolicyManager;

public class LockDeviceCommand implements ICommand{
	
	private boolean isLock = true;
	
	public LockDeviceCommand(boolean isLock){
		this.isLock = isLock;
	}

	@Override
	public void execute() {
		DevicePolicyManager dpm = MDMUtil.INSTANCE.getPolicyManager();
		if (dpm != null && MDMUtil.INSTANCE.isActive()) {
			
			if( isLock ){
				dpm.resetPassword("0214", 0);
//				dpm.lockNow();
			}else{
				dpm.resetPassword("", 0);
			}
			
		} else {
			MDMUtil.INSTANCE.log( "Can't lock screen, device policy manager error");
		}
	}

}

