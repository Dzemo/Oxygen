package com.deep_blue.oxygen.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheDateDialogFragment;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.util.DateStringUtils;

public class FicheSecuriteTabsInfoFragment extends Fragment {

	private FicheSecurite ficheSecurite = null;
	private View rootView;
	
	public FicheSecuriteTabsInfoFragment(FicheSecurite ficheSecurite) {
		super();
		this.ficheSecurite = ficheSecurite;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// The last two arguments ensure LayoutParams are inflated
		// properly.
		rootView = inflater.inflate(
				R.layout.activity_fiche_securite_fragment_info, container,
				false);

		if (ficheSecurite != null) {
			// Initialisation de la vue avec la fiche de sécurité séléctionné

			// Info général de la fiche
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_date_value))
					.setText(DateStringUtils.timestampsToDate(ficheSecurite.getTimestamp()));
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_site_value))
					.setText(ficheSecurite.getSite() != null ? ficheSecurite.getSite().getNom() : "");
			String directeurPlongeValue = ficheSecurite.getDirecteurPlonge()
					.getPrenom()
					+ " "
					+ ficheSecurite.getDirecteurPlonge().getNom();
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_directeur_plonge_value))
					.setText(directeurPlongeValue);
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_embarcation_value))
					.setText(ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getLibelle() : "");
		}
		
		//Ajout du clique de modification de la date
		rootView.findViewById(R.id.iB_infos_date).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {				
				FicheDateDialogFragment ndf = new FicheDateDialogFragment(rootView,ficheSecurite);
				ndf.show(getFragmentManager(),"TAG");
			}			
		});

		return rootView;
	}
}