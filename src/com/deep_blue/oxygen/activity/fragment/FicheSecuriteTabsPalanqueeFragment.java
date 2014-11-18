package com.deep_blue.oxygen.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.model.Palanquee;
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
			
			
        }
        else
        	System.out.println("Affichage du fragment sans palanquee");
        
        return rootView;
    }
}