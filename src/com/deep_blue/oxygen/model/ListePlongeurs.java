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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this);
	}
}