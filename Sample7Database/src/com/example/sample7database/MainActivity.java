package com.example.sample7database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sample7database.DBConstant.AddressTable;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<ItemData> mAdapter;
	SimpleCursorAdapter mCursorAdapter;
	int nameIndex = -1, emailIndex = -1, phoneIndex = -1, addressIndex = -1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
//        mAdapter = new ArrayAdapter<ItemData>(this, android.R.layout.simple_list_item_1);
//        listView.setAdapter(mAdapter);
        String[] from = {AddressTable.NAME, AddressTable.EMAIL, AddressTable.PHONE, AddressTable.ADDRESS};
        int[] to = {R.id.text_name, R.id.text_email, R.id.text_phone, R.id.text_address};
        mCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_address, null, from, to, 0);
        mCursorAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == nameIndex) {
					TextView nameView = (TextView)view;
					String name = cursor.getString(columnIndex);
					nameView.setText("name : " + name);
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
//				ItemData item = (ItemData)listView.getItemAtPosition(position);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				ItemData item = new ItemData();
				item.id = c.getLong(c.getColumnIndex(AddressTable._ID));
				item.name = c.getString(c.getColumnIndex(AddressTable.NAME));
				item.email = c.getString(c.getColumnIndex(AddressTable.EMAIL));
				item.phone = c.getString(c.getColumnIndex(AddressTable.PHONE));
				item.address = c.getString(c.getColumnIndex(AddressTable.ADDRESS));
				Intent intent = new Intent(MainActivity.this, ContentActivity.class);
				intent.putExtra(ContentActivity.EXTRA_ITEM, item);
				startActivity(intent);
				
			}
		});
        Button btn = (Button)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ContentActivity.class);
				startActivity(intent);
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
//    	mAdapter.clear();
//    	List<ItemData> list = DBManager.getInstance().getList(null);
//    	for (ItemData item : list) {
//    		mAdapter.add(item);
//    	}
    	Cursor c = DBManager.getInstance().getCursor(null);
    	nameIndex = c.getColumnIndex(AddressTable.NAME);
    	emailIndex = c.getColumnIndex(AddressTable.EMAIL);
    	phoneIndex = c.getColumnIndex(AddressTable.PHONE);
    	addressIndex = c.getColumnIndex(AddressTable.ADDRESS);
    	mCursorAdapter.changeCursor(c);
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
