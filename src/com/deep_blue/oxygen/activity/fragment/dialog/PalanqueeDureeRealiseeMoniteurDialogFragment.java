package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;

public class PalanqueeDureeRealiseeMoniteurDialogFragment extends DialogFragment implements OnClickListener{
	private View rootView;
	private View dialogView;
	private Integer dureeParDefaut;
	private Palanquee palanquee;

	public PalanqueeDureeRealiseeMoniteurDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getDureeRealiseeMoniteur() != 0)
			dureeParDefaut = palanquee.getDureeRealiseeMoniteur();
		else if (palanquee.getDureePrevue() != 0)
			dureeParDefaut = palanquee.getDureePrevue();
		else
			dureeParDefaut = 20;
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_palanquee_duree, null);

		// Ajout des numberpicker
		NumberPicker npMinutes = (NumberPicker) dialogView
				.findViewById(R.id.dureePickerHeure);
		npMinutes.setMaxValue(2);
		npMinutes.setMinValue(0);
		npMinutes.setValue(dureeParDefaut.intValue()/60);

		NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.dureePickerMinute);
		npSecond.setMaxValue(59);
		npSecond.setMinValue(0);
		npSecond.setValue(dureeParDefaut%60);
		
		//Onclick sur le boutton
		Button now = (Button)dialogView.findViewById(R.id.button_palanquee_dialog_duree_now);
		now.setOnClickListener(this);
		
		// builder
		builder.setView(dialogView)
				.setTitle(R.string.palanquee_dialog_duree_realisee_moniteur_title)
				.setPositiveButton(R.string.dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dismiss(true);
							}
						})
				.setNegativeButton(R.string.dialog_annuler,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dismiss(false);
							}
						});

		// Create the AlertDialog object and return it
		return builder.create();
	}

	public void dismiss(boolean type) {
		
		if (type) {
			int heures = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerHeure)).getValue();
			int minutes = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerMinute)).getValue();
			int duree = heures * 60 + minutes;
			
			// On set la profondeur realisee de la palanquee avec la nouvelle valeur
			palanquee.setDureeRealiseeMoniteur(duree);
			
			// On set la profondeur realisee des plongeurs de la palanquée qui n'ont pas de profondeur réalisé
			for(Plongeur plongeur : palanquee.getPlongeurs()){
				if(plongeur.getDureeRealisee() == null || plongeur.getDureeRealisee() <= 0){
					plongeur.setDureeRealisee(duree);
				}
			}
			
			//On met à jours l'affichage
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_realisee_moniteur_value))
					.setText(DateStringUtils.minutesToNiceString(palanquee.getDureeRealiseeMoniteur()));
		}
		
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		
		if (palanquee.getHeure() != null && !palanquee.getHeure().isEmpty()){
			String[] split = palanquee.getHeure().split(":");
			int heureDepart = Integer.parseInt(split[0]);
			int minuteDepart = Integer.parseInt(split[1]);
			
			Date now = new Date();
			int heureMaintenant = now.getHours();
			int minuteMaintenant = now.getMinutes();
			
			int dureeMinute = 0;
			
			if(heureMaintenant > heureDepart)
				dureeMinute += (heureMaintenant - heureDepart)*60;
			
			dureeMinute += minuteMaintenant - minuteDepart;
			
			NumberPicker npHeure = (NumberPicker) dialogView.findViewById(R.id.dureePickerHeure);
			npHeure.setValue(dureeMinute/60);
			
			NumberPicker npMinute = (NumberPicker) dialogView.findViewById(R.id.dureePickerMinute);
			npMinute.setValue(dureeMinute%60);
		}
		
	}
}
