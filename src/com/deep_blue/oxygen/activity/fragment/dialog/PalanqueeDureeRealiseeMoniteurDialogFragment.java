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
			dureeParDefaut = 1200;
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
				.findViewById(R.id.dureePickerMinute);
		npMinutes.setMaxValue(120);
		npMinutes.setMinValue(0);
		npMinutes.setValue(dureeParDefaut.intValue()/60);

		NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.dureePickerSeconde);
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
			int minutes = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerMinute)).getValue();
			int secondes = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerSeconde)).getValue();
			int duree = minutes * 60 + secondes;
			
			// On set la profondeur realisee de la palanquee avec la nouvelle valeur
			palanquee.setDureeRealiseeMoniteur(duree);
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_realisee_moniteur_value))
					.setText(minutes+"m"+secondes+"s");
		}
		
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		
		if (palanquee.getHeure() != null && !palanquee.getHeure().isEmpty()){
			String[] split = palanquee.getHeure().split(":");
			int heureDepart = Integer.parseInt(split[0]);
			int minuteDepart = Integer.parseInt(split[1]);
			int secondDepart = Integer.parseInt(split[2]);	
			
			Date now = new Date();
			int heureMaintenant = now.getHours();
			int minuteMaintenant = now.getMinutes();
			int secondMaintenant = now.getSeconds();
			
			int dureeMinute = 0, dureeSeconde = 0;
			
			if(heureMaintenant > heureDepart)
				dureeMinute += (heureMaintenant - heureDepart)*60;
			
			dureeMinute += minuteMaintenant - minuteDepart;
			dureeSeconde = secondMaintenant - secondDepart;
			
			if(dureeSeconde < 0){
				dureeSeconde += 60;
				dureeMinute -= 1;
			}
			
			NumberPicker npMinute = (NumberPicker) dialogView.findViewById(R.id.dureePickerMinute);
			npMinute.setValue(dureeMinute);
			
			NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.dureePickerSeconde);
			npSecond.setValue(dureeSeconde);
		}
		
	}
}
