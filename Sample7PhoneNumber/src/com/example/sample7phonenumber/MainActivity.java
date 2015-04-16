package com.example.sample7phonenumber;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Data;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        String[] from = {Data.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER};
        int[] to = {android.R.id.text1, android.R.id.text2};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, from, to, 0);
        listView.setAdapter(mAdapter);
        
        String[] projection = {Data._ID, Data.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER, CommonDataKinds.Phone.TYPE, Data.CONTACT_ID, Data.LOOKUP_KEY};
        String selection = Data.MIMETYPE + " = ?";
        String[] selectionArgs = {CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        String sortOrder = Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor c = getContentResolver().query(Data.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
        mAdapter.changeCursor(c);
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
