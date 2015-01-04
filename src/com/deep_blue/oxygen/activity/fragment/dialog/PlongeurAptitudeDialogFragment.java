package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurAptitudeDialogFragment extends DialogFragment implements OnClickListener{
	private View rootView;
	private View dialogView;
	private Plongeur plongeur;
	private ArrayList selectedItems=new ArrayList();
	// Constructeur
	public PlongeurAptitudeDialogFragment(View rootView, Plongeur plongeur, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		//ListeAptitudes aptitudesParDefaut;
		//if (plongeur.getAptitudes() )
			//aptitudesParDefaut[] = plongeur.getAptitudes();
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// On a la liste de nos aptitudes
		final CharSequence[] items = {"PA-12","PA-20","PA-40","PA-60"};
        // arraylist to keep the selected items
        
		
		
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    // builder
 		builder.setView(dialogView)
 				.setTitle(R.string.plongeur_dialog_aptitudes_title)
 				.setMultiChoiceItems(items, null,
                         new DialogInterface.OnMultiChoiceClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int indexSelected,
                          boolean isChecked) {
                      if (isChecked) {
                          // If the user checked the item, add it to the selected items
                          selectedItems.add(indexSelected);
                      } else if (selectedItems.contains(indexSelected)) {
                          // Else, if the item is already in the array, remove it 
                          selectedItems.remove(Integer.valueOf(indexSelected));
                      }
                  }
              })
               // Set the action buttons
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                      //  Validation de la selection des aptitudes
                	  dismiss(true);
                     
                  }
              })
              .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                     //  Annulation et retour aux aptitudes normales
                	  dismiss(false);
                  }
              });
        
                 return builder.create();
         }

	public void dismiss(boolean type) {
		
		if (type) {
			// On crée une nouvelle ListeAptitude qui contient l'array avec les aptitudes checked
			ListeAptitudes aptitudesSelected = new ListeAptitudes(selectedItems);
					
			// On set les aptitudes avec celles qui sont dans l'arraylist
			plongeur.setAptitudes(aptitudesSelected);
			
			for (int i = 0; i < selectedItems.size(); i++) {
			((TextView) rootView
					.findViewById(R.id.textView_plongeur_aptitudes_value))
					.setText(selectedItems.get(i).toString());
			}
		}
		
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
