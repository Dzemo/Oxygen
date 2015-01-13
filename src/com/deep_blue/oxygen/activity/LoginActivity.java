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
import com.deep_blue.oxygen.synchronisation.SynchThread;
import com.deep_blue.oxygen.util.IntentKey;
import com.deep_blue.oxygen.util.MD5Utils;
import com.deep_blue.oxygen.util.PreferenceKey;

public class LoginActivity extends Activity {

	private UtilisateurDao utilisateurDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		utilisateurDao = new UtilisateurDao(LoginActivity.this);

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		// Récupération du login enregistré
		if (preferences.getBoolean(PreferenceKey.SAVE_LOGIN.toString(), false)) {
			((EditText) findViewById(R.id.editTextLogin)).setText(preferences
					.getString(PreferenceKey.SAVED_LOGIN.toString(), ""));
		}

		// Récupération d'une eventuelle connection
		if (preferences.getBoolean(PreferenceKey.KEEP_CONNECTION.toString(),
				false)) {
			String login = preferences.getString(
					PreferenceKey.KEPT_CONNECTION_LOGIN.toString(), "");
			Long version = preferences.getLong(
					PreferenceKey.KEPT_CONNECTION_VERSION.toString(), 0);

			Utilisateur utilisateur = utilisateurDao.getByLogin(login);

			if (utilisateur != null && utilisateur.getVersion() <= version) {
				// Démarrage de l'activité
				Intent intent = new Intent(LoginActivity.this,
						ListeFichesSecuriteActivity.class);
				intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(),
						utilisateur);

				startActivity(intent);
			}
		}
		
		Long maxVersion = utilisateurDao.getMaxVersion();
		if(maxVersion == 0){
			//Aucun utilisateur en base, affichage d'un toaster à l'utilisateur
			Toast toast = Toast.makeText(this, getResources().getString(R.string.login_aucun_utilisateur), Toast.LENGTH_LONG);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		Utilisateur utilisateur = utilisateurDao.authentifier(login, MD5Utils.md5(password));

		if (utilisateur != null) {
			// Enregistrement du login et de la connection si spécifié dans les
			// préférences
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);

			// Enregistrement du login
			if (preferences.getBoolean(PreferenceKey.SAVE_LOGIN.toString(),
					false)) {
				SharedPreferences.Editor pEditor = preferences.edit();
				pEditor.putString(PreferenceKey.SAVED_LOGIN.toString(),
						((EditText) findViewById(R.id.editTextLogin)).getText()
								.toString());
				pEditor.apply();
			}
			// Enregistrement de l'id de connection
			if (preferences.getBoolean(
					PreferenceKey.KEEP_CONNECTION.toString(), false)) {
				SharedPreferences.Editor pEditor = preferences.edit();
				pEditor.putString(
						PreferenceKey.KEPT_CONNECTION_LOGIN.toString(),
						utilisateur.getLogin());
				pEditor.putLong(
						PreferenceKey.KEPT_CONNECTION_VERSION.toString(),
						utilisateur.getVersion());
				pEditor.apply();
			}

			// Démarrage de l'activité
			Intent intent = new Intent(LoginActivity.this,
					ListeFichesSecuriteActivity.class);
			intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(),
					utilisateur);

			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(this, R.string.login_invalide,
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
