package net.synergyinfosys.android.myblue.command;

import net.synergyinfosys.android.myblue.util.SMSUtil;

public class HideSMSCommand implements ICommand {

	private boolean isHide = true;
	private String number = null;

	public HideSMSCommand(boolean isHide, String number) {
		this.isHide = isHide;
		this.number = number;
	}

	@Override
	public void execute() {
		SMSUtil.INSTANCE.hideSMS(isHide, number);
	}

}
