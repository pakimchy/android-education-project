package com.example.listviewfiltersample;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.AbsListView.OnScrollListener;

import com.example.listviewfilter.PinnedHeaderAdapter;
import com.example.listviewfilter.ui.PinnedHeaderListView;

public class MainActivity extends ActionBarActivity {

	static final String[] ITEMS = new String[] { "East Timor", "Ecuador",
		"Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
		"Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
		"Finland", "Afghanistan", "Albania", "Algeria", "American Samoa",
		"Andorra", "Angola", "Anguilla", "Antarctica",
		"Antigua and Barbuda", "Argentina", "Armenia", "Aruba",
		"Australia", "Austria", "Azerbaijan", "Bahrain", "Bangladesh",
		"Barbados", "Belarus", "Belgium", "Monaco", "Mongolia",
		"Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
		"Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
		"New Caledonia", "New Zealand", "Guyana", "Haiti",
		"Heard Island and McDonald Islands", "Honduras", "Hong Kong",
		"Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
		"Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
		"Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
		"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
		"Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger",
		"Nigeria", "Niue", "Norfolk Island", "North Korea",
		"Northern Marianas", "Norway", "Oman", "Pakistan", "Palau",
		"Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
		"Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
		"French Southern Territories", "Gabon", "Georgia", "Germany",
		"Ghana", "Gibraltar", "Greece", "Greenland", "Grenada",
		"Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
		"Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico",
		"Micronesia", "Moldova", "Bosnia and Herzegovina", "Botswana",
		"Bouvet Island", "Brazil", "British Indian Ocean Territory",
		"Saint Vincent and the Grenadines", "Samoa", "San Marino",
		"Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone",
		"Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
		"South Africa", "South Georgia and the South Sandwich Islands",
		"South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname",
		"Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
		"Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
		"The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
		"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
		"Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
		"United Arab Emirates", "United Kingdom", "United States",
		"United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
		"Vanuatu", "Vatican City", "Venezuela", "Vietnam",
		"Virgin Islands", "Wallis and Futuna", "Western Sahara",
		"British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso",
		"Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada",
		"Cape Verde", "Cayman Islands", "Central African Republic", "Chad",
		"Chile", "China", "Reunion", "Romania", "Russia", "Rwanda",
		"Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis",
		"Saint Lucia", "Saint Pierre and Miquelon", "Belize", "Benin",
		"Bermuda", "Bhutan", "Bolivia", "Christmas Island",
		"Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
		"Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus",
		"Czech Republic", "Democratic Republic of the Congo", "Denmark",
		"Djibouti", "Dominica", "Dominican Republic",
		"Former Yugoslav Republic of Macedonia", "France", "French Guiana",
		"French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia",
		"Maldives", "Mali", "Malta", "Marshall Islands", "Yemen",
		"Yugoslavia", "Zambia", "Zimbabwe" };

	ArrayList<String> mItems;
	PinnedHeaderListView mListView;
	PinnedHeaderAdapter mAdaptor;
	EditText mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSearchView = (EditText) findViewById(R.id.search_view);
		mListView = (PinnedHeaderListView) findViewById(R.id.list_view);
		mItems = new ArrayList<String>(Arrays.asList(ITEMS));
		mAdaptor = new PinnedHeaderAdapter(this, mItems);
		mListView.setAdapter(mAdaptor);
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (view instanceof PinnedHeaderListView) {
					((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
				}
			}
		});
		mSearchView.addTextChangedListener(filterTextWatcher);		
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			String str = s.toString();
			if (mAdaptor != null && str != null)
				mAdaptor.getFilter().filter(str);
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};
	
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
