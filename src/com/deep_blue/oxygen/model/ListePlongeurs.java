package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListePlongeurs extends ArrayList<Plongeur> implements Parcelable {

	public static final Parcelable.Creator<ListePlongeurs> CREATOR = new Parcelable.Creator<ListePlongeurs>() {
		public ListePlongeurs createFromParcel(Parcel in) {
			return new ListePlongeurs(in);
		}

		@Override
		public ListePlongeurs[] newArray(int size) {
			return null;
		}
	};

	public ListePlongeurs() {
		super();
	}

	public ListePlongeurs(Parcel in) {
		super();
		in.readTypedList(this, Plongeur.CREATOR);
	}
	
	/**
	 * Permet de retourner le prochain id negatif pour la création des plongeurs avant qu'il soit enregistrer en base
	 * @return
	 */
	public Long getNextNegativeId(){
		Long result = -1L;
		
		for(Plongeur plongeur : this){
			if(plongeur.getId() <= result){
				result = plongeur.getId()-1L;
			}
		}
		
		return result;
	}

	/**
	 * Retire le plongeur de la liste et renvoi true si un plongeur a effectivement été retirer ou false sinon
	 * @param plongeur
	 * @return
	 */
	public boolean remove(Plongeur plongeur) {
		if (plongeur != null) {
			for (int i = 0; i < size(); i++) {
				if (get(i).getId() == plongeur.getId()) {
					remove(i);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Ajoute ou met à jours le plongeur passé en paramètre dans la liste des plongeurs
	 * Ne fait rien si le plongeur est null
	 * @param plongeur
	 */
	public void ajouterOuMajPlongeur(Plongeur plongeur){
		boolean plongeurMaj = false;
		if (plongeur != null) {
			for (int i = 0; i < size(); i++) {
				Plongeur plongeurInList = get(i);
				if (plongeurInList.getId() == plongeur.getId()) {
					remove(i);
					add(i, plongeur);
					plongeurMaj = true;
				}
			}

			if (!plongeurMaj) {
				add(plongeur);
			}
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this);
	}
}