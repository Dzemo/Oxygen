package com.deep_blue.oxygen.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

@SuppressWarnings("serial")
public class ListeAptitudes extends ArrayList<Aptitude> implements Parcelable {

	public static final Parcelable.Creator<ListeAptitudes> CREATOR = new Parcelable.Creator<ListeAptitudes>() {
		public ListeAptitudes createFromParcel(Parcel in) {
			return new ListeAptitudes(in);
		}

		@Override
		public ListeAptitudes[] newArray(int size) {
			return null;
		}
	};

	public ListeAptitudes() {
		super();
	}

	/**
	 * Initialise avec une liste d'ids
	 * 
	 * @param aptitudesString
	 *            Id d'aptitudes sépéraé par Aptitude.SEPARATOR
	 * @param allAptitudes
	 *            SparseArray contenant toute les aptitudes
	 */
	public ListeAptitudes(String aptitudesString,
			SparseArray<Aptitude> allAptitudes) {
		String aptitudesStringArray[] = aptitudesString
				.split(Aptitude.SEPARATOR);
		for (String aptitudeString : aptitudesStringArray) {
			this.add(allAptitudes.get(Integer.valueOf(aptitudeString)));
		}
	}

	public ListeAptitudes(Parcel in) {
		super();
		in.readTypedList(this, Aptitude.CREATOR);
	}

	/**
	 * Transforme la liste en une chaine de caractere contenant les ids des
	 * aptitudes séparé par Aptitude.SEPARATOR
	 * 
	 * @return
	 */
	public String toIdsList() {
		String result = "";

		for (Aptitude aptitude : this) {
			if (!result.isEmpty())
				result += Aptitude.SEPARATOR;

			result += aptitude.getIdWeb();
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "[";

		for (Aptitude aptitude : this) {
			if (!result.equals("["))
				result += ", ";
			result += aptitude.getLibelleCourt();
		}

		return result + "]";
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
