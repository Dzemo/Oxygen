package com.deep_blue.oxygen.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.listener.PalanqueeListOnClickListener;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;

public class FicheSecuriteActivity extends Activity {

	private FicheSecurite ficheSecurite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiche_securite);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		ficheSecurite = intent
				.getParcelableExtra(IntentKey.FICHE_SECURITE_COURANTE
						.toString());

		if (ficheSecurite == null) {
			System.out.println("Pas de fiche selectionné !");
			//finish();
			FicheSecuriteDao ficheSecuriteDao = new FicheSecuriteDao(this);
			ficheSecurite = ficheSecuriteDao.getById(1);
		}

		// Initialisation de la vue avec la fiche de sécurité séléctionné
		((TextView) findViewById(R.id.textView_fiche_infos_date_value))
				.setText(ficheSecurite.getTimestamp().toString());
		((TextView) findViewById(R.id.textView_fiche_infos_heure_value))
				.setText(ficheSecurite.getTimestamp().toString());
		((TextView) findViewById(R.id.textView_fiche_infos_site_value))
				.setText(ficheSecurite.getSite());
		String directeurPlongeValue = ficheSecurite.getDirecteurPlonge()
				.getPrenom()
				+ " "
				+ ficheSecurite.getDirecteurPlonge().getNom();
		((TextView) findViewById(R.id.textView_fiche_infos_directeur_plonge_value))
				.setText(directeurPlongeValue);

		TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout_fiche_palanquees);
		int i = 0;
		for (Palanquee palanquee : ficheSecurite.getPalanquees()) {
			TableRow row = new TableRow(this);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(
					TableRow.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(lp);

			// "Palanquée n°X encadree utilisant de l'air à 12m pendant 30min";

			TextView tvDescription = new TextView(this);
			String textDescription = "Palanquée n°" + palanquee.getNumero()
					+ " " + palanquee.getTypePlonge().toString()
					+ " utilisant du gaz " + palanquee.getTypeGaz().toString()
					+ " à "+palanquee.getProfondeurPrevue()+"m "
					+ "pendant "+palanquee.getDureePrevue()+"s";
			tvDescription.setText(textDescription);

			System.out.println(textDescription);
			
			row.setOnClickListener(new PalanqueeListOnClickListener(palanquee, FicheSecuriteActivity.this));
			
			if (i % 2 == 0)
				row.setBackgroundResource(R.drawable.list_item_background_1);
			else
				row.setBackgroundResource(R.drawable.list_item_background_2);
			tableLayout.addView(row, i);
			i++;
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
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
