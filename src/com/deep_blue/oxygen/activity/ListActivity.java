package com.deep_blue.oxygen.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListeFichesSecurite;
import com.deep_blue.oxygen.model.Utilisateur;

public class ListActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		Intent intent = getIntent();
		Utilisateur utilisateur = (Utilisateur) intent.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		System.out.println("Utilisateur courant:"+utilisateur);

		TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
		FicheSecuriteDao ficheSecuriteDao = new FicheSecuriteDao(this);
		ListeFichesSecurite listeFiche = ficheSecuriteDao.getAll();
		
		int i = 0;
		for(FicheSecurite ficheSecurite : listeFiche){
			
			TableRow row= new TableRow(this);
	        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(lp);
	        ImageView ivIcon = new ImageView(this);
	        if(ficheSecurite.getEtat().equals(EnumEtat.SYNCHRONISE)){
		        ivIcon.setImageResource(android.R.drawable.ic_menu_edit);
		        ivIcon.setAdjustViewBounds(true);
		        //ivIcon.setPadding(5, 25, 5, 25);
	        }
	        else if(ficheSecurite.getEtat().equals(EnumEtat.VALIDE)){
		        ivIcon.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
		        ivIcon.setAdjustViewBounds(true);
		        //ivIcon.setPadding(5, 25, 5, 25);
	        }
	        
	        TextView tvDescription = new TextView(this);
	        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(ficheSecurite.getTimestamp()*1000));
	        String hourString = new SimpleDateFormat("hh:mm").format(new Date(ficheSecurite.getTimestamp()*1000));

	        String textDescription = "Plong� � "+ficheSecurite.getSite()+" le "+dateString+" � "+hourString;
	        tvDescription.setText(textDescription);
	        //tvDescription.setPadding(15, 0, 15, 0);
	       
	       
	        
	        if(i % 2 == 0)
	        	row.setBackgroundResource(R.drawable.list_item_background_1);
	        else
	        	row.setBackgroundResource(R.drawable.list_item_background_2);
	        
			row.addView(ivIcon);
	        row.addView(tvDescription);
	        tableLayout.addView(row,i);
	        
	        i++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		
		return super.onOptionsItemSelected(item);
	}
}