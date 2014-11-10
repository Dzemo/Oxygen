package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListePlongeurs extends ArrayList<Plongeur> implements Parcelable{

	public static final Parcelable.Creator<ListePlongeurs> CREATOR = new Parcelable.Creator<ListePlongeurs>()
    {
        public ListePlongeurs createFromParcel(Parcel in)
        {
            return new ListePlongeurs(in);
        }
 
        @Override
        public ListePlongeurs[] newArray(int size) {
            return null;
        }
    };
		    
	public ListePlongeurs()
	{
		 super();
    }
 
    public ListePlongeurs(Parcel in)
    {
    	 // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du nombre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
        	Plongeur plongeur = in.readParcelable(Plongeur.class.getClassLoader());
            this.add(plongeur);
        }
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
        	Plongeur plongeur = this.get(i);
        	plongeur.writeToParcel(dest, flags);
        }
    }
}