package com.example.sample5dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_alert);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Dialog Test");
				builder.setMessage("Alert Dialog...");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Clicked", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "No Clicked", Toast.LENGTH_SHORT).show();						
					}
				});
				
//				builder.setCancelable(false);
				
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						Toast.makeText(MainActivity.this, "Dialog Canceled", Toast.LENGTH_SHORT).show();						
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_list_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("List Dialog");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "selected item : " + items[which], Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_single_choice);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Single choice");
				builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "selected item : " + items[which], Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				builder.create().show();
				
			}
		});
		
		btn = (Button)findViewById(R.id.btn_multiple_choice);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Multiple choice");
				
				final boolean[] mSelected = new boolean[]{false, true, true};
				
				builder.setMultiChoiceItems(items, mSelected, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						mSelected[which] = isChecked;
					}
				});
				
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0 ; i < items.length; i++) {
							if (mSelected[i]) {
								sb.append(items[i] + ",");
							}
						}
						Toast.makeText(MainActivity.this, "selected : " + sb.toString(), Toast.LENGTH_SHORT).show();
					}
				});
				
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_progress);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("progress indeterminate");
				dialog.setMessage("dialog....");
				dialog.show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_progress_bar);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setMax(100);
				dialog.setProgress(50);
				dialog.setSecondaryProgress(30);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("Dialog progress");
				dialog.setMessage("dialog ...");
				dialog.show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_dialog_fragment);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyDialogFragment dialog = new MyDialogFragment();
				dialog.show(getSupportFragmentManager(), "dialog");
			}
		});
		
		btn = (Button)findViewById(R.id.btn_custom_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyCustomDialogFragment dialog = new MyCustomDialogFragment();
				dialog.show(getSupportFragmentManager(), "dialog");
			}
		});
		
		btn = (Button)findViewById(R.id.btn_activity_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, DialogActivity.class);
				startActivity(i);
			}
		});
	}

	final String[] items = new String[] { "item1" , "item2" , "item3"};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
