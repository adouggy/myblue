package net.synergyinfosys.android.myblue.command;

import net.synergyinfosys.android.myblue.adao.SMSADao;

public class HideSMSCommand implements ICommand {

	private boolean isHide = true;
	private String number = null;

	public HideSMSCommand(boolean isHide, String number) {
		this.isHide = isHide;
		this.number = number;
	}

	@Override
	public void execute() {
		SMSADao.INSTANCE.hideSMS(isHide, number);
	}

}
