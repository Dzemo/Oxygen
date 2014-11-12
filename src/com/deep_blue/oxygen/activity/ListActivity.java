package com.deep_blue.oxygen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
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
	        
	        TextView tvIcon = new TextView(this);
	        tvIcon.setText(ficheSecurite.getEtat().toString());
	        TextView tvDescription = new TextView(this);
	        String textDescription = "Plongé à "+ficheSecurite.getSite()+" le "+ficheSecurite.getTimestamp();
	        tvDescription.setText(textDescription);
	       
	        row.setOnClickListener(new OnClickListener(){

	            @Override
	            public void onClick(View v){
	                // TODO Auto-generated method stub
	                //row_id=contact_table.indexOfChild(row);
	            	Intent intent = new Intent(ListActivity.this, FicheSecuriteActivity.class);
	            	startActivity(intent);
	            }
	        });
	        
	        if(i % 2 == 0)
	        	row.setBackgroundResource(R.drawable.list_item_background_1);
	        else
	        	row.setBackgroundResource(R.drawable.list_item_background_2);
	        
	        row.addView(tvIcon);
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