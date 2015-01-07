package com.example.sample6dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
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
		Button btn = (Button) findViewById(R.id.btn_alert);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Dialog Test");
				builder.setMessage("this is dialog test...");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this, "Yes Click",
										Toast.LENGTH_SHORT).show();
							}
						});
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this, "Yes Click",
										Toast.LENGTH_SHORT).show();
							}
						});
				builder.setNeutralButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this,
										"Cancel Click", Toast.LENGTH_SHORT)
										.show();
							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(MainActivity.this, "No Click",
										Toast.LENGTH_SHORT).show();
							}
						});
				builder.setCancelable(false);
				AlertDialog dialog = builder.create();
				dialog.show();

			}
		});
		
		btn = (Button)findViewById(R.id.btn_list);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("List Dialog");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "items : " + items[which], Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_single);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Singe Choice");
				builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "items : " + items[which], Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				builder.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						Toast.makeText(MainActivity.this, "dialog cancel", Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_multi);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("multi dialog");
				final boolean[] selectlist = { true, false, true };
				builder.setMultiChoiceItems(items, selectlist, new OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						selectlist[which] = isChecked;
					}
				});
				
				builder.setPositiveButton("finish", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < selectlist.length; i++) {
							if (selectlist[i]) {
								sb.append(items[i] + ",");
							}
						}
						Toast.makeText(MainActivity.this, "multichoice : " + sb.toString(), Toast.LENGTH_SHORT).show();
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
				dialog.setTitle("progress...");
				dialog.setMessage("app downloading...");
				dialog.show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_progress_bar);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("progress...");
				dialog.setMessage("downloading...");
				dialog.setMax(5000);
				dialog.setProgress(3000);
				dialog.show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_fragment);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyListDialogFragment f = new MyListDialogFragment();
				f.show(getSupportFragmentManager(), "dialog");
			}
		});
		
		btn = (Button)findViewById(R.id.btn_show_custom);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CustomDialogFragment f = new CustomDialogFragment();
				f.show(getSupportFragmentManager(), "dialog");
			}
		});
	}
	final String[] items = {"item1", "item2" , "item3"};

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
