package net.synergyinfosys.android.myblue.ui.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.synergyinfosys.android.myblue.adao.ContactADao;
import net.synergyinfosys.android.myblue.bean.Contact;
import net.synergyinfosys.android.myblue.dao.ContactDao;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class MediaCache implements IUIDataCache {
	
	public static final String TAG = "MediaCache";
	
	private List<String> mContactList = null;
	private HashMap<String, Drawable> mPhoto = null;
	private Drawable mMyPhoto = null;
	private RotateAnimation rotateAnimation_in, rotateAnimation_out;

	private static class SingletonHolder {
		public static final MediaCache INSTANCE = new MediaCache();
	}

	public static MediaCache getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	@Override
	public void initialData(Context ctx) {
		mContactList = new ArrayList<String>();
		mPhoto = new HashMap<String, Drawable>();
		
		ArrayList<Contact> list = ContactDao.getInstance().getContactAll();
		for( Contact c : list ){
			mContactList.add( c.getName() );
			mPhoto.put(c.getName(), new BitmapDrawable(ctx.getResources(), ContactADao.INSTANCE.getFacebookPhoto(c.getNumber()) ));
		}
		mMyPhoto = new BitmapDrawable(ctx.getResources(), ContactADao.INSTANCE.getFacebookPhoto( "13601303722" ) );
		
		rotateAnimation_in = new RotateAnimation(0, 135,
		        Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation_in.setInterpolator(new LinearInterpolator());
		rotateAnimation_in.setDuration(500);
		rotateAnimation_in.setRepeatCount(0);
		rotateAnimation_in.setFillAfter(true);
		
		rotateAnimation_out = new RotateAnimation(0, 315,
		        Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation_out.setInterpolator(new LinearInterpolator());
		rotateAnimation_out.setDuration(500);
		rotateAnimation_out.setRepeatCount(0);
		rotateAnimation_out.setFillAfter(true);
		
	}
	
	public List<String> getContactNames(){
		return mContactList;
	}
	
	public Map<String, Drawable> getPhotoMap(){
		return mPhoto;
	}
	
	public Drawable getMyPhoto(){
		return mMyPhoto;
	}
	
	public RotateAnimation getAnimationIncoming(){
		return rotateAnimation_in;
	}
	
	public RotateAnimation getAnimationOutgoing(){
		return rotateAnimation_out;
	}
	
}
