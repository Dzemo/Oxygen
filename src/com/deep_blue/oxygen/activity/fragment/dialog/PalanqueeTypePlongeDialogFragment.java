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
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.Palanquee;

public class PalanqueeTypePlongeDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	private View rootView;
	private int plongeParDefaut;
	private Palanquee palanquee;

	public PalanqueeTypePlongeDialogFragment(View rootView, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		switch (palanquee.getTypePlonge()) {
		case AUTONOME:
			plongeParDefaut = 0;
			break;
		case ENCADRE:
			plongeParDefaut = 1;
			break;
		case TECHNIQUE:
			plongeParDefaut = 2;
			break;
		case BAPTEME:
			plongeParDefaut = 3;
			break;
		default:
			plongeParDefaut = -1;
			break;

		}
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		CharSequence[] items = { EnumTypePlonge.AUTONOME.toString(),
				EnumTypePlonge.ENCADRE.toString(),
				EnumTypePlonge.TECHNIQUE.toString(),
				EnumTypePlonge.BAPTEME.toString() };

		builder.setTitle(R.string.palanquee_dialog_type_plongee_title)
				.setSingleChoiceItems(items, plongeParDefaut, this)
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

		return builder.create();
	}

	public void dismiss(boolean type) {

		if (type) {

			switch (plongeParDefaut) {
			case 0:
				palanquee.setTypePlonge(EnumTypePlonge.AUTONOME);
				break;
			case 1:
				palanquee.setTypePlonge(EnumTypePlonge.ENCADRE);
				break;
			case 2:
				palanquee.setTypePlonge(EnumTypePlonge.TECHNIQUE);
				break;
			case 3:
				palanquee.setTypePlonge(EnumTypePlonge.BAPTEME);
				break;
			default:
				palanquee.setTypePlonge(null);
				break;
			}

			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_plongee_value))
					.setText(palanquee.getTypePlonge() != null ? palanquee
							.getTypePlonge().toString() : "");
		}

		super.dismiss();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		plongeParDefaut = which;
	}
}
