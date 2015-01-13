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

public class PlongeurDureeRealiseeDialogFragment extends DialogFragment implements OnClickListener{
	private View rootView;
	private View dialogView;
	private Integer dureeParDefaut;
	private Plongeur plongeur;
	private Palanquee palanquee;

	public PlongeurDureeRealiseeDialogFragment(View rootView, Plongeur plongeur, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		this.palanquee = palanquee;
		
		if (plongeur.getDureeRealisee() != 0)
			dureeParDefaut = plongeur.getDureeRealisee();
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
				.inflate(R.layout.dialog_plongeur_duree_realise, null);

		// Ajout des numberpicker
		NumberPicker npHeures = (NumberPicker) dialogView
				.findViewById(R.id.dureePickerHeure);
		npHeures.setMaxValue(2);
		npHeures.setMinValue(0);
		npHeures.setValue(dureeParDefaut.intValue()/60);

		NumberPicker npMinutes = (NumberPicker) dialogView.findViewById(R.id.dureePickerMinute);
		npMinutes.setMaxValue(59);
		npMinutes.setMinValue(0);
		npMinutes.setValue(dureeParDefaut%60);
		
		//Onclick sur le boutton
		Button now = (Button)dialogView.findViewById(R.id.button_plongeur_dialog_duree_now);
		now.setOnClickListener(this);
		
		// builder
		builder.setView(dialogView)
				.setTitle(R.string.plongeur_dialog_duree_realisee_title)
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
			
			// On set la profondeur realisee de la plongeur avec la nouvelle valeur
			plongeur.setDureeRealisee(duree);
			
			((TextView) rootView
					.findViewById(R.id.textView_plongeur_duree_realisee_value)).setText(DateStringUtils.minutesToNiceString(plongeur.getDureeRealisee()));
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
			
			int dureeMinute = 0, dureeHeure = 0;
			
			if(heureMaintenant > heureDepart)
				dureeHeure += (heureMaintenant - heureDepart);
			
			dureeMinute += minuteMaintenant - minuteDepart;
			
			NumberPicker npHeure = (NumberPicker) dialogView.findViewById(R.id.dureePickerHeure);
			npHeure.setValue(dureeMinute);
			
			NumberPicker npMinute = (NumberPicker) dialogView.findViewById(R.id.dureePickerMinute);
			npMinute.setValue(dureeHeure);
		}
		
	}
}
