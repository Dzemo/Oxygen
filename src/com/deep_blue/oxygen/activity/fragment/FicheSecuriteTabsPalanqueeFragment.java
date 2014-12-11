package com.deep_blue.oxygen.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureePrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureeRealiseeDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeHeureDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurPrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurRealiseeDialogFragment;
import com.deep_blue.oxygen.listener.PlongeurOnClickListener;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;

public class FicheSecuriteTabsPalanqueeFragment extends Fragment {

	private Palanquee palanquee;
	private View rootView;

	public FicheSecuriteTabsPalanqueeFragment(Palanquee palanquee) {
		super();
		this.palanquee = palanquee;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// The last two arguments ensure LayoutParams are inflated
		// properly.
		rootView = inflater.inflate(
				R.layout.activity_fiche_securite_fragment_palanquee, container,
				false);

		if (palanquee != null) {

			// Initialisation de la vue avec la palanquée séléctionné

			// Info général de la palanquée
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_gaz_value))
					.setText(palanquee.getTypeGaz().toString());
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_plongee_value))
					.setText(palanquee.getTypePlonge().toString());
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_prevue_value))
					.setText(palanquee.getProfondeurPrevue().toString()
							+ " mètres");
			if (palanquee.getProfondeurRealiseeMoniteur() > 0)
				((TextView) rootView
						.findViewById(R.id.textView_palanquee_info_profondeur_realisee_value))
						.setText(palanquee.getProfondeurRealiseeMoniteur()
								.toString() + "mètres");
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_prevue_value))
					.setText(DateStringUtils.secondsToNiceString(palanquee
							.getDureePrevue()));
			if (palanquee.getDureeRealiseeMoniteur() > 0)
				((TextView) rootView
						.findViewById(R.id.textView_palanquee_info_duree_realisee_value))
						.setText(DateStringUtils.secondsToNiceString(palanquee
								.getDureeRealiseeMoniteur()));
			((TextView) rootView.findViewById(R.id.textView_palanquee_heure))
					.setText(palanquee.getHeure());

			// onClick listener sur les images
			//Profondeur réalisée
			rootView.findViewById(R.id.iB_palanquee_profondeur_realisee)
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							PalanqueeProfondeurRealiseeDialogFragment ndf = new PalanqueeProfondeurRealiseeDialogFragment(
									rootView, palanquee);
							ndf.show(getFragmentManager(), "TAG");
						}
					});
			//Profondeur prévue
			rootView.findViewById(R.id.iB_palanquee_profondeur_prevue)
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PalanqueeProfondeurPrevueDialogFragment ndf = new PalanqueeProfondeurPrevueDialogFragment(
							rootView, palanquee);
					ndf.show(getFragmentManager(), "TAG");
				}
			});
			//Duree prévue
			rootView.findViewById(R.id.iB_palanquee_duree_prevue)
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PalanqueeDureePrevueDialogFragment ndf = new PalanqueeDureePrevueDialogFragment(
							rootView, palanquee);
					ndf.show(getFragmentManager(), "TAG");
				}
			});
			//Duree réalisé
			rootView.findViewById(R.id.iB_palanquee_duree_realisee)
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PalanqueeDureeRealiseeDialogFragment ndf = new PalanqueeDureeRealiseeDialogFragment(
							rootView, palanquee);
					ndf.show(getFragmentManager(), "TAG");
				}
			});
			//Heure
			rootView.findViewById(R.id.iB_palanquee_heure)
			.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PalanqueeHeureDialogFragment ndf = new PalanqueeHeureDialogFragment(
							rootView, palanquee);
					ndf.show(getFragmentManager(), "TAG");
				}
			});

			// Moniteur si présent
			if (palanquee.getMoniteur() != null) {
				Moniteur moniteur = palanquee.getMoniteur();

				((TextView) rootView
						.findViewById(R.id.textView_palanquee_moniteur))
						.setText(moniteur.getPrenom() + " " + moniteur.getNom());
			} else {
				rootView.findViewById(R.id.palanquee_moniteur).setVisibility(
						View.GONE);
			}

			// Ajout des plongeurs
			TableLayout tableLayout = (TableLayout) rootView
					.findViewById(R.id.activity_fiche_securite_fragment_palanquee);

			int index = 3;
			int parite_background = palanquee.getMoniteur() != null ? 1 : 0;

			for (Plongeur plongeur : palanquee.getPlongeurs()) {

				TableRow row = (TableRow) inflater.inflate(R.layout.activity_fiche_securite_fragment_palanquee_plongeur_layout, tableLayout, false);
				
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
						plongeur, null, this.getActivity()));
				
				
				// Ajout de la row dans la table
				tableLayout.addView(row, index, row.getLayoutParams());
				index++;
			}
			rootView.requestLayout();

		} else
			System.out.println("Affichage du fragment sans palanquee");

		return rootView;
	}

}