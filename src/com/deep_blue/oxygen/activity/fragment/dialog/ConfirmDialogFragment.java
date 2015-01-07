package com.deep_blue.oxygen.activity.fragment.dialog;

import com.deep_blue.oxygen.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ConfirmDialogFragment extends DialogFragment {
    
	//Valeur envoyé lors du clique pour savoir qu'elle confirmation était demandée
	public static final int CLOTURE_FICHE_SECURITE = 4;
	public static final int SUPPRESSION_FICHE_SECURITE = 3;
	public static final int SUPPRESSION_PALANQUEE = 1;
	public static final int SUPPRESSION_PLONGEUR = 2;
	
    // Use this instance of the interface to deliver action events
    private ConfirmDialogListener mListener;
	
    //Le texte afficher dans le dialog
    private String text;
    
    //Pour savoir de quel confirmation il s'agit
    private int confirmType;
    
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ConfirmDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int confirmType);
        public void onDialogNegativeClick(DialogFragment dialog, int confirmType);
    }
    
    public ConfirmDialogFragment(String text, int confirmType){
    	this.text = text;
    	this.confirmType = confirmType;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
               .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the positive button event back to the host activity
                       mListener.onDialogPositiveClick(ConfirmDialogFragment.this, confirmType);
                   }
               })
               .setNegativeButton(R.string.dialog_annuler, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the negative button event back to the host activity
                       mListener.onDialogNegativeClick(ConfirmDialogFragment.this, confirmType);
                   }
               });
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