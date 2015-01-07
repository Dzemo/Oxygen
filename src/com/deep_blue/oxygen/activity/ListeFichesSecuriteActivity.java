package com.deep_blue.oxygen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.listener.ListeFichesSecuriteOnClickListener;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListeFichesSecurite;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.synchronisation.SynchThread;
import com.deep_blue.oxygen.tests.TestValidationFiche;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;

public class ListeFichesSecuriteActivity extends Activity {

	private Utilisateur utilisateur;

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

		case R.id.itemParam:
			Intent intent = new Intent(ListeFichesSecuriteActivity.this,
					ParametreActivity.class);
			startActivity(intent);
			return true;

		case R.id.itemSync:
			Thread synchThread = new SynchThread(this, utilisateur);
			synchThread.start();
			//TODO recharger la liste des fiches après la synchronisation
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
		int i = 0;
		int index = 1;
		int countFichePerso = 0, countFicheAutre = 0;

		// Si l'utilisateur courant à un moniteur associé, on affiche d'abord la
		// liste des fiches perso
		if (utilisateur.getMoniteurAssocie() != null) {
			for (FicheSecurite ficheSecurite : listeFiche) {
				if (ficheSecurite.getDirecteurPlonge().getIdWeb() == utilisateur
						.getMoniteurAssocie().getIdWeb()) {

					TableRow row = (TableRow) getLayoutInflater().inflate(
							R.layout.activity_list_row_fiche_layout, tableLayout,
							false);

					ImageView ivIcon = (ImageView) row
							.findViewById(R.id.iB_fiche);
					if (ficheSecurite.getEtat().equals(EnumEtat.SYNCHRONISE)) {
						ivIcon.setImageDrawable(getResources().getDrawable(
								R.drawable.inprogressstatus));
						ivIcon.setContentDescription(getResources().getString(
								R.string.list_icon_edit));
					} else if (ficheSecurite.getEtat().equals(EnumEtat.VALIDE)) {
						ivIcon.setImageDrawable(getResources().getDrawable(
								R.drawable.closedstatus));
						ivIcon.setContentDescription(getResources().getString(
								R.string.list_icon_valid));
					}

					TextView tvDescription = (TextView) row
							.findViewById(R.id.textView_liste_fiche_row_label);
					String dateString = DateStringUtils
							.timestampsToDate(ficheSecurite.getTimestamp());
					String hourString = DateStringUtils
							.timestampsToHeure(ficheSecurite.getTimestamp());

					String textDescription = "Plongé"
							+ (ficheSecurite.getSite() != null ? " à "
									+ ficheSecurite.getSite().getNom() : "")
							+ " le " + dateString + " à " + hourString;
					tvDescription.setText(textDescription);

					// Coloration selon la parité de la row
					if (i % 2 == 0)
						row.setBackgroundResource(R.drawable.list_item_background_1);
					else
						row.setBackgroundResource(R.drawable.list_item_background_2);

					// Clik listener sur la row
					row.setOnClickListener(new ListeFichesSecuriteOnClickListener(
							ficheSecurite, utilisateur, this));

					// Ajout de la row dans la table
					tableLayout.addView(row, index);

					index++;
					i++;
					countFichePerso++;
				}
			}
		}

		// Affichage des autres fiches
		i = 0;
		index++;
		for (FicheSecurite ficheSecurite : listeFiche) {
			if (utilisateur.getMoniteurAssocie() == null
					|| ficheSecurite.getDirecteurPlonge().getIdWeb() != utilisateur
							.getMoniteurAssocie().getIdWeb()) {
				TableRow row = (TableRow) getLayoutInflater().inflate(
						R.layout.activity_list_row_fiche_layout, tableLayout, false);

				ImageView ivIcon = (ImageView) row.findViewById(R.id.iB_fiche);
				if (ficheSecurite.getEtat().equals(EnumEtat.SYNCHRONISE)) {
					ivIcon.setImageDrawable(getResources().getDrawable(
							R.drawable.inprogressstatus));
					ivIcon.setContentDescription(getResources().getString(
							R.string.list_icon_edit));
				} else if (ficheSecurite.getEtat().equals(EnumEtat.VALIDE)) {
					ivIcon.setImageDrawable(getResources().getDrawable(
							R.drawable.closedstatus));
					ivIcon.setContentDescription(getResources().getString(
							R.string.list_icon_valid));
				}

				TextView tvDescription = (TextView) row
						.findViewById(R.id.textView_liste_fiche_row_label);
				String dateString = DateStringUtils
						.timestampsToDate(ficheSecurite.getTimestamp());
				String hourString = DateStringUtils
						.timestampsToHeure(ficheSecurite.getTimestamp());

				String textDescription = "Plongé"
						+ (ficheSecurite.getSite() != null ? " à "
								+ ficheSecurite.getSite().getNom() : "")
						+ " le " + dateString + " à " + hourString;
				tvDescription.setText(textDescription);

				// Coloration selon la parité de la row
				if (i % 2 == 0)
					row.setBackgroundResource(R.drawable.list_item_background_1);
				else
					row.setBackgroundResource(R.drawable.list_item_background_2);

				// Clik listener sur la row
				row.setOnClickListener(new ListeFichesSecuriteOnClickListener(
						ficheSecurite, utilisateur, this));

				// Ajout de la row dans la table
				tableLayout.addView(row, index);

				i++;
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
}