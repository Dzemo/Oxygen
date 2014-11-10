package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

@SuppressWarnings("serial")
public class ListeAptitudes extends ArrayList<Aptitude> implements Parcelable{

	public static final Parcelable.Creator<ListeAptitudes> CREATOR = new Parcelable.Creator<ListeAptitudes>()
    {
        public ListeAptitudes createFromParcel(Parcel in)
        {
            return new ListeAptitudes(in);
        }
 
        @Override
        public ListeAptitudes[] newArray(int size) {
            return null;
        }
    };
		    
	public ListeAptitudes()
	{
		 super();
    }
	
	/**
	 * Initialise avec une liste d'ids
	 * @param aptitudesString Id d'aptitudes sépéraé par Aptitude.SEPARATOR
	 * @param allAptitudes SparseArray contenant toute les aptitudes
	 */
	public ListeAptitudes(String aptitudesString, SparseArray<Aptitude> allAptitudes){		
		String aptitudesStringArray[] = aptitudesString.split(Aptitude.SEPARATOR);
		for(String aptitudeString : aptitudesStringArray){
			this.add(allAptitudes.get(Integer.valueOf(aptitudeString)));
		}
	}
 
    public ListeAptitudes(Parcel in)
    {
    	 // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du nombre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
        	Aptitude aptitude = in.readParcelable(Aptitude.class.getClassLoader());
            this.add(aptitude);
        }
    }
 
    /**
     * Transforme la liste en une chaine de caractere contenant les ids des aptitudes séparé par Aptitude.SEPARATOR
     * @return
     */
    public String toIdsList(){
    	String result = "";
    	
    	for(Aptitude aptitude : this){
    		if(!result.isEmpty()) result += Aptitude.SEPARATOR;
    		
    		result += aptitude.getId();
    	}
    	
    	return result;
    }
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
        	Aptitude aptitude = this.get(i);
        	aptitude.writeToParcel(dest, flags);
        }
    }
}
