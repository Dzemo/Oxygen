package com.deep_blue.oxygen.activity.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.Palanquee;

public class PalanqueeTypeGazDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

	private View rootView;
	private int gazParDefaut;
	private Palanquee palanquee;

	public PalanqueeTypeGazDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		if (palanquee.getTypeGaz() == EnumTypeGaz.NITROX)
			gazParDefaut = 1;
		else if (palanquee.getTypeGaz() == null)
			gazParDefaut = -1;
		else
			gazParDefaut = 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		CharSequence[] items = {EnumTypeGaz.AIR.toString(), EnumTypeGaz.NITROX.toString()};
		
		builder.setTitle(R.string.palanquee_dialog_type_gaz_title)
				.setSingleChoiceItems(items, gazParDefaut, this)
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

		return builder.create();
	}

	public void dismiss(boolean type) {

		if (type) {

			if(gazParDefaut == 0)
				palanquee.setTypeGaz(EnumTypeGaz.AIR);
			else if(gazParDefaut == 1)
				palanquee.setTypeGaz(EnumTypeGaz.NITROX);
			else
				palanquee.setTypeGaz(null);
			
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_gaz_value))
					.setText(palanquee.getTypeGaz() != null ? palanquee.getTypeGaz().toString() : "");
		}

		super.dismiss();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		gazParDefaut = which;
	}
}
