package com.example.examplecursorloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.FileObserver;
import android.support.v4.content.AsyncTaskLoader;

public class FileListLoader extends AsyncTaskLoader<List<File>> {

	File mDir;
	List<File> mList;
	FileObserver mObserver;
	
	public FileListLoader(Context context, File dir) {
		super(context);
		mDir = dir;
		mObserver = new FileObserver(dir.getAbsolutePath()) {
			
			@Override
			public void onEvent(int event, String path) {
				onContentChanged();
			}
		};
	}

	@Override
	public List<File> loadInBackground() {
		if (mDir.isDirectory()) {
			List<File> list = Arrays.asList(mDir.listFiles());
			mObserver.startWatching();
			return list;
		}
		return null;
	}
	
	@Override
	public void deliverResult(List<File> data) {
		if (isReset()) {
			releaseList(data);
			return;
		}
		List<File> old = mList;
		mList = data;
		if (isStarted()) {
			super.deliverResult(data);
		}
		if (old != null && old != data) {
			releaseList(old);
		}
	}
	
	@Override
	protected void onStartLoading() {
		if (mList != null) {
			deliverResult(mList);
		}
		
		if (takeContentChanged() || mList == null) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}
	
	@Override
	public void onCanceled(List<File> data) {
		super.onCanceled(data);
		releaseList(data);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();		
		if (mList != null) {
			releaseList(mList);
			mList = null;
		}
		mObserver.stopWatching();
	}

	private void releaseList(List<File> list) {
		// ...
	}
		
}
