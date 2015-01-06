package com.deep_blue.oxygen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheDateDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheDirecteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheEmbarcationDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheSiteDialogFragment;
import com.deep_blue.oxygen.listener.PalanqueeOnClickListener;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;

public class FicheSecuriteInfoActivity extends FragmentActivity {

	private FicheSecurite ficheSecurite = null;
	private View rootView;
	private Utilisateur utilisateur = null;

	@SuppressLint("InflateParams") @Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		//Affichage du bouton de retour à coté du logo
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Récupération de la fiche de sécurité et de l'utilisateur
		Intent intent = getIntent();
		ficheSecurite = intent.getParcelableExtra(IntentKey.FICHE_SECURITE_COURANTE.toString());
		utilisateur = intent.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		
		//Inlfation du layout
		rootView = getLayoutInflater().inflate(R.layout.activity_fiche_securite_info, null);
		setContentView(rootView);
		
		
		if (ficheSecurite != null) {
			// Initialisation de la vue avec la fiche de sécurité séléctionné

			// Info général de la fiche
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_date_value))
					.setText(DateStringUtils.timestampsToDate(ficheSecurite
							.getTimestamp()));
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_site_value))
					.setText(ficheSecurite.getSite() != null ? ficheSecurite
							.getSite().getNom() : "");
			String directeurPlongeValue = "";
			if (ficheSecurite.getDirecteurPlonge() != null) {
				directeurPlongeValue = ficheSecurite.getDirecteurPlonge()
						.getPrenom()
						+ " "
						+ ficheSecurite.getDirecteurPlonge().getNom();
			}
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_directeur_plonge_value))
					.setText(directeurPlongeValue);
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_embarcation_value))
					.setText(ficheSecurite.getEmbarcation() != null ? ficheSecurite
							.getEmbarcation().getLibelle() : "");
		}

		// Ajout du clique de modification de la date
		rootView.findViewById(R.id.iB_infos_date).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheDateDialogFragment ndf = new FicheDateDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification du directeur de plonge
		rootView.findViewById(R.id.iB_infos_directeur_plonge)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheDirecteurDialogFragment ndf = new FicheDirecteurDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification de l'embarcation
		rootView.findViewById(R.id.iB_infos_embarcation)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheEmbarcationDialogFragment ndf = new FicheEmbarcationDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification du site
		rootView.findViewById(R.id.iB_infos_site)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FicheSiteDialogFragment ndf = new FicheSiteDialogFragment(
						rootView, ficheSecurite);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		
		// Ajout du clique d'ajout de palanquee
		rootView.findViewById(R.id.iB_fiche_ajout_palanquee)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toastDelete = Toast.makeText(rootView.getContext(), "Bouton d'ajout d'une nouvelle palanquee (non implémenté)", Toast.LENGTH_SHORT);
				toastDelete.show();
			}
		});

		
		//Affichage des palanquée
		TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.activity_fiche_securite_info);
		int index = 5;
		for (Palanquee palanquee : ficheSecurite.getPalanquees()) {
			
			TableRow row = (TableRow) getLayoutInflater().inflate(
					R.layout.activity_fiche_securite_row_palanquee_layout, tableLayout, false);

			TextView tvDescription = (TextView) row
					.findViewById(R.id.textView_palanquee_label);
			tvDescription.setText("Palanquee "+palanquee.getNumero()+" ("+palanquee.getTypePlonge().toString()+" - "+palanquee.getProfondeurPrevue()+"m)");

			// Coloration selon la parité de la row
			if (index % 2 == 0)
				row.setBackgroundResource(R.drawable.list_item_background_2);
			else
				row.setBackgroundResource(R.drawable.list_item_background_1);

			// Clik listener sur l'image de modification de la palanquee
			row.findViewById(R.id.iB_palanquee).setOnClickListener(new PalanqueeOnClickListener(
					palanquee, utilisateur, this));
			
			// Ajout de la row dans la table
			tableLayout.addView(row, index);
			
			index++;
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fiche_securite_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		case R.id.itemValide:
			Toast toastValide = Toast.makeText(this, "Bouton de validation de la fiche (non implémenté)", Toast.LENGTH_SHORT);
			toastValide.show();
			return true;
		case R.id.itemSave:
			Toast toastSave = Toast.makeText(this, "Bouton de sauvegarde de la fiche (non implémenté)", Toast.LENGTH_SHORT);
			toastSave.show();
			return true;
		case R.id.itemDelete:
			Toast toastDelete = Toast.makeText(this, "Bouton de suppression de la fiche (non implémenté)", Toast.LENGTH_SHORT);
			toastDelete.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
