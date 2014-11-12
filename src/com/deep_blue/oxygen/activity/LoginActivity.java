package com.deep_blue.oxygen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.UtilisateurDao;
import com.deep_blue.oxygen.model.Utilisateur;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
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
        	System.out.println("Menu param");
        	Intent intent = new Intent(LoginActivity.this, ParametreActivity.class);
			startActivity(intent);
            return true;
            
        case R.id.itemSync:
           // showHelp();
        	System.out.println("Menu sync");
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
	}
	
	public void onClickCancel(View view){
		System.out.println("Clique cancel");
		finish();
	}
	
	public void onClickLogin(View view){
		String login = ((EditText)findViewById(R.id.editTextLogin)).getText().toString();
		String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
		
		System.out.println("Clique login: login="+login+" password="+password);
		
		UtilisateurDao utilisateurDao = new UtilisateurDao(LoginActivity.this);
		Utilisateur utilisateur = utilisateurDao.authentifier(login, password);
		
		if(utilisateur != null){
			System.out.println("Authentification réussi!");
			System.out.println("Utilisateur: "+utilisateur);
			
			Intent intent = new Intent(LoginActivity.this, ListActivity.class);
			intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(), utilisateur);
			
			startActivity(intent);
		}
		else{
			System.out.println("Authentification échoué!");
		}
	}
}
