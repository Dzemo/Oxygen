package com.deep_blue.oxygen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureePrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureeRealiseeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeHeureDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurPrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurRealiseeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeTypeGazDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeTypePlongeDialogFragment;
import com.deep_blue.oxygen.listener.PlongeurOnClickListener;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;

@SuppressLint("InflateParams") public class PalanqueeActivity extends FragmentActivity {

	
	
	private Palanquee palanquee;
	private View rootView;

	public PalanqueeActivity(){
		super();
	}
	
	public PalanqueeActivity(Palanquee palanquee) {
		super();
		this.palanquee = palanquee;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Affichage du bouton de retour à coté du logo
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Récupération de la palanquee et de l'utilisateur
		Intent intent = getIntent();
		palanquee = intent.getParcelableExtra(IntentKey.PALANQUEE_COURANTE.toString());
		
		//Inlfation du layout
		rootView = getLayoutInflater().inflate(R.layout.activity_palanquee_layout, null);
		setContentView(rootView);
		
		final Palanquee palanqueeFinal = palanquee;

		// Initialisation de la vue avec la palanquée séléctionné

		// Info général de la palanquée
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_gaz_value))
				.setText(palanqueeFinal.getTypeGaz() != null ? palanqueeFinal.getTypeGaz().toString() : "");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_plongee_value))
				.setText(palanqueeFinal.getTypePlonge() != null ? palanqueeFinal.getTypePlonge().toString() : "");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_profondeur_prevue_value))
				.setText(palanqueeFinal.getProfondeurPrevue().toString()
						+ " mètres");
		if (palanqueeFinal.getProfondeurRealiseeMoniteur() != null  && palanqueeFinal.getProfondeurRealiseeMoniteur() > 0)
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_realisee_moniteur_value))
					.setText(palanqueeFinal.getProfondeurRealiseeMoniteur()
							.toString() + " mètres");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_duree_prevue_value))
				.setText(DateStringUtils.secondsToNiceString(palanqueeFinal
						.getDureePrevue()));
		if (palanqueeFinal.getDureeRealiseeMoniteur() > 0)
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_realisee_moniteur_value))
					.setText(DateStringUtils.secondsToNiceString(palanqueeFinal
							.getDureeRealiseeMoniteur()));
		((TextView) rootView.findViewById(R.id.textView_palanquee_heure))
				.setText(palanqueeFinal.getHeure());

		// onClick listener sur les images
		//Profondeur réalisée
		rootView.findViewById(R.id.iB_palanquee_profondeur_realisee_moniteur)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PalanqueeProfondeurRealiseeMoniteurDialogFragment ndf = new PalanqueeProfondeurRealiseeMoniteurDialogFragment(
								rootView, palanqueeFinal);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		//Profondeur prévue
		rootView.findViewById(R.id.iB_palanquee_profondeur_prevue)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeProfondeurPrevueDialogFragment ndf = new PalanqueeProfondeurPrevueDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Duree prévue
		rootView.findViewById(R.id.iB_palanquee_duree_prevue)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeDureePrevueDialogFragment ndf = new PalanqueeDureePrevueDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Duree réalisé
		rootView.findViewById(R.id.iB_palanquee_duree_realisee_moniteur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeDureeRealiseeMoniteurDialogFragment ndf = new PalanqueeDureeRealiseeMoniteurDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Heure
		rootView.findViewById(R.id.iB_palanquee_heure)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeHeureDialogFragment ndf = new PalanqueeHeureDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Type gaz
		rootView.findViewById(R.id.iB_palanquee_gaz)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeTypeGazDialogFragment ndf = new PalanqueeTypeGazDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Type plonge
		rootView.findViewById(R.id.iB_palanquee_plonge)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeTypePlongeDialogFragment ndf = new PalanqueeTypePlongeDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Moniteur
		rootView.findViewById(R.id.iB_palanquee_moniteur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeMoniteurDialogFragment ndf = new PalanqueeMoniteurDialogFragment(
						rootView, palanqueeFinal);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});

		// Moniteur si présent
		if (palanqueeFinal.getTypePlonge() != EnumTypePlonge.AUTONOME) {
			Moniteur moniteur = palanqueeFinal.getMoniteur();
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_moniteur))
					.setText(moniteur != null ? moniteur.getPrenom() + " " + moniteur.getNom() : "");
		} else {
			rootView.findViewById(R.id.palanquee_moniteur).setVisibility(
					View.GONE);
		}
		

		
		// Ajout du clique d'ajout de palanquee
		rootView.findViewById(R.id.iB_palanquee_ajout_plongeur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toastDelete = Toast.makeText(rootView.getContext(), "Bouton d'ajout d'un nouveau plongeur (non implémenté)", Toast.LENGTH_SHORT);
				toastDelete.show();
			}
		});

		// Ajout des plongeurs
		TableLayout tableLayout = (TableLayout) rootView
				.findViewById(R.id.activity_fiche_securite_fragment_palanquee);

		
		// Suppression des précédents plongeurs
		if(tableLayout.getChildCount() - 9 > 0)
			tableLayout.removeViews(4, tableLayout.getChildCount()-5);
		
		int index = 4;
		int parite_background = palanqueeFinal.getMoniteur() != null ? 1 : 0;

		for (Plongeur plongeur : palanqueeFinal.getPlongeurs()) {

			TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.activity_palanquee_plongeur_layout, tableLayout, false);
			
			// Ajout du plongeur
			((TextView) row.findViewById(R.id.textView_palanquee_plongeur_label))
			.setText("Plongeur: " + plongeur.getPrenom() + " "
					+ plongeur.getNom());

			// Coloration selon la parité de la row
			if (parite_background % 2 == 0)
				row.setBackgroundResource(R.drawable.list_item_background_plongeur_1);
			else
				row.setBackgroundResource(R.drawable.list_item_background_plongeur_2);
			parite_background++;

			// Clik listener sur la row
			row.findViewById(R.id.iB_palanquee_plongeur).setOnClickListener(new PlongeurOnClickListener(
					plongeur, null, palanqueeFinal, this));
			
			
			// Ajout de la row dans la table
			tableLayout.addView(row, index, row.getLayoutParams());
			index++;
		}
		rootView.requestLayout();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Plongeur plongeur = (Plongeur) data.getExtras().get(IntentKey.RESULT_PLONGEUR.toString());
		Log.w("Fragment", plongeur.toString());
	}

	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.palanquee, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		case R.id.itemDelete:
			Toast toast = Toast.makeText(this, "Bouton de suppression de la palanquee (non implémenté)", Toast.LENGTH_SHORT);
			toast.show();
			return true;
		default:

		return super.onOptionsItemSelected(item);
	}
}

}