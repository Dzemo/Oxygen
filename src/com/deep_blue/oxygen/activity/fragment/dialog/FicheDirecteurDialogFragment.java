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
import com.deep_blue.oxygen.dao.MoniteurDao;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Moniteur;

public class FicheDirecteurDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	private View rootView;
	private int selectedIndex;
	private FicheSecurite ficheSecurite;
	private List<String> items;
	private List<Moniteur> directeurs;

	public FicheDirecteurDialogFragment(View rootView,
			FicheSecurite ficheSecurite) {
		super();
		this.rootView = rootView;
		this.ficheSecurite = ficheSecurite;
		this.selectedIndex = -1;

		// Récupération de la liste des directeurs
		MoniteurDao moniteurDao = new MoniteurDao(rootView.getContext());
		directeurs = moniteurDao.getAllActifDirecteurPlonge();

		items = new ArrayList<String>();
		for (int i = 0; i < directeurs.size(); i++) {
			Moniteur directeur = directeurs.get(i);
			items.add(directeur.getPrenom() + " " + directeur.getNom());

			if (ficheSecurite.getDirecteurPlonge() != null
					&& directeur.getIdWeb() == ficheSecurite
							.getDirecteurPlonge().getIdWeb())
				selectedIndex = i;
		}

	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] charSequenceItems = items.toArray(new CharSequence[items.size()]);
		builder.setTitle(R.string.fiche_info_dialog_directeur_plonge_title)
				.setSingleChoiceItems(charSequenceItems,
						selectedIndex, this)
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

			if (selectedIndex == -1)
				ficheSecurite.setDirecteurPlonge(null);
			else
				ficheSecurite.setDirecteurPlonge(directeurs.get(selectedIndex));

			String directeurPlongeValue = "";
			if (ficheSecurite.getDirecteurPlonge() != null) {
				directeurPlongeValue = ficheSecurite.getDirecteurPlonge()
						.getPrenom()
						+ " "
						+ ficheSecurite.getDirecteurPlonge().getNom();
			}
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_directeur_plonge_value))
					.setText(directeurPlongeValue);
		}

		super.dismiss();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		selectedIndex = which;
	}
}
