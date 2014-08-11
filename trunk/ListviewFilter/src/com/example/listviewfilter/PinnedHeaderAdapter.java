// @author Bhavya Mehta
package com.example.listviewfilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.listviewfilter.ui.IndexBarView;

// Customized adaptor to populate data in PinnedHeaderListView
public class PinnedHeaderAdapter<T extends Comparable<T>> extends BaseAdapter implements IPinnedHeader, Filterable {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SECTION = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SECTION + 1;

	LayoutInflater mLayoutInflater;
	int mCurrentSectionPosition = 0, mNextSectionPostion = 0;

	// array list to store section positions
	ArrayList<Integer> mListSectionPos;

	// array list to store list view data
	ArrayList<String> mListItems;
	
	ArrayList<T> mItems;

	// context object
	Context mContext;

	public PinnedHeaderAdapter(Context context, ArrayList<T> items) {
		this.mContext = context;
		this.mItems = items;
		this.mListItems = new ArrayList<String>();
		this.mListSectionPos = new ArrayList<Integer>();
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		publishResult(items);
	}
	
	public PinnedHeaderAdapter(Context context, ArrayList<String> listItems,ArrayList<Integer> listSectionPos) {
		this.mContext = context;
		this.mListItems = listItems;
		this.mListSectionPos = listSectionPos;

		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mListItems.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return !mListSectionPos.contains(position);
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		return mListSectionPos.contains(position) ? TYPE_SECTION : TYPE_ITEM;
	}

	@Override
	public Object getItem(int position) {
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mListItems.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			int type = getItemViewType(position);

			switch (type) {
			case TYPE_ITEM:
				convertView = mLayoutInflater.inflate(R.layout.row_view, null);
				break;
			case TYPE_SECTION:
				convertView = mLayoutInflater.inflate(R.layout.section_row_view, null);
				break;
			}
			holder.textView = (TextView) convertView.findViewById(R.id.row_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(mListItems.get(position).toString());
		return convertView;
	}

	@Override
	public int getPinnedHeaderState(int position) {
		// hide pinned header when items count is zero OR position is less than
		// zero OR
		// there is already a header in list view
		if (getCount() == 0 || position < 0 || mListSectionPos.indexOf(position) != -1) {
			return PINNED_HEADER_GONE;
		}

		// the header should get pushed up if the top item shown
		// is the last item in a section for a particular letter.
		mCurrentSectionPosition = getCurrentSectionPosition(position);
		mNextSectionPostion = getNextSectionPosition(mCurrentSectionPosition);
		if (mNextSectionPostion != -1 && position == mNextSectionPostion - 1) {
			return PINNED_HEADER_PUSHED_UP;
		}

		return PINNED_HEADER_VISIBLE;
	}

	public int getCurrentSectionPosition(int position) {
		String listChar = mListItems.get(position).toString().substring(0, 1).toUpperCase(Locale.getDefault());
		return mListItems.indexOf(listChar);
	}

	public int getNextSectionPosition(int currentSectionPosition) {
		int index = mListSectionPos.indexOf(currentSectionPosition);
		if ((index + 1) < mListSectionPos.size()) {
			return mListSectionPos.get(index + 1);
		}
		return mListSectionPos.get(index);
	}

	@Override
	public void configurePinnedHeader(View v, int position) {
		// set text in pinned header
		TextView header = (TextView) v;
		mCurrentSectionPosition = getCurrentSectionPosition(position);
		header.setText(mListItems.get(mCurrentSectionPosition).toString());
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ListFilter();
		}
		return mFilter;
	}

	public static class ViewHolder {
		public TextView textView;
	}
	
	@Override
	public View getPinnedHeaderView() {
		View pinnedHeaderView = mLayoutInflater.inflate(R.layout.section_row_view,null);
		return pinnedHeaderView;
	}
	
	@Override
	public IndexBarView getIndexBarView() {
		IndexBarView indexBarView = (IndexBarView) mLayoutInflater.inflate(R.layout.index_bar_view, null);
		indexBarView.setData(mListItems, mListSectionPos);
		return indexBarView;
	}

	@Override
	public View getPreviewView() {
		View previewTextView = mLayoutInflater.inflate(R.layout.preview_view,null);
		return previewTextView;
	}
	
	ListFilter mFilter;
	
	public class ListFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// NOTE: this function is *always* called from a background thread,
			// and
			// not the UI thread.
			String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
			FilterResults result = new FilterResults();

			if (constraint != null && constraint.toString().length() > 0) {
				ArrayList<T> filterItems = new ArrayList<T>();

				synchronized (this) {
					for (int i = 0; i < mItems.size(); i++) {
						T item = mItems.get(i);
						if (item.toString().toLowerCase(Locale.getDefault()).startsWith(constraintStr)) {
							filterItems.add(item);
						}
					}
					result.count = filterItems.size();
					result.values = filterItems;
				}
			} else {
				synchronized (this) {
					result.count = mItems.size();
					result.values = mItems;
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,FilterResults results) {
//			ArrayList<String> filtered = (ArrayList<String>) results.values;
//			setIndexBarViewVisibility(constraint.toString());
			// sort array and extract sections in background Thread
//			new Poplulate().execute(filtered);
			ArrayList<T> items = (ArrayList<T>)results.values;
			publishResult(items);
		}

	}
	
	public void publishResult(ArrayList<T> items) {
		mListItems.clear();
		mListSectionPos.clear();
		if (mItems.size() > 0) {

			// NOT forget to sort array
			Collections.sort(items);

			int i = 0;
			String prev_section = "";
			while (i < items.size()) {
				String current_item = items.get(i).toString();
				String current_section = current_item.substring(0, 1).toUpperCase(Locale.getDefault());

				if (!prev_section.equals(current_section)) {
					mListItems.add(current_section);
					mListItems.add(current_item);
					// array list of section positions
					mListSectionPos.add(mListItems.indexOf(current_section));
					prev_section = current_section;
				} else {
					mListItems.add(current_item);
				}
				i++;
			}
		}
		notifyDataSetChanged();
	}
	
}
