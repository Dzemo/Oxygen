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

public class PalanqueeProfondeurRealiseeMoniteurDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private Float profondeurParDefautMoniteur;
	private Palanquee palanquee;

	public PalanqueeProfondeurRealiseeMoniteurDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getProfondeurRealiseeMoniteur() != 0.0)
			profondeurParDefautMoniteur = palanquee.getProfondeurRealiseeMoniteur();
		else if (palanquee.getProfondeurPrevue() != 0.0)
			profondeurParDefautMoniteur = palanquee.getProfondeurPrevue();
		else
			profondeurParDefautMoniteur = 12.0f;
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_palanquee_profondeur, null);

		// Ajout du numberpicker
		// On choisit d'abord le metre
		NumberPicker npMeter = (NumberPicker) dialogView
				.findViewById(R.id.depthPickerMeter);
		npMeter.setMaxValue(120);
		npMeter.setMinValue(0);
		npMeter.setValue(profondeurParDefautMoniteur.intValue());

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
		npDecimal.setValue(Float.valueOf(profondeurParDefautMoniteur*10).intValue()%10);

		// builder
		builder.setView(dialogView)
				.setTitle(R.string.palanquee_dialog_profondeur_realisee_moniteur_title)
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
			
			int metres = ((NumberPicker) dialogView
					.findViewById(R.id.depthPickerMeter)).getValue();
			int decimals = ((NumberPicker) dialogView
					.findViewById(R.id.decimalDepthPickerMeter)).getValue();
			Float total = (float) metres + ((float) decimals)/10;
			
			// On set la profondeur realisee de la palanquee avec la nouvelle valeur
			palanquee.setProfondeurRealiseeMoniteur(total);
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_realisee_moniteur_value))
					.setText(palanquee.getProfondeurRealiseeMoniteur() + " mètres");
		}
		
		super.dismiss();
	}
}
