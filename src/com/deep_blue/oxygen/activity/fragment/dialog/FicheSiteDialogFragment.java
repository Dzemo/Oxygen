package com.deep_blue.oxygen.activity.fragment.dialog;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.SiteDao;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Site;

@SuppressLint("InflateParams")
public class FicheSiteDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	private FicheSecurite ficheSecurite;
	private Site site;
	private String siteNom;
	private Map<String,Site> sitesMap;

	public FicheSiteDialogFragment(View rootView, FicheSecurite ficheSecurite) {
		super();
		this.rootView = rootView;
		this.ficheSecurite = ficheSecurite;

		if (ficheSecurite.getSite() != null) {
			this.site = ficheSecurite.getSite();
			this.siteNom = this.site.getNom();
		} else {
			this.site = null;
			this.siteNom = "";
		}
		
		//Récupération de la liste des sites existants
		sitesMap = new HashMap<String,Site>();
		SiteDao siteDao = new SiteDao(rootView.getContext());
		SparseArray<Site> sites = siteDao.getAll();
		int key = 0;
		for(int i = 0; i < sites.size(); i++) {
		   key = sites.keyAt(i);
		   Site site = sites.get(key);
		   sitesMap.put(site.getNom(),site);
		}
	}

	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// INitialisation du builder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// Création de la vue
		LayoutInflater inflater = getActivity().getLayoutInflater();
		dialogView = inflater
				.inflate(R.layout.dialog_fiche_site, null);

		// Initialisation de l'autocomplete
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, sitesMap.keySet().toArray(new String[0]) );
		AutoCompleteTextView autoComplete = ((AutoCompleteTextView) dialogView.findViewById(R.id.aCTV_fiche_site));
		autoComplete.setAdapter(adapter);
		autoComplete.setText(siteNom);
		autoComplete.dismissDropDown();
		
		// builder
		builder.setView(dialogView)
				.setTitle(R.string.fiche_info_dialogie_date_title)
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
			String selectedNom = ((AutoCompleteTextView) dialogView.findViewById(R.id.aCTV_fiche_site)).getText().toString();
			if(sitesMap.get(selectedNom) != null){
				ficheSecurite.setSite(sitesMap.get(selectedNom));
			}
			else if(!selectedNom.isEmpty()){
				Site site = new Site(-1, null, selectedNom, "", false);
				ficheSecurite.setSite(site);
			}
		
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_site_value))
					.setText(ficheSecurite.getSite() != null ? ficheSecurite
							.getSite().getNom() : "");
		}
		
		super.dismiss();
	}
}