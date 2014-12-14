package com.deep_blue.oxygen.activity.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.EnumPlongeurText;

/**
 * Permet de modifier le nom, prenom, téléphone ou téléphone d'urgence d'un
 * plongeur
 * 
 * @author Raphael
 * 
 */
@SuppressLint("InflateParams")
public class PlongeurTextDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private Plongeur plongeur;
	private String text;
	private EnumPlongeurText typeEdition;
	private int resourceTitleId;
	private int editTextInputType;

	public PlongeurTextDialogFragment(View rootView, Plongeur plongeur,
			EnumPlongeurText typeEdition) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		this.typeEdition = typeEdition != null ? typeEdition
				: EnumPlongeurText.NOM;

		switch (typeEdition) {
		case NOM:
			text = plongeur.getNom();
			resourceTitleId = R.string.plongeur_dialog_nom_title;
			editTextInputType = InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS;
			break;
		case PRENOM:
			text = plongeur.getPrenom();
			resourceTitleId = R.string.plongeur_dialog_prenom_title;
			editTextInputType = InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS;
			break;
		case TELEPHONE:
			text = plongeur.getTelephone();
			resourceTitleId = R.string.plongeur_dialog_telephone_title;
			editTextInputType = InputType.TYPE_CLASS_PHONE;
			break;
		case TELEPHONE_URGENCE:
			text = plongeur.getTelephoneUrgence();
			resourceTitleId = R.string.plongeur_dialog_telephone_urgence_title;
			editTextInputType = InputType.TYPE_CLASS_PHONE;
			break;
		default:
			typeEdition = EnumPlongeurText.NOM;
			text = plongeur.getNom();
			resourceTitleId = R.string.plongeur_dialog_nom_title;
			editTextInputType = InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS;
			break;
		}
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater.inflate(R.layout.dialog_plongeur_text, null);

		// Initialisation du EditText
		EditText editText = (EditText) dialogView
				.findViewById(R.id.editText_plongeur_text);
		editText.setText(text);
		editText.setInputType(editTextInputType);

		// builder
		builder.setView(dialogView)
				.setTitle(resourceTitleId)
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
			//Mise à jours du text du plongeur
			text = ((EditText) dialogView
					.findViewById(R.id.editText_plongeur_text)).getText()
					.toString();
			switch (typeEdition) {
			case NOM:
				plongeur.setNom(text);
				((TextView) rootView
						.findViewById(R.id.textView_plongeur_nom_value))
						.setText(plongeur.getNom());
				break;
			case PRENOM:
				plongeur.setPrenom(text);
				((TextView) rootView
						.findViewById(R.id.textView_plongeur_prenom_value))
						.setText(plongeur.getPrenom());
				break;
			case TELEPHONE:
				plongeur.setTelephone(text);
				((TextView) rootView
						.findViewById(R.id.textView_plongeur_telephone_value))
						.setText(plongeur.getTelephone());
				break;
			case TELEPHONE_URGENCE:
				plongeur.setTelephoneUrgence(text);
				((TextView) rootView
						.findViewById(R.id.textView_plongeur_telephone_urgence_value))
						.setText(plongeur.getTelephoneUrgence());
				break;
			}
		}

		super.dismiss();
	}
}