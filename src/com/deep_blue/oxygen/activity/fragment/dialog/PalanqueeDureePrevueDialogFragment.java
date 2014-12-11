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

public class PalanqueeDureePrevueDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private Integer dureeParDefaut;
	private Palanquee palanquee;

	public PalanqueeDureePrevueDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getDureePrevue() != 0)
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
		NumberPicker npMinute = (NumberPicker) dialogView
				.findViewById(R.id.dureePickerMinute);
		npMinute.setMaxValue(120);
		npMinute.setMinValue(0);
		npMinute.setValue(dureeParDefaut.intValue()/60);
		
		NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.dureePickerSeconde);
		npSecond.setMaxValue(59);
		npSecond.setMinValue(0);
		npSecond.setValue(dureeParDefaut%60);
		
		dialogView.findViewById(R.id.button_palanquee_dialog_duree_now).setVisibility(View.GONE);

		// builder
		builder.setView(dialogView)
				.setTitle(R.string.palanquee_dialog_duree_prevue_title)
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

		if (type) {
			int minutes = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerMinute)).getValue();
			int secondes = ((NumberPicker) dialogView
					.findViewById(R.id.dureePickerSeconde)).getValue();
			int duree = minutes * 60 + secondes;
			
			// On set la profondeur realisee de la palanquee avec la nouvelle valeur
			palanquee.setDureePrevue(duree);
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_prevue_value))
					.setText(minutes+"m"+secondes+"s");
		}
		
		super.dismiss();
	}
}
