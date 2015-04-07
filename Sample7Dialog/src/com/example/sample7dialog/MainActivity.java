package com.example.sample7dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
        Button btn = (Button)findViewById(R.id.btn_alert);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Alert Dialog");
				builder.setMessage("Dialog Test....");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Cancel Click", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						Toast.makeText(MainActivity.this, "Dialog Canceled", Toast.LENGTH_SHORT).show();
					}
				});
//				builder.setCancelable(false);
				
				builder.create().show();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_list);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Dialog List");
				final String[] array = getResources().getStringArray(R.array.items);
				builder.setItems(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "selected : " + array[which], Toast.LENGTH_SHORT).show();
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
				builder.setTitle("Single");
				final String[] array = getResources().getStringArray(R.array.items);
				builder.setSingleChoiceItems(array, 2, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "single : " + array[which], Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
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
				builder.setTitle("Multi");
				final String[] array = getResources().getStringArray(R.array.items);
				final boolean[] selected = {false,true, false, false, true};
				builder.setMultiChoiceItems(array, selected, new OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						selected[which] = isChecked;
					}
				});
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < selected.length; i++) {
							if (selected[i]) {
								sb.append(array[i]).append(",");
							}
						}
						Toast.makeText(MainActivity.this, "Selected : " + sb.toString(), Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(MainActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
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
				dialog.setTitle("Progress...");
				dialog.setMessage("Download....");
				dialog.show();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_progress_horizontal);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("Progress...");
				dialog.setMessage("Download....");
				dialog.setMax(5264);
				dialog.show();
				dialog.setProgress(2352);
			}
		});
        
    }


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
