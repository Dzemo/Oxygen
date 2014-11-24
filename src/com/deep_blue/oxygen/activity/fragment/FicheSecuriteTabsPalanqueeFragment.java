package com.deep_blue.oxygen.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;

public class FicheSecuriteTabsPalanqueeFragment extends Fragment {
	
    private Palanquee palanquee;
    
    public FicheSecuriteTabsPalanqueeFragment(Palanquee palanquee){
    	super();
    	this.palanquee = palanquee;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
       
    	// The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.activity_fiche_securite_fragment_palanquee, container, false);
        
        if(palanquee != null){			

			// Initialisation de la vue avec la palanquée séléctionné

			// Info général de la palanquée
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_gaz_value))
					.setText(palanquee.getTypeGaz().toString());
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_plongee_value))
					.setText(palanquee.getTypePlonge().toString());
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_prevue_value))
					.setText(palanquee.getProfondeurPrevue().toString());
			if(palanquee.getProfondeurRealisee() > 0)
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_realisee_value))
					.setText(palanquee.getProfondeurRealisee().toString());
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_prevue_value))
					.setText(DateStringUtils.secondsToNiceString(palanquee.getDureePrevue()));
			if(palanquee.getDureeRealisee() > 0)
				((TextView) rootView
						.findViewById(R.id.textView_palanquee_info_duree_realisee_value))
						.setText(DateStringUtils.secondsToNiceString(palanquee.getDureeRealisee()));
			
			//Moniteur si présent
			if(palanquee.getMoniteur() != null){
				Moniteur moniteur = palanquee.getMoniteur();
				
				((TextView) rootView
						.findViewById(R.id.textView_palanquee_moniteur))
						.setText("Moniteur: "+moniteur.getPrenom()+" "+moniteur.getNom());
			}
			else{
				rootView.findViewById(R.id.palanquee_moniteur).setVisibility(View.GONE);
			}
			
			//Ajout des plongeurs
			TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.TableLayout_palanquee_plongeurs);

			int i = 0;
			int parite_background = palanquee.getMoniteur() != null ? 1 : 0;
			
			int pxPadding = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, R.dimen.panel_padding_size, rootView.getContext().getResources().getDisplayMetrics()));
			
			for (Plongeur plongeur : palanquee.getPlongeurs()) {

				TableRow row = new TableRow(rootView.getContext());
				TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
				lp.setMargins(pxPadding, pxPadding, pxPadding, pxPadding);
				row.setLayoutParams(lp);
				
				TextView tvPlongeur = new TextView(rootView.getContext());
				tvPlongeur.setText("    Plongeur: "+plongeur.getPrenom()+" "+plongeur.getNom());

				
				// Coloration selon la parité de la row
				if (parite_background % 2 == 0)
					row.setBackgroundResource(R.drawable.list_item_background_1);
				else
					row.setBackgroundResource(R.drawable.list_item_background_2);
				parite_background++;				

				// Ajout du contenu de la row et ajout de la row dans la table
				row.addView(tvPlongeur);
				
				tableLayout.addView(row, i, lp);
				i++;
			}
			
        }
        else
        	System.out.println("Affichage du fragment sans palanquee");
        
        return rootView;
    }
}