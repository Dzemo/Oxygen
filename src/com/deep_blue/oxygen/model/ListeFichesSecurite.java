package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class ListeFichesSecurite extends ArrayList<FicheSecurite> implements
		Parcelable {

	public static final Parcelable.Creator<ListeFichesSecurite> CREATOR = new Parcelable.Creator<ListeFichesSecurite>() {
		public ListeFichesSecurite createFromParcel(Parcel in) {
			return new ListeFichesSecurite(in);
		}

		@Override
		public ListeFichesSecurite[] newArray(int size) {
			return null;
		}
	};

	public ListeFichesSecurite() {
		super();
	}

	public ListeFichesSecurite(Parcel in) {
		super();
		in.readTypedList(this, FicheSecurite.CREATOR);
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
