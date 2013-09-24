package net.synergyinfosys.android.myblue.ui.cards;

import net.synergyinfosys.android.myblue.R;
import net.synergyinfosys.android.myblue.bean.Contact;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyImageCard extends Card {

	private Contact mContact = null;
	private String mText = null;
	private Bitmap mBmp = null;
	
	public MyImageCard(String title, int image, Contact c){
		super(title, image);
		this.mContact = c;
	}
	
	public Contact getContact(){
		return this.mContact;
	}
	
	public void setText(String text){
		this.mText = text;
	}
	
	public void setPhoto(Bitmap bmp){
		this.mBmp = bmp;
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_picture, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		if( mBmp == null )
			((ImageView) view.findViewById(R.id.imageView1)).setImageResource(image);
		else
			((ImageView) view.findViewById(R.id.imageView1)).setImageBitmap( mBmp );
		if( mText != null ){
			((TextView) view.findViewById(R.id.description)).setText(mText);
		}
		
		return view;
	}

}
