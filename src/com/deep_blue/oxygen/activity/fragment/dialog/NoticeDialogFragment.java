package com.deep_blue.oxygen.activity.fragment.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.deep_blue.oxygen.R;

public class NoticeDialogFragment extends DialogFragment {
	private View rootView;
	private View dialogView;
	
	public NoticeDialogFragment(View rootView){
		super();
		this.rootView = rootView;
	}
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    dialogView = inflater.inflate(R.layout.notice_layout, null);
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(dialogView)
				.setPositiveButton("Fire", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int id) {
	                       dismiss(true);
	                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss(false);
                   }
               });
	    
		// Create the AlertDialog object and return it
		return builder.create();
	}

	public void dismiss(boolean type) {
		
		String text = ((EditText) dialogView.findViewById(R.id.notice_profondeur)).getText().toString();
		
		if(type){
			((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_profondeur_realisee_value))
				.setText(text+" *");
		}
		super.dismiss();
	}
}
