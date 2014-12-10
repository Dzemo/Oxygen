package com.deep_blue.oxygen.activity.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Palanquee;

public class ProfondeurRealiseeDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private Float profondeurParDefaut;
	private Palanquee palanquee;

	public ProfondeurRealiseeDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getProfondeurRealisee() != 0.0)
			profondeurParDefaut = palanquee.getProfondeurRealisee();
		else if (palanquee.getProfondeurPrevue() != 0.0)
			profondeurParDefaut = palanquee.getProfondeurPrevue();
		else
			profondeurParDefaut = 12.0f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_profondeur_realisee, null);

		// Ajout du numberpicker
		// On choisit d'abord le metre
		NumberPicker npMeter = (NumberPicker) dialogView
				.findViewById(R.id.depthPickerMeter);
		npMeter.setMaxValue(120);
		npMeter.setMinValue(0);
		npMeter.setValue(profondeurParDefaut.intValue());
		
		Log.i("toto","npMeter="+npMeter.getValue()+" profondeurParDefaut="+profondeurParDefaut.intValue());

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
				.setTitle(R.string.palanquee_dialog_profondeur_realisee_title)
				.setPositiveButton(R.string.palanquee_dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dismiss(true);
							}
						})
				.setNegativeButton(R.string.palanquee_dialog_annuler,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dismiss(false);
							}
						});

		// Create the AlertDialog object and return it
		return builder.create();
	}

	public void dismiss(boolean type) {

		int metres = ((NumberPicker) dialogView
				.findViewById(R.id.depthPickerMeter)).getValue();
		int decimals = ((NumberPicker) dialogView
				.findViewById(R.id.decimalDepthPickerMeter)).getValue();
		Float total = (float) metres + ((float) decimals)/10;
		
		// On set la profondeur realisee de la palanquee avec la nouvelle valeur
		palanquee.setProfondeurRealisee(total);

		if (type) {
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_realisee_value))
					.setText(palanquee.getProfondeurRealisee() + " mètres");
		}
		super.dismiss();
	}
}
