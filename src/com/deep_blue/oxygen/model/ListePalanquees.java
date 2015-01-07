package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListePalanquees extends ArrayList<Palanquee> implements Parcelable {

	public static final Parcelable.Creator<ListePalanquees> CREATOR = new Parcelable.Creator<ListePalanquees>() {
		public ListePalanquees createFromParcel(Parcel in) {
			return new ListePalanquees(in);
		}

		@Override
		public ListePalanquees[] newArray(int size) {
			return null;
		}
	};

	public ListePalanquees() {
		super();
	}

	public ListePalanquees(Parcel in) {
		super();
		in.readTypedList(this, Palanquee.CREATOR);
	}
	
	/**
	 * Permet de retourner le prochain id negatif pour la création des palanquées avant qu'il soit enregistrer en base (avec un nouveau id positif)
	 * @return
	 */
	public Long getNextNegativeId(){
		Long result = -1L;
		
		for(Palanquee palanquee : this){
			if(palanquee.getId() <= result){
				result = palanquee.getId()-1L;
			}
		}
		
		return result;
	}
	
	/**
	 * Renvoi le prochain numéro de palanquée
	 * @return
	 */
	public Integer getNextNumero(){
		Integer result = 1;
		
		for(Palanquee palanquee : this){
			if(palanquee.getNumero() >= result){
				result = palanquee.getNumero()+1;
			}
		}
		
		return result;
	}

	/**
	 * Retire la palanquee de la liste et renvoi true si une palanquee a effectivement été retirer ou false sinon
	 * @param palanquee
	 * @return
	 */
	public boolean remove(Palanquee palanquee) {
		if (palanquee != null) {
			for (int i = 0; i < size(); i++) {
				if (get(i).getId() == palanquee.getId()) {
					remove(i);
					return true;
				}
			}
		}
		
		return false;
	}

	
	/**
	 * Ajoute ou met à jours la palanquee passé en paramètre dans la liste des palanquées
	 * Ne fait rien si la palanquee est null
	 * @param palanquee
	 */
	public void ajouterOuMajPalanquee(Palanquee palanquee){
		boolean plongeurMaj = false;
		if (palanquee != null) {
			for (int i = 0; i < size(); i++) {
				Palanquee palanqueeInList = get(i);
				if (palanqueeInList.getId() == palanquee.getId()) {
					remove(i);
					add(i, palanquee);
					plongeurMaj = true;
				}
			}

			if (!plongeurMaj) {
				add(palanquee);
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