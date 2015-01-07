package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Utilisateur;

public class DirecteurPlongerPickerDialogFragment extends DialogFragment
		implements DialogInterface.OnClickListener {

    // Use this instance of the interface to deliver action events
    private DirecteurPlongerPickListener mListener;
    
    private Utilisateur utilisateurCourant;
    private List<Moniteur> directeurs;
	private List<String> items;
    
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface DirecteurPlongerPickListener {
        public void onDirecteurPlongerPick(Boolean selected, Moniteur directeurPlonge);
    }
    
    public DirecteurPlongerPickerDialogFragment(Utilisateur utilisateurCourant, List<Moniteur> directeurs){    	
    	this.directeurs = directeurs;
    	this.utilisateurCourant = utilisateurCourant;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
    	//Création de la liste des options
    	items = new ArrayList<String>();
		for (int i = 0; i < directeurs.size(); i++) {
			Moniteur moniteur = directeurs.get(i);
			if(utilisateurCourant.getMoniteurAssocie() != null && utilisateurCourant.getMoniteurAssocie().getIdWeb() == moniteur.getIdWeb())
				//Pour l'utilisateur courant, on remplace par "Moi"
				items.add(getResources().getString(R.string.dialog_moi));
			else
				items.add(moniteur.getPrenom() + " " + moniteur.getNom() + " (" + moniteur.getAptitudes().toString() + ")");
		}
		
		//Pour créer une nouvelle fiche sans directeur de plongé
		items.add(getResources().getString(R.string.dialog_aucun));	
		
		//On ajoute un bouton pour annuler a la fin, qui ferme le DialogFragment sans créer de nouvelle fiche
		items.add(getResources().getString(R.string.dialog_annuler));		
    	
    	final CharSequence[] charSequenceItems = items
				.toArray(new CharSequence[items.size()]);    	
    	
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("").setItems(charSequenceItems, this);
		return builder.create();

    }
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DirecteurPlongerPickListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DirecteurPlongerPickListener");
        }
    }
    
    @Override
	public void onClick(DialogInterface dialog, int which) {
    	if (which == -1 || which > directeurs.size()){
    		//Clique en dehors du fragment ou sur l'element annuler
    		mListener.onDirecteurPlongerPick(false, null);
    	} 
    	else if(which == directeurs.size()){
    		//Clique sur le bouton aucun
    		mListener.onDirecteurPlongerPick(true, null);
    	} 
    	else{
    		//Clique sur un des directeurs
    		mListener.onDirecteurPlongerPick(true, directeurs.get(which));
    	}
	}
}