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
import com.deep_blue.oxygen.dao.EmbarcationDao;
import com.deep_blue.oxygen.model.Embarcation;
import com.deep_blue.oxygen.model.FicheSecurite;

public class FicheEmbarcationDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	private View rootView;
	private int selectedIndex;
	private FicheSecurite ficheSecurite;
	private List<String> items;
	private List<Embarcation> embarcations;

	public FicheEmbarcationDialogFragment(View rootView,
			FicheSecurite ficheSecurite) {
		super();
		this.rootView = rootView;
		this.ficheSecurite = ficheSecurite;
		this.selectedIndex = -1;

		// Récupération de la liste des embarcations
		EmbarcationDao embarcationDao = new EmbarcationDao(rootView.getContext());
		embarcations = embarcationDao.getAllDisponible();

		items = new ArrayList<String>();
		for (int i = 0; i < embarcations.size(); i++) {
			Embarcation embarcation = embarcations.get(i);
			items.add(embarcation.getLibelle());

			if (ficheSecurite.getEmbarcation() != null
					&& embarcation.getIdWeb() == ficheSecurite
							.getEmbarcation().getIdWeb())
				selectedIndex = i;
		}

	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final CharSequence[] charSequenceItems = items.toArray(new CharSequence[items.size()]);
		builder.setTitle(R.string.fiche_info_dialogie_embarcation_title)
				.setSingleChoiceItems(charSequenceItems,
						selectedIndex, this)
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

			if (selectedIndex == -1)
				ficheSecurite.setEmbarcation(null);
			else
				ficheSecurite.setEmbarcation(embarcations.get(selectedIndex));

			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_embarcation_value))
					.setText(ficheSecurite.getEmbarcation() != null ? ficheSecurite
							.getEmbarcation().getLibelle() : "");
		}

		super.dismiss();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		selectedIndex = which;
	}
}
