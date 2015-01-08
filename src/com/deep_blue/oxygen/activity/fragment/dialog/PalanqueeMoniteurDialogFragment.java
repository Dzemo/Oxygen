package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.PalanqueeActivity;
import com.deep_blue.oxygen.dao.MoniteurDao;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;

public class PalanqueeMoniteurDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	private View rootView;
	private int selectedIndex;
	private Palanquee palanquee;
	private List<String> items;
	private List<Moniteur> moniteurs;

	public PalanqueeMoniteurDialogFragment(View rootView,
			Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.palanquee = palanquee;
		this.selectedIndex = -1;

		// Récupération de la liste des moniteurs
		MoniteurDao moniteurDao = new MoniteurDao(rootView.getContext());
		moniteurs = moniteurDao.getAllActif();

		items = new ArrayList<String>();
		for (int i = 0; i < moniteurs.size(); i++) {
			Moniteur moniteur = moniteurs.get(i);
			items.add(moniteur.getPrenom() + " " + moniteur.getNom() + " (" + moniteur.getAptitudes().toString() + ")");

			if (palanquee.getMoniteur() != null
					&& moniteur.getIdWeb() == palanquee.getMoniteur()
							.getIdWeb())
				selectedIndex = i;
		}

	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] charSequenceItems = items
				.toArray(new CharSequence[items.size()]);
		builder.setTitle(R.string.palanquee_dialog_moniteur_title)
				.setSingleChoiceItems(charSequenceItems, selectedIndex, this)
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

		if (type && selectedIndex != -1) {

			palanquee.setMoniteur(moniteurs.get(selectedIndex));

			if(palanquee.getMoniteur() != null){
				((TextView) rootView
						.findViewById(R.id.textView_palanquee_moniteur))
						.setText(palanquee.getMoniteur() != null ? palanquee.getMoniteur().getPrenom() + " " + palanquee.getMoniteur().getNom() : "");
			}
		}

		super.dismiss();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		selectedIndex = which;
	}
}