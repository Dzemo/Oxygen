package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListePalanquees extends ArrayList<Palanquee> implements Parcelable{

	public static final Parcelable.Creator<ListePalanquees> CREATOR = new Parcelable.Creator<ListePalanquees>()
    {
        public ListePalanquees createFromParcel(Parcel in)
        {
            return new ListePalanquees(in);
        }
 
        @Override
        public ListePalanquees[] newArray(int size) {
            return null;
        }
    };
		    
	public ListePalanquees()
	{
		 super();
    }
 
    public ListePalanquees(Parcel in)
    {
    	 // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du nombre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
        	Palanquee palanquee = in.readParcelable(Palanquee.class.getClassLoader());
            this.add(palanquee);
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
        	Palanquee palanquee = this.get(i);
        	palanquee.writeToParcel(dest, flags);
        }
    }
}