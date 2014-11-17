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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this);
	}
}