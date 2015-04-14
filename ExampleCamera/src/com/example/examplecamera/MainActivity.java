package com.example.examplecamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;

public class MainActivity extends ActionBarActivity implements
		SurfaceHolder.Callback {

	private static final String TAG = "MainActivity";

	Camera mCamera;
	SurfaceView mPreview;
	Gallery mGallery;
	ImageAdapter mAdapter;
	SurfaceHolder mSurfaceHolder;
	int cameraId;
	int mOrientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPreview = (SurfaceView) findViewById(R.id.surface_preview);
		mPreview.getHolder().addCallback(this);
		mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mGallery = (Gallery) findViewById(R.id.gallery1);
		mAdapter = new ImageAdapter(this);
		mGallery.setAdapter(mAdapter);
		cameraId = CameraInfo.CAMERA_FACING_BACK;
		mCamera = Camera.open(cameraId);
		mOrientation = setCameraDisplayOrientation(this, cameraId, mCamera);

		Button btn = (Button) findViewById(R.id.btn_change);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeCamera();
			}
		});

		btn = (Button) findViewById(R.id.btn_effect);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setColorEffect();
			}
		});

		btn = (Button) findViewById(R.id.btn_capture);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				capture();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	private void changeCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
		cameraId = (cameraId == CameraInfo.CAMERA_FACING_BACK) ? CameraInfo.CAMERA_FACING_FRONT
				: CameraInfo.CAMERA_FACING_BACK;
		mCamera = Camera.open(cameraId);
		mOrientation = setCameraDisplayOrientation(this, cameraId, mCamera);
		if (mSurfaceHolder != null) {
			try {
				mCamera.setPreviewDisplay(mSurfaceHolder);
				mCamera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setColorEffect() {
		Camera.Parameters params = mCamera.getParameters();
		final List<String> effects = params.getSupportedColorEffects();
		String[] items = effects.toArray(new String[effects.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("Select ColorEffect");
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String colorEffect = effects.get(which);
				Camera.Parameters params = mCamera.getParameters();
				params.setColorEffect(colorEffect);
				mCamera.setParameters(params);
			}
		});
		builder.create().show();
	}

	Camera.ShutterCallback shutter = new Camera.ShutterCallback() {

		@Override
		public void onShutter() {
			Log.i(TAG, "Called ShutterCallback");
		}
	};

	Camera.PictureCallback raw = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "Called Raw PictureCallback");
		}
	};

	private static final int THUMBNAIL_WIDTH = 96;

	Camera.PictureCallback jpeg = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "Called JPEG PictureCallback");
			saveJpeg(data);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap,
					THUMBNAIL_WIDTH, THUMBNAIL_WIDTH);
			bitmap.recycle();
			mAdapter.add(thumbnail);
		}
	};

	private void capture() {
		mCamera.takePicture(shutter, raw, jpeg);
	}
	
	private String saveJpeg(byte[] data) {
		File cameraDirectory = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (!cameraDirectory.exists()) {
			cameraDirectory.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		File file = new File(cameraDirectory, "picture_"
				+ sdf.format(new Date()) + ".jpeg");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		addToMediaStore(file);

		return file.getAbsolutePath();
	}

	private void addToMediaStore(File file) {
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, "my picture");
		values.put(Images.Media.DESCRIPTION, "sample camera picture");
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.DATA, file.getAbsolutePath());
		values.put(Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
		values.put(Images.Media.ORIENTATION, mOrientation);
		try {
			ExifInterface exif = new ExifInterface(file.getAbsolutePath());
			int size = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
			if (size > 0) {
				values.put(Images.Media.WIDTH, size);
			}
			size = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
			values.put(Images.Media.HEIGHT, size);
			float[] latLng = new float[2];
			if (exif.getLatLong(latLng)) {
				values.put(Images.Media.LATITUDE, latLng[0]);
				values.put(Images.Media.LONGITUDE, latLng[1]);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = getContentResolver().insert(
				Images.Media.EXTERNAL_CONTENT_URI, values);
		long id = ContentUris.parseId(uri);
		Bitmap bm = Images.Thumbnails.getThumbnail(getContentResolver(), id,
				Images.Thumbnails.MINI_KIND, null);
		storeThumbnail(getContentResolver(), bm, id, 50f, 50f,
				Images.Thumbnails.MICRO_KIND);
		if (uri != null) {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		}
	}

	private static final Bitmap storeThumbnail(ContentResolver cr,
			Bitmap source, long id, float width, float height, int kind) {
		Matrix matrix = new Matrix();

		float scaleX = width / source.getWidth();
		float scaleY = height / source.getHeight();

		matrix.setScale(scaleX, scaleY);

		Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);

		ContentValues values = new ContentValues(4);
		values.put(Images.Thumbnails.KIND, kind);
		values.put(Images.Thumbnails.IMAGE_ID, (int) id);
		values.put(Images.Thumbnails.HEIGHT, thumb.getHeight());
		values.put(Images.Thumbnails.WIDTH, thumb.getWidth());

		Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

		try {
			OutputStream thumbOut = cr.openOutputStream(url);

			thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
			thumbOut.close();
			return thumb;
		} catch (IOException ex) {
			return null;
		}
	}

	public static int setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		
		camera.setDisplayOrientation(result);
		return result;
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

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurfaceHolder = holder;
		if (mCamera != null) {
			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceHolder = null;
		if (mCamera != null) {
			mCamera.stopPreview();
			try {
				mCamera.setPreviewDisplay(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
