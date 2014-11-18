package com.deep_blue.oxygen.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.deep_blue.oxygen.activity.fragment.FicheSecuriteTabsInfoFragment;
import com.deep_blue.oxygen.activity.fragment.FicheSecuriteTabsPalanqueeFragment;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;

public class FicheSecuriteTabsAdapter extends FragmentStatePagerAdapter {

	private FicheSecurite ficheSecurite;

	public FicheSecuriteTabsAdapter(FragmentManager fm,
			FicheSecurite ficheSecurite) {
		super(fm);
		this.ficheSecurite = ficheSecurite;
	}

	@Override
	public Fragment getItem(int index) {

		if (index == 0)
			return getFicheInfosFragment();
		else
			return getPalanqueeFragment(index);
	}

	@Override
	public int getCount() {
		if (ficheSecurite.getPalanquees() != null)
			return ficheSecurite.getPalanquees().size() + 1;
		else
			return 1;
	}

	private Fragment getFicheInfosFragment() {
		Fragment fragment = new FicheSecuriteTabsInfoFragment(ficheSecurite);

		return fragment;
	}

	private Fragment getPalanqueeFragment(int index) {
		Palanquee palanquee = null;
		if(ficheSecurite.getPalanquees() != null && index-1 < ficheSecurite.getPalanquees().size()){
			palanquee = ficheSecurite.getPalanquees().get(index-1);
		}

		Fragment fragment = new FicheSecuriteTabsPalanqueeFragment(palanquee);
		//Bundle args = new Bundle();
		// Our object is just an integer :-P
		//args.putInt(FicheSecuriteTabsInfoFragment.ARG_OBJECT, index + 1);
		//fragment.setArguments(args);

		return fragment;
	}
}
