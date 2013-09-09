package net.synergyinfosys.android.myblue.command;

import net.synergyinfosys.android.myblue.util.MDMUtil;

public class HideIconCommand implements ICommand {

	private boolean isHide = true;

	public HideIconCommand(boolean isHide) {
		this.isHide = isHide;
	}

	@Override
	public void execute() {
		MDMUtil.INSTANCE.hideIcon(isHide, "net.synergyinfosys.android.myblue.HomeActivity");
	}

}
