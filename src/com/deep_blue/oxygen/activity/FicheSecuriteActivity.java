package com.deep_blue.oxygen.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.adapter.FicheSecuriteTabsAdapter;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.util.IntentKey;

public class FicheSecuriteActivity extends FragmentActivity {

	// When requested, this adapter returns a FicheSecuriteTabsInfoFragment or
	// FicheSecuriteTabsPalanqueeFragment,
	// representing an object in the collection.
	private FicheSecuriteTabsAdapter ficheSecuriteTabsAdapter;
	private ViewPager mViewPager;

	private FicheSecurite ficheSecurite;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiche_securite);
		final ActionBar actionBar = getActionBar();

		// Récupération de la fiche de sécurité dans l'Intent
		Intent intent = getIntent();
		ficheSecurite = intent
				.getParcelableExtra(IntentKey.FICHE_SECURITE_COURANTE
						.toString());
		// ViewPager and its adapters use support library
		// fragments, so use getSupportFragmentManager.
		ficheSecuriteTabsAdapter = new FicheSecuriteTabsAdapter(
				getSupportFragmentManager(), ficheSecurite);
		mViewPager = (ViewPager) findViewById(R.id.fiche_securite_tabs);
		mViewPager.setAdapter(ficheSecuriteTabsAdapter);

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// When the tab is selected, switch to the
				// corresponding page in the ViewPager.
				mViewPager.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// ignore this event
			}

			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// ignore this event
			}
		};

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		// Ajout de la tabs info index 0
		actionBar.addTab(actionBar.newTab().setText("Infos")
				.setTabListener(tabListener));
		// Ajout des tabs palanquée à la suite
		if (ficheSecurite.getPalanquees() != null) {
			for (Palanquee palanquee : ficheSecurite.getPalanquees()) {
				actionBar.addTab(actionBar.newTab()
						.setText("Pal " + palanquee.getNumero())
						.setTabListener(tabListener));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.fiche_securite, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;

		case R.id.itemParam:
			Intent intent = new Intent(FicheSecuriteActivity.this,
					ParametreActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
