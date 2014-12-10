package com.deep_blue.oxygen.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.IntentKey;

public class PlongeurActivity extends Activity {

	private Utilisateur utilisateur;
	private Plongeur plongeur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plongeur);

		// Specify that tabs should be displayed in the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		utilisateur = (Utilisateur) intent
				.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		plongeur = (Plongeur) intent
				.getParcelableExtra(IntentKey.PLONGEUR_COURANT.toString());

		System.out.println(utilisateur);
		System.out.println(plongeur);
		
		((TextView) findViewById(R.id.textView_demo))
				.setText(plongeur.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.liste_fiches_securite, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		default:

			return super.onOptionsItemSelected(item);
		}
	}
}