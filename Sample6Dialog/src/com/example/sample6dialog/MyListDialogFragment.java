package com.example.sample6dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MyListDialogFragment extends DialogFragment {
	final String[] items = {"item1", "item2" , "item3", "item4", "item5", "item6", "item7"};

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("List Dialog");
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getActivity(), "items : " + items[which], Toast.LENGTH_SHORT).show();
				MyDialogFragment f = new MyDialogFragment();
//				f.show(getFragmentManager(), "dialog1");
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment old = getFragmentManager().findFragmentByTag("dialog");
				if (old != null) {
					ft.remove(old);
				}
				ft.addToBackStack(null);
				f.show(ft, "dialog1");
			}
		});		
		return builder.create();
	}
}
