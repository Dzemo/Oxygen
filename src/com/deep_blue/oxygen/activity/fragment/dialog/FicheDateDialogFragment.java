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
import android.widget.DatePicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.util.DateStringUtils;

@SuppressLint("InflateParams")
public class FicheDateDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private FicheSecurite ficheSecurite;
	private Date date;

	public FicheDateDialogFragment(View rootView, FicheSecurite ficheSecurite) {
		super();
		this.rootView = rootView;
		this.ficheSecurite = ficheSecurite;

		if (ficheSecurite.getTimestamp() != null) {
			this.date = ficheSecurite.getDate();
		} else {
			this.date = new Date();
		}
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_fiche_date, null);

		// Initialisation du datepicker
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		int day = date.getDate();
		
		((DatePicker) dialogView.findViewById(R.id.dialogueFicheDatePicker)).updateDate(
				year,month, day);

		// builder
		builder.setView(dialogView)
				.setTitle(R.string.fiche_info_dialog_date_title)
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

		if (updateValue) {
			// On set la date de la ficheSecurite avec la nouvelle valeur
			DatePicker datePicker = (DatePicker) dialogView
					.findViewById(R.id.dialogueFicheDatePicker);
			int day = datePicker.getDayOfMonth();
			int month = datePicker.getMonth();
			int year = datePicker.getYear()-1900;
			ficheSecurite.setDate(new Date(year, month, day));
		
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_date_value))
					.setText(DateStringUtils.timestampsToDate(ficheSecurite
							.getTimestamp()));
		}
		
		super.dismiss();
	}
}