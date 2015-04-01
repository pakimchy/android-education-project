package com.example.exampleaddressbook;

import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exampleaddressbook.AddressBookConstant.AddressTable;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	AddressBookAdapter mAdapter;
	EditText keywordView;
	SimpleCursorAdapter mCursorAdapter;
	private static final boolean useCursor = true;
	
	private int nameColumnIndex = -1;
	private int phoneColumnIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.edit_keyword);

		if (useCursor) {
			String[] from = { AddressTable.NAME, AddressTable.PHONE,
					AddressTable.HOME, AddressTable.OFFICE };
			int[] to = { R.id.text_name, R.id.text_phone, R.id.text_home,
					R.id.text_office };
			mCursorAdapter = new SimpleCursorAdapter(this,
					R.layout.address_item_cursor, null, from, to,
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			mCursorAdapter.setViewBinder(new ViewBinder() {
				
				@Override
				public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
					if (columnIndex == nameColumnIndex) {
						TextView nameView = (TextView)view;
						String name = cursor.getString(columnIndex);
						nameView.setText("name : " + name);
						return true;
					} else if (columnIndex == phoneColumnIndex) {
						TextView phoneView = (TextView)view;
						String phone = cursor.getString(columnIndex);
						phoneView.setText("phone : " + phone);
						return true;
					}
					return false;
				}
			});
			listView.setAdapter(mCursorAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Cursor c = (Cursor)listView.getItemAtPosition(position);
					Address addr = new Address();
					addr.id = id;
					addr.name = c.getString(c.getColumnIndex(AddressTable.NAME));
					addr.phone = c.getString(c.getColumnIndex(AddressTable.PHONE));
					addr.home = c.getString(c.getColumnIndex(AddressTable.HOME));
					addr.office = c.getString(c.getColumnIndex(AddressTable.OFFICE));
					Intent intent = new Intent(MainActivity.this,
							AddressItemActivity.class);
					intent.putExtra(AddressItemActivity.EXTRA_ADDRESS, addr);
					startActivity(intent);					
				}
			});
		} else {
			mAdapter = new AddressBookAdapter();
			listView.setAdapter(mAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Address addr = (Address) listView
							.getItemAtPosition(position);
					Intent intent = new Intent(MainActivity.this,
							AddressItemActivity.class);
					intent.putExtra(AddressItemActivity.EXTRA_ADDRESS, addr);
					startActivity(intent);
				}
			});
		}

		Button btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showList();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		showList();
	}

	private void showList() {
		String keyword = keywordView.getText().toString();
		if (useCursor) {
			Cursor c = AddressDBHelper.getInstance().searchAddressBookCursor(keyword);
			nameColumnIndex = c.getColumnIndex(AddressTable.NAME);
			phoneColumnIndex = c.getColumnIndex(AddressTable.PHONE);
			mCursorAdapter.changeCursor(c);
		} else {
			List<Address> list = AddressDBHelper.getInstance()
					.searchAddressBook(keyword);
			mAdapter.clear();
			mAdapter.addAll(list);
		}
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
		if (id == R.id.action_add) {
			startActivity(new Intent(this, AddressItemActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
