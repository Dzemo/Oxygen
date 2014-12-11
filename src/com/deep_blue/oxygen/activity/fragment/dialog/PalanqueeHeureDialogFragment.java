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

public class PalanqueeHeureDialogFragment extends DialogFragment implements OnClickListener{
	private View rootView;
	private View dialogView;
	private int heureParDefaut, minuteParDefaut, secondParDefaut;
	private Palanquee palanquee;

	public PalanqueeHeureDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getHeure() != null && !palanquee.getHeure().isEmpty()){
			String[] split = palanquee.getHeure().split(":");
			heureParDefaut = Integer.parseInt(split[0]);
			minuteParDefaut = Integer.parseInt(split[1]);
			secondParDefaut = Integer.parseInt(split[2]);			
		}
		else{
			Date now = new Date();
			heureParDefaut = now.getHours();
			minuteParDefaut = now.getMinutes();
			secondParDefaut = now.getSeconds();
		}
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_palanquee_heure, null);

		// Ajout des numberpicker
		NumberPicker npHeure = (NumberPicker) dialogView.findViewById(R.id.heurePickerHeure);
		npHeure.setMaxValue(23);
		npHeure.setMinValue(0);
		npHeure.setValue(heureParDefaut);
		
		NumberPicker npMinute = (NumberPicker) dialogView.findViewById(R.id.heurePickerMinute);
		npMinute.setMaxValue(59);
		npMinute.setMinValue(0);
		npMinute.setValue(minuteParDefaut);
		
		NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.heurePickerSeconde);
		npSecond.setMaxValue(59);
		npSecond.setMinValue(0);
		npSecond.setValue(secondParDefaut);
		
		//Ajout du click sur le bouton now
		Button now = (Button)dialogView.findViewById(R.id.button_palanquee_dialog_heure_now);
		now.setOnClickListener(this);

		// builder
		builder.setView(dialogView)
				.setTitle(R.string.palanquee_dialog_heure_title)
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
			int heures = ((NumberPicker) dialogView
					.findViewById(R.id.heurePickerHeure)).getValue();
			int minutes = ((NumberPicker) dialogView
					.findViewById(R.id.heurePickerMinute)).getValue();
			int secondes = ((NumberPicker) dialogView
					.findViewById(R.id.heurePickerSeconde)).getValue();
			
			palanquee.setHeure(heures+":"+minutes+":"+secondes);
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_heure))
					.setText(palanquee.getHeure());
		}
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		Date now = new Date();
		heureParDefaut = now.getHours();
		minuteParDefaut = now.getMinutes();
		secondParDefaut = now.getSeconds();
		
		NumberPicker npHeure = (NumberPicker) dialogView.findViewById(R.id.heurePickerHeure);
		npHeure.setValue(heureParDefaut);
		
		NumberPicker npMinute = (NumberPicker) dialogView.findViewById(R.id.heurePickerMinute);
		npMinute.setValue(minuteParDefaut);
		
		NumberPicker npSecond = (NumberPicker) dialogView.findViewById(R.id.heurePickerSeconde);
		npSecond.setValue(secondParDefaut);
	}
}
