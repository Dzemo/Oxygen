package com.deep_blue.oxygen.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.DirecteurPlongerPickerDialogFragment;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.dao.MoniteurDao;
import com.deep_blue.oxygen.listener.ListeFichesSecuriteOnClickListener;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListeFichesSecurite;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.synchronisation.SynchThread;
import com.deep_blue.oxygen.tests.TestValidationFiche;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;

public class ListeFichesSecuriteActivity extends FragmentActivity implements DirecteurPlongerPickerDialogFragment.DirecteurPlongerPickListener {

	private Utilisateur utilisateur;
	private List<Moniteur> directeurs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		Intent intent = getIntent();
		utilisateur = (Utilisateur) intent
				.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		
		loadListeFiche();

		TestValidationFiche testvalidation = new TestValidationFiche(this);
		testvalidation.testValidationEnregistrementFiche();

		//Récupération de tout les moniteurs
    	MoniteurDao moniteurDao = new MoniteurDao(this);
    	directeurs = moniteurDao.getAllActifDirecteurPlonge();
    	if(utilisateur.getMoniteurAssocie() != null){
    		//Si l'utilisateur a un moniteur associé, on le replace en tête de liste
    		for(int i = 0; i < directeurs.size(); i++){
    			if(utilisateur.getMoniteurAssocie().getIdWeb() == directeurs.get(i).getIdWeb()){
    				directeurs.remove(i);
    				directeurs.add(i, directeurs.get(0));
    				directeurs.add(0, utilisateur.getMoniteurAssocie());
    			}
    		}
    	}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data != null && data.getExtras() != null){
			String text = (String) data.getExtras().get(IntentKey.RESULT_TEXT.toString());
			if(text != null){
				Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		loadListeFiche();
	}


	@Override
	public void onDirecteurPlongerPick(Boolean selected, Moniteur directeurPlonge) {
		if(selected){
			FicheSecurite nouvelleFiche = new FicheSecurite();
			nouvelleFiche.setIdWeb(-1);
			nouvelleFiche.setId(-1L);
			nouvelleFiche.setDirecteurPlonge(directeurPlonge);
			
			Intent intent = new Intent(this, FicheSecuriteInfoActivity.class);		
			intent.putExtra(IntentKey.FICHE_SECURITE_COURANTE.toString(), nouvelleFiche);
			intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(), utilisateur);
			
			this.startActivityForResult(intent, 0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.liste_fiches_securite, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.itemAdd:
			DirecteurPlongerPickerDialogFragment directeurPlongerPickerDialogFragment = new DirecteurPlongerPickerDialogFragment(utilisateur, directeurs);
			directeurPlongerPickerDialogFragment.show(getSupportFragmentManager(), "DirecteurPlongerPickerDialogFragment");
			return true;
		case R.id.itemParam:
			Intent intent = new Intent(ListeFichesSecuriteActivity.this,
					ParametreActivity.class);
			startActivity(intent);
			return true;
		case R.id.itemSync:
			Thread synchThread = new SynchThread(this, utilisateur);
			synchThread.start();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Charge la liste des fiches dans la vue depuis la base de données
	 */
	public void loadListeFiche(){
		TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout_listeFiches);
		
		//Effacage de la liste des fiches
		tableLayout.removeAllViews();
		
		//Ajout des titres
		TableRow rowTitrePerso = (TableRow) getLayoutInflater().inflate(
				R.layout.activity_list_row_title_perso_layout, tableLayout,
				false);
		tableLayout.addView(rowTitrePerso, 0);
		TableRow rowTitreAutre = (TableRow) getLayoutInflater().inflate(
				R.layout.activity_list_row_title_autre_layout, tableLayout,
				false);
		tableLayout.addView(rowTitreAutre, 1);
		
		//Chargement de la liste des fiches		
		FicheSecuriteDao ficheSecuriteDao = new FicheSecuriteDao(this);
		ListeFichesSecurite listeFiche = ficheSecuriteDao.getAll();
		int parite = 0;
		int index = 1;
		int countFichePerso = 0, countFicheAutre = 0;

		// Si l'utilisateur courant à un moniteur associé, on affiche d'abord la
		// liste des fiches perso
		if (utilisateur.getMoniteurAssocie() != null) {
			for (FicheSecurite ficheSecurite : listeFiche) {
				if (ficheSecurite.getDirecteurPlonge() != null && ficheSecurite.getDirecteurPlonge().getIdWeb() == utilisateur
						.getMoniteurAssocie().getIdWeb()) {

					//Génération de la tablerow
					TableRow row = genererRowPourFiche(ficheSecurite, tableLayout, parite);
					
					// Ajout de la row dans la table
					tableLayout.addView(row, index);

					index++;
					parite++;
					countFichePerso++;
				}
			}
		}

		// Affichage des autres fiches
		parite = 0;
		index++;
		for (FicheSecurite ficheSecurite : listeFiche) {
			if (ficheSecurite.getDirecteurPlonge() == null || utilisateur.getMoniteurAssocie() == null
					|| ficheSecurite.getDirecteurPlonge().getIdWeb() != utilisateur
							.getMoniteurAssocie().getIdWeb()) {
				
				//Génération de la tablerow
				TableRow row = genererRowPourFiche(ficheSecurite, tableLayout, parite);
				
				// Ajout de la row dans la table
				tableLayout.addView(row, index);

				parite++;
				index++;
				countFicheAutre++;
			}
		}

		// Sinon on cache les titres de row
		if (utilisateur.getMoniteurAssocie() != null) {
			findViewById(R.id.liste_fiche_perso_title).setVisibility(View.VISIBLE);
			findViewById(R.id.liste_fiche_autre_title).setVisibility(View.VISIBLE);
			
			((TextView) findViewById(R.id.textView_liste_fiche_perso_title))
					.setText(String.format(
							getResources().getString(
									R.string.list_fiche_perso_title),
							countFichePerso));
			((TextView) findViewById(R.id.textView_liste_fiche_autre_title))
					.setText(String.format(
							getResources().getString(
									R.string.list_fiche_autre_title),
							countFicheAutre));
		} else {
			findViewById(R.id.liste_fiche_perso_title).setVisibility(View.GONE);
			findViewById(R.id.liste_fiche_autre_title).setVisibility(View.GONE);
		}
	}
	
	private TableRow genererRowPourFiche(FicheSecurite ficheSecurite, TableLayout tableLayout, int parite){
		TableRow row = (TableRow) getLayoutInflater().inflate(
				R.layout.activity_list_row_fiche_layout, tableLayout, false);

		//Ajout du logo
		ImageView ivIcon = (ImageView) row.findViewById(R.id.iB_fiche);
		if (ficheSecurite.getEtat() != null && ficheSecurite.getEtat().equals(EnumEtat.VALIDE)) {
			ivIcon.setImageDrawable(getResources().getDrawable(
					R.drawable.closedstatus));
			ivIcon.setContentDescription(getResources().getString(
					R.string.list_icon_valid));
		} else {
			ivIcon.setImageDrawable(getResources().getDrawable(
					R.drawable.inprogressstatus));
			ivIcon.setContentDescription(getResources().getString(
					R.string.list_icon_edit));
		}

		//Génération du label		
		String textDate = "";
		if(ficheSecurite.getTimestamp() != null && ficheSecurite.getTimestamp() > 0){
			textDate = " le " + DateStringUtils.timestampsToDate(ficheSecurite.getTimestamp());
		}
		String textDirecteurPlonge = "";
		if(ficheSecurite.getDirecteurPlonge() != null){
			if(utilisateur.getMoniteurAssocie() != null && utilisateur.getMoniteurAssocie().getIdWeb() == ficheSecurite.getDirecteurPlonge().getIdWeb()){
				textDirecteurPlonge = "";
			}
			else{
				textDirecteurPlonge = " par "+ficheSecurite.getDirecteurPlonge().getNom();
			}
		}
		
		String textDescription = "Plongé"
				+ (ficheSecurite.getSite() != null ? " à "
						+ ficheSecurite.getSite().getNom() : "")
				+ textDate+textDirecteurPlonge;
		
		//Set du label
		TextView tvDescription = (TextView) row
				.findViewById(R.id.textView_liste_fiche_row_label);
		tvDescription.setText(textDescription);

		// Coloration selon la parité de la row
		if (parite % 2 == 0)
			row.setBackgroundResource(R.drawable.list_item_background_1);
		else
			row.setBackgroundResource(R.drawable.list_item_background_2);

		// Clik listener sur la row
		row.setOnClickListener(new ListeFichesSecuriteOnClickListener(
				ficheSecurite, utilisateur, this));
		
		return row;
	}
}