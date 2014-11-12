package com.deep_blue.oxygen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Utilisateur;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Intent i = getIntent();
		Utilisateur utilisateur = (Utilisateur) i.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		System.out.println("Utilisateur courant:"+utilisateur);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		
		return super.onOptionsItemSelected(item);
	}
	
	public void onCliqueSynch(){
		System.out.println("clique synch");
	}
	
	public void onCliqueList(){
		System.out.println("clique list");
	}
	
	public void onCliqueCurrent(){
		System.out.println("clique current");
	}
	
	public void onCliqueParam(){
		System.out.println("clique param");
	}
	
	public void onCliqueLogout(){
		System.out.println("clique logout");
		finish();
	}
}
