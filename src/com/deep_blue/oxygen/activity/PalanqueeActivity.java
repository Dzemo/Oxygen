package com.deep_blue.oxygen.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.ListePlongeurs;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;

public class PalanqueeActivity extends Activity {

	private Palanquee palanquee;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palanquee);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		
		ListePlongeurs result = intent.getParcelableExtra(IntentKey.PALANQUEE_COURANTE.toString());
		
		if(result == null){
			System.out.println("Pas de palanquée selectionnée");
		}
		else{
			System.out.println("Palanquée séléctionnée "+result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.palanquee, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
