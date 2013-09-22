package net.synergyinfosys.android.myblue.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class MyHelper {
	
	protected Activity mActivity;
	protected View mView;
	
	public MyHelper(Activity act){
		this.mActivity = act;
	}
	
	/**
	 *  the object implemented this interface should hold a Activity instance.
	 *  this method is for inflating a view, after it, getView should works.
	 *  
	 * @throws IllegalStateException
	 */
	public void onCreate(int layoutId) throws IllegalStateException {
		if( this.mActivity == null )
			throw new IllegalStateException("call initial and set a acitiviy first.");
		
		mView = LayoutInflater.from(mActivity).inflate(layoutId, null);
	}

	/**
	 *  this method should call after onCreate, 
	 *  view instance is a must
	 *  then  onPostCreate do some widget/adapter initiation.
	 *  
	 * @throws IllegalStateException
	 */
	public void onPostCreate() throws IllegalStateException {
		if( this.mView == null ){
			throw new IllegalStateException("call onCreate before this method.");
		}
	}

	/**
	 * should be called after onCreate.
	 * 
	 * @return get a view instance.
	 * @throws IllegalStateException
	 */
	public View getView() throws IllegalStateException {
		if( this.mView == null ){
			throw new IllegalStateException("call onCreate before this method.");
		}
		return this.mView;
	}
	
}
