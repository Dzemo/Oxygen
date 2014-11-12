package com.deep_blue.oxygen.listener;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

import com.deep_blue.oxygen.activity.FicheSecuriteActivity;
import com.deep_blue.oxygen.activity.IntentKey;
import com.deep_blue.oxygen.activity.PalanqueeActivity;
import com.deep_blue.oxygen.model.Palanquee;

public class PalanqueeListOnClickListener implements OnClickListener{

	private Palanquee clickedPalanquee;
	private FicheSecuriteActivity ficheSecuriteActivity;
	
	public PalanqueeListOnClickListener(Palanquee palanquee, FicheSecuriteActivity ficheSecuriteActivity){
		this.clickedPalanquee = palanquee;
		this.ficheSecuriteActivity = ficheSecuriteActivity;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(ficheSecuriteActivity, PalanqueeActivity.class);		
		intent.putExtra(IntentKey.PALANQUEE_COURANTE.toString(), (Parcelable)clickedPalanquee.getPlongeurs());
		
		ficheSecuriteActivity.startActivity(intent);
	}

}
