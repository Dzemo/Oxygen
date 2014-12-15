package com.deep_blue.oxygen.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.util.PreferenceKey;

public class ParametreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametre);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		((EditText) findViewById(R.id.editText_parametre_remote_url))
				.setText(preferences.getString(
						PreferenceKey.REMOTE_URL.toString(), "indéfini"));
		((CheckBox) findViewById(R.id.checkBox_save_login))
				.setChecked(preferences.getBoolean(
						PreferenceKey.SAVE_LOGIN.toString(), false));
		((CheckBox) findViewById(R.id.checkBox_keep_connection))
				.setChecked(preferences.getBoolean(
						PreferenceKey.KEEP_CONNECTION.toString(), false));
		((TextView) findViewById(R.id.textView_parametre_last_synch_value))
				.setText(preferences.getString(
						PreferenceKey.LAST_SYNCH.toString(), "jamais"));

		if (preferences.getBoolean(
				PreferenceKey.RETREIVE_TYPE_ALL_FICHE.toString(), true))
			((RadioGroup) findViewById(R.id.radioGroup_retreive_type))
					.check(R.id.radio_parametre_retrieve_type_all);
		else
			((RadioGroup) findViewById(R.id.radioGroup_retreive_type))
					.check(R.id.radio_parametre_retrieve_type_only_own);

		int retrieveLength = preferences.getInt(
				PreferenceKey.RETREIVE_LENGTH.toString(), 2);
		final TextView textViewRetrieveLength = (TextView) findViewById(R.id.textView_parametre_retrieve_length_label);
		if (retrieveLength > 0)
			textViewRetrieveLength.setText(getResources().getQuantityString(
					R.plurals.parametre_retrieve_length_label, retrieveLength,
					retrieveLength));
		else
			textViewRetrieveLength
					.setText(R.string.parametre_retrieve_length_label_zero);
		SeekBar seekBarRetrieveLength = (SeekBar) findViewById(R.id.seekBar_retrieve_length);
		seekBarRetrieveLength.setProgress(retrieveLength);
		seekBarRetrieveLength
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (progress > 0)
							textViewRetrieveLength
									.setText(getResources()
											.getQuantityString(
													R.plurals.parametre_retrieve_length_label,
													progress, progress));
						else
							textViewRetrieveLength
									.setText(R.string.parametre_retrieve_length_label_zero);
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parametre, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			return true;
		case R.id.itemSave:
			savePreferences();
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	/**
	 * Save the preferences
	 */
	private void savePreferences() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor pEditor = preferences.edit();

		boolean checkedSaveLogin = ((CheckBox) findViewById(R.id.checkBox_save_login))
				.isChecked();
		if (!checkedSaveLogin) {
			pEditor.remove(PreferenceKey.SAVED_LOGIN.toString());
		}
		pEditor.putBoolean(PreferenceKey.SAVE_LOGIN.toString(),
				checkedSaveLogin);

		boolean checkedKeepConnection = ((CheckBox) findViewById(R.id.checkBox_save_login))
				.isChecked();
		if (!checkedKeepConnection) {
			pEditor.remove(PreferenceKey.KEPT_CONNECTION_LOGIN.toString());
			pEditor.remove(PreferenceKey.KEPT_CONNECTION_VERSION.toString());
		}
		pEditor.putBoolean(PreferenceKey.KEEP_CONNECTION.toString(),
				checkedKeepConnection);

		pEditor.putString(PreferenceKey.REMOTE_URL.toString(),
				((EditText) findViewById(R.id.editText_parametre_remote_url))
						.getText().toString());

		pEditor.putString(PreferenceKey.LAST_SYNCH.toString(), preferences
				.getString(PreferenceKey.LAST_SYNCH.toString(), "jamais"));

		if (((RadioGroup) findViewById(R.id.radioGroup_retreive_type))
				.getCheckedRadioButtonId() == R.id.radio_parametre_retrieve_type_all) {
			pEditor.putBoolean(
					PreferenceKey.RETREIVE_TYPE_ALL_FICHE.toString(), true);
		} else {
			pEditor.putBoolean(
					PreferenceKey.RETREIVE_TYPE_ALL_FICHE.toString(), false);
		}

		pEditor.putInt(PreferenceKey.RETREIVE_LENGTH.toString(),
				((SeekBar) findViewById(R.id.seekBar_retrieve_length))
						.getProgress());

		pEditor.apply();

		Toast toast = Toast.makeText(this, R.string.parametre_saved,
				Toast.LENGTH_SHORT);
		toast.show();
	}
}
