package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.ConfirmDialogFragment.ConfirmDialogListener;

public class ListeErreursDialogFragment extends DialogFragment {
    
	//Valeur envoyé lors du clique pour savoir qu'elle confirmation était demandée
	public static final int LISTE_ERREURS_ENREGISTREMENT = 1002;
	public static final int LISTE_ERREURS_CLOTURE = 1001;
	
    // Use this instance of the interface to deliver action events
    private ConfirmDialogListener mListener;
    
    //Pour savoir de quel type de liste d'erreur il s'agit
    private int confirmType;

    //Liste des erreurs à afficher
    private List<String> erreurs;
    
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ListeErreursDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int confirmType);
        public void onDialogNegativeClick(DialogFragment dialog, int confirmType);
    }
    
    public ListeErreursDialogFragment(List<String> erreurs, int confirmType){
    	this.confirmType = confirmType;
    	this.erreurs = erreurs;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {    	
    	//Création de la liste 
    	List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    	for(String erreur : erreurs){
    		HashMap<String, String> hashMap = new HashMap<String, String>(1);
    		hashMap.put("erreur", erreur);
    		data.add(hashMap);
    	}
    	
    	ListView view = new ListView(getActivity());
    	view.setAdapter(new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_1, new String[]{"erreur"}, new int[]{android.R.id.text1}));
    	
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getQuantityText(R.plurals.fiche_info_dialog_erreurs, erreurs.size()))
        	   .setView(view)
               .setNegativeButton(R.string.dialog_corriger, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the negative button event back to the host activity
                       mListener.onDialogNegativeClick(ListeErreursDialogFragment.this, confirmType);
                   }
               });
        
        if(confirmType != LISTE_ERREURS_CLOTURE){
        	builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Send the positive button event back to the host activity
                    mListener.onDialogPositiveClick(ListeErreursDialogFragment.this, confirmType);
                }
            });
        }
        
        return builder.create();
    }
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ConfirmDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}