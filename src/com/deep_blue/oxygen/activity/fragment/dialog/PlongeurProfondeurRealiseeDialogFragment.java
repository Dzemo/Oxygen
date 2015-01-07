package com.deep_blue.oxygen.activity.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurProfondeurRealiseeDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private Float profondeurParDefaut;
	private Plongeur plongeur;
	// Constructeur
	public PlongeurProfondeurRealiseeDialogFragment(View rootView, Plongeur plongeur, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		if (plongeur.getProfondeurRealisee() != 0)
			profondeurParDefaut = plongeur.getProfondeurRealisee();
		else if (palanquee.getProfondeurPrevue() != 0)
			profondeurParDefaut = palanquee.getProfondeurPrevue();
		else
			profondeurParDefaut = (float) 40;
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_plongeur_profondeur_realise, null);

		// Ajout des numberpicker
		NumberPicker npMeters = (NumberPicker) dialogView
				.findViewById(R.id.depthPickerMeter);
		npMeters.setMaxValue(120);
		npMeters.setMinValue(0);
		npMeters.setValue(profondeurParDefaut.intValue());

		// On cree l'array qui contient les decimales souhaitees
		String[] decimalSelection = new String[10];
		for (int i = 0; i < decimalSelection.length; i++) {
			decimalSelection[i] = Integer.toString(i);
		}
		// Puis on declare le numberpicker pour lui attribuer la range de l'array (pour les decimales)
		NumberPicker npDecimal = (NumberPicker) dialogView.findViewById(R.id.decimalDepthPickerMeter);
		npDecimal.setMaxValue(decimalSelection.length - 1);
		npDecimal.setMinValue(0);
		npDecimal.setDisplayedValues(decimalSelection);
		npDecimal.setValue(Float.valueOf(profondeurParDefaut*10).intValue()%10);
		
		// builder
		builder.setView(dialogView)
				.setTitle(R.string.plongeur_dialog_profondeur_realise_title)
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
			int meters = ((NumberPicker) dialogView
					.findViewById(R.id.depthPickerMeter)).getValue();
			int decimal = ((NumberPicker) dialogView
					.findViewById(R.id.decimalDepthPickerMeter)).getValue();
			float profondeur = meters + (decimal/100);
			
			// On set la profondeur realisee du plongeur avec la nouvelle valeur
			plongeur.setProfondeurRealisee(profondeur);
			
			((TextView) rootView
					.findViewById(R.id.textView_plongeur_profondeur_realisee_value))
					.setText(meters+","+decimal+"m");
		}
		
		super.dismiss();
	}
}
