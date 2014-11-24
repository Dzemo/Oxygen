package com.deep_blue.oxygen.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.UtilisateurDao;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.thread.SynchThread;
import com.deep_blue.oxygen.util.IntentKey;
import com.deep_blue.oxygen.util.PreferenceKey;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
	
		//Récupération du login enregistré 
		if(preferences.getBoolean(PreferenceKey.SAVE_LOGIN.toString(), false)){
			((EditText) findViewById(R.id.editTextLogin)).setText(preferences.getString(PreferenceKey.SAVED_LOGIN.toString(), ""));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.itemParam:
			Intent intent = new Intent(LoginActivity.this,
					ParametreActivity.class);
			startActivity(intent);
			return true;

		case R.id.itemSync:
			Thread synchThread = new SynchThread(this);
			synchThread.start();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onClickCancel(View view) {
		finish();
	}

	public void onClickLogin(View view) {
		String login = ((EditText) findViewById(R.id.editTextLogin)).getText()
				.toString().trim();
		String password = ((EditText) findViewById(R.id.editTextPassword))
				.getText().toString();

		UtilisateurDao utilisateurDao = new UtilisateurDao(LoginActivity.this);
		Utilisateur utilisateur = utilisateurDao.authentifier(login, password);

		if (utilisateur != null) {

			Intent intent = new Intent(LoginActivity.this,
					ListeFichesSecuriteActivity.class);
			intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(),
					utilisateur);

			//Enregistrement du login si spécifié dans les préférences
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);
		
			//Récupération du login enregistré 
			if(preferences.getBoolean(PreferenceKey.SAVE_LOGIN.toString(), false)){
				SharedPreferences.Editor pEditor = preferences.edit();
				pEditor.putString(PreferenceKey.SAVED_LOGIN.toString(), ((EditText) findViewById(R.id.editTextLogin)).getText().toString());
				pEditor.apply();
			}
			
			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(this, R.string.login_invalide,
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
