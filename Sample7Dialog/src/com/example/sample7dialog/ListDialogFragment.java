package com.example.sample7dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class ListDialogFragment extends DialogFragment {

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("Single");
		final String[] array = getResources().getStringArray(R.array.items);
		builder.setSingleChoiceItems(array, 2, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getActivity(), "single : " + array[which], Toast.LENGTH_SHORT).show();
				MyDialogFragment dd = new MyDialogFragment();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment f= getFragmentManager().findFragmentByTag("dialog");
				ft.remove(f);
				ft.addToBackStack(null);
				dd.show(ft, "dialog");
//				dialog.dismiss();
			}
		});
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getActivity(), "Yes Click", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getActivity(), "Yes Click", Toast.LENGTH_SHORT).show();
			}
		});
		
		return builder.create();
	}
}
