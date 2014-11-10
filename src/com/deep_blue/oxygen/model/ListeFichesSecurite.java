package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListeFichesSecurite  extends ArrayList<FicheSecurite> implements Parcelable{

	public static final Parcelable.Creator<ListeFichesSecurite> CREATOR = new Parcelable.Creator<ListeFichesSecurite>()
    {
        public ListeFichesSecurite createFromParcel(Parcel in)
        {
            return new ListeFichesSecurite(in);
        }
 
        @Override
        public ListeFichesSecurite[] newArray(int size) {
            return null;
        }
    };
		    
	public ListeFichesSecurite()
	{
		 super();
    }
 
    public ListeFichesSecurite(Parcel in)
    {
    	 // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du nombre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
        	FicheSecurite ficheSecurite = in.readParcelable(FicheSecurite.class.getClassLoader());
            this.add(ficheSecurite);
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
        	FicheSecurite ficheSecurite = this.get(i);
        	ficheSecurite.writeToParcel(dest, flags);
        }
    }
}
