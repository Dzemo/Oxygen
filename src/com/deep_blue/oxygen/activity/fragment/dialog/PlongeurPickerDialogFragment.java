package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurPickerDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

	private CharSequence[] listePlongeurSuggeresLabel;
	private List<Plongeur> listePlongeurSuggeres;
	
	 /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PlongeurPickerDialogListener {
        public void onPlongeurSelection(Plongeur plongeurSelectionne);
    }
    
	private PlongeurPickerDialogListener mListener;
	
	public PlongeurPickerDialogFragment(List<Plongeur> listePlongeurSuggeres) {
		super();
		this.listePlongeurSuggeres = listePlongeurSuggeres;
		
		listePlongeurSuggeresLabel = new String[listePlongeurSuggeres.size()+1];
		
		listePlongeurSuggeresLabel[0] = "Nouveau plongeur";
		int i = 1;
		for(Plongeur plongeur : listePlongeurSuggeres){
			listePlongeurSuggeresLabel[i] = plongeur.getPrenom() + " " + plongeur.getPrenom() + " " + plongeur.getDateNaissance();
			i++;
		}
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle(R.string.palanquee_dialog_ajout_plongeur_titre)
	           .setItems(listePlongeurSuggeresLabel, this);
	    return builder.create();

	}

	 // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PlongeurPickerDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which < 0 || which > listePlongeurSuggeresLabel.length){
			// Clique en dehors du fragment
			return;
		} else if(which == 0){
			// Clique sur nouveau plongeur
			mListener.onPlongeurSelection(null);
		} else {
			// Selection d'un plongeur existant
			mListener.onPlongeurSelection(listePlongeurSuggeres.get(which-1));
		}
	}
}
