package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.AptitudeDao;
import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurAptitudeDialogFragment extends DialogFragment{
	private View rootView;
	private View dialogView;
	private Plongeur plongeur;
	private List<String> items;
	private SparseArray<Aptitude> allAptitudes;
	private boolean[] checkedItems;
	
	private HashMap<Integer,Aptitude> selectedItems= new HashMap<Integer,Aptitude>();
	
	// Constructeur
	public PlongeurAptitudeDialogFragment(View rootView, Plongeur plongeur, Palanquee palanquee) {
		super();
		this.rootView = rootView;
		this.plongeur = plongeur;
		
		// Nous avons besoin d'un Aptitude DAO pour aller chercher la liste des Aptitudes qu'il existe
		AptitudeDao aptitudeDao = new AptitudeDao(rootView.getContext());
		allAptitudes = aptitudeDao.getAll();
		
		// Items va contenir les aptitudes selectionné
		items = new ArrayList<String>();
		
		// checkedItems est un booléen qui exprime si la checkbox est coché ou pas, donc, si le plongeur a, ou pas, l'aptitude correspondante
		checkedItems = new boolean[allAptitudes.size()];		
		int key = 0;
		
		// On parcourt toutes les aptitudes de la base de donnée
		for (int i = 0; i < allAptitudes.size(); i++) {
			key = allAptitudes.keyAt(i);
			Aptitude aptitude = allAptitudes.get(key);
			
			// On ajoute à la liste le libellé de chacune des aptitudes
			items.add(aptitude.getLibelleCourt());
			
			// On initialise les items checké à faux
			checkedItems[i] = false;
			
			// Puis si il devrait y en avoir qui soit à vrai (et qui soit donc déjà coché quand le dialog pop
			if (plongeur.getAptitudes() != null){
				for (int j = 0; j < plongeur.getAptitudes().size(); j++) {
					if (plongeur.getAptitudes().get(j).getIdWeb() == aptitude.getIdWeb()){
						// Si le plongeur a des aptitudes dans la liste des aptitudes, on met ces aptitudes cochés
						checkedItems[i] = true;
						selectedItems.put(i+1, allAptitudes.get(i+1));
					}
				}			
			}
		}
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// On insère notre Liste dans un CharSequence (dont nous avons besoin pour la méthode setMultiChoiceItems
		final CharSequence[] charSequenceItems = items.toArray(new CharSequence[items.size()]);
		
		// Initialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    // builder
 		builder.setView(dialogView)
 				.setTitle(R.string.plongeur_dialog_aptitudes_title)
 				.setMultiChoiceItems(charSequenceItems, checkedItems,
                         new DialogInterface.OnMultiChoiceClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int indexSelected,
                          boolean isChecked) {
                      if (isChecked) {
						// If the user checked the item, add it to the selected items
                          selectedItems.put(indexSelected+1, allAptitudes.get(indexSelected+1));
                      } else {
                          // Else, if the item is already in the array, remove it 
                          selectedItems.remove(indexSelected+1);
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
			// On set les aptitudes avec celles qui sont dans l'arraylist
			plongeur.setAptitudes(new ListeAptitudes(selectedItems.values()));
			
			for (int i = 0; i < selectedItems.size(); i++) {
			((TextView) rootView
					.findViewById(R.id.textView_plongeur_aptitudes_value))
					.setText(plongeur.getAptitudes().toString());
			}
		}
		
		super.dismiss();
	}
}
