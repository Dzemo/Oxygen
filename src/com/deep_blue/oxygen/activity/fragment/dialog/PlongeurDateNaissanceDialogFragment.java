package com.deep_blue.oxygen.activity.fragment.dialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurDateNaissanceDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private String dateString;
	private Plongeur plongeur;
	private Date convertedDate;

	// Constructeur
	public PlongeurDateNaissanceDialogFragment(View rootView, Plongeur plongeur) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		
		if (!plongeur.getDateNaissance().isEmpty()){
			dateString = plongeur.getDateNaissance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    this.convertedDate = new Date();
		        
		    try {
				this.convertedDate = dateFormat.parse(dateString);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

		}
		else
			convertedDate = new Date();
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Cr�ation de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_plongeur_date_naissance, null);

		// Edition du Datepicker (si besoin)
		int year = convertedDate.getYear() + 1900;
		int month = convertedDate.getMonth();
		int day = convertedDate.getDate();
		
		((DatePicker) dialogView.findViewById(R.id.dialoguePlongeurDatePicker)).updateDate(
				year,day,month);

		
		// builder
		builder.setView(dialogView)
				.setTitle(R.string.plongeur_dialog_date_naissance_title)
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

	public void dismiss(boolean updateValue) {
		
		final Date date;
		
		if (updateValue) {
			// On set la date le plongeur avec la nouvelle valeur
			DatePicker datePicker = (DatePicker) dialogView
					.findViewById(R.id.dialoguePlongeurDatePicker);
			int day = datePicker.getDayOfMonth();
			int month = datePicker.getMonth();
			int year = datePicker.getYear()-1900;
			date = new Date(year, month, day);
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateNaissance = formatter.format(date);
			plongeur.setDateNaissance(dateNaissance);
		
			((TextView) rootView
					.findViewById(R.id.textView_plongeur_date_naissance_value))
					.setText(dateNaissance);
		}
		
		super.dismiss();
		
	}
}