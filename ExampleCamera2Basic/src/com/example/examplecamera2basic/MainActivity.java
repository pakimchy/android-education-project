package com.example.examplecamera2basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.VideoSource;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener {

	TextureView mTextureView;
	SurfaceTexture mSurfaceTexture;
	CameraManager mCM;
	CameraDevice mCamera;
	Gallery gallery;
	ImageAdapter mAdapter;
	MediaRecorder mRecorder;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    int mOrientation;
    boolean isRecording = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gallery = (Gallery)findViewById(R.id.gallery1);
		mAdapter = new ImageAdapter(this);
		gallery.setAdapter(mAdapter);
		
		mTextureView = (TextureView)findViewById(R.id.textureView1);
		mTextureView.setSurfaceTextureListener(this);
		mCM = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
		
		Button btn = (Button)findViewById(R.id.btn_capture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					CaptureRequest.Builder builder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
					builder.addTarget(mImageReader.getSurface());
		            builder.set(CaptureRequest.CONTROL_AF_MODE,
		                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
		            builder.set(CaptureRequest.CONTROL_AE_MODE,
		                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
		            int rotation = getWindowManager().getDefaultDisplay().getRotation();
		            mOrientation = ORIENTATIONS.get(rotation);
		            builder.set(CaptureRequest.JPEG_ORIENTATION, mOrientation);
					
		            CaptureRequest request = builder.build();
					
		            mCaptureSession.capture(request, new CaptureCallback() {
					}, null);
				} catch (CameraAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.btn_record);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isRecording) {
					isRecording = true;
					mRecorder.start();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
	}
	
	private void startRecording() {
		if (!isRecording) {
			isRecording = true;
			mRecorder.start();
		}
	}

	private void stopRecording() {
		if (isRecording) {
			isRecording = false;
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;

			addToMediaStore();

			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(
					mSavedFile.getAbsolutePath(), Thumbnails.MINI_KIND);
			mAdapter.add(thumb);
			
			createCameraPreviewSession();
		}
	}
	
	private void addToMediaStore() {
		ContentValues values = new ContentValues();
		values.put(Video.Media.TITLE, "my video");
		values.put(Video.Media.DESCRIPTION, "test video");
		values.put(Video.Media.DATA, mSavedFile.getAbsolutePath());
		values.put(Video.Media.MIME_TYPE, "video/mpeg");
		values.put(Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);

		Uri uri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI,
				values);
		if (uri != null) {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }
    
    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<Size>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }

        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            return choices[0];
        }
    }

	
	String mCameraId;
	ImageReader mImageReader;
	Size mPreviewSize;
	private static final int THUMBNAIL_WIDTH = 96;
	
	OnImageAvailableListener mAvailableListener = new OnImageAvailableListener() {
		
		@Override
		public void onImageAvailable(ImageReader reader) {
			// Save File...
			Image image = reader.acquireNextImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            
			saveJpeg(data);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap,
					THUMBNAIL_WIDTH, THUMBNAIL_WIDTH);
			bitmap.recycle();
			mAdapter.add(thumbnail);

		}
	};

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
	
    private static Size chooseVideoSize(Size[] choices) {
        for (Size size : choices) {
            if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080) {
                return size;
            }
        }
        return choices[choices.length - 1];
    }

	
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		mSurfaceTexture = surface;
		openCamera(width, height);
	}
	
	private void openCamera(int width, int height) {
		try {
			String[] cameraIds = mCM.getCameraIdList();
			for (String cameraId : cameraIds) {
				CameraCharacteristics cc = mCM.getCameraCharacteristics(cameraId);
				if (cc.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
					mCameraId = cameraId;
	                StreamConfigurationMap map = cc.get(
	                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
	                
	                Size largest = Collections.max(
	                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
	                        new CompareSizesByArea());
	                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
	                        ImageFormat.JPEG, /*maxImages*/2);
					mImageReader.setOnImageAvailableListener(mAvailableListener, null);

	                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
	                        width, height, largest);
	                
					mCM.openCamera(mCameraId, mStateCallback, null);
					break;
				}
			}
			
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
		
		@Override
		public void onOpened(CameraDevice camera) {
			mCamera = camera;
			
			createCameraPreviewSession();
		}
		
		@Override
		public void onError(CameraDevice camera, int error) {
			
		}
		
		@Override
		public void onDisconnected(CameraDevice camera) {
			mCamera = null;
		}
	};
	
	CameraCaptureSession mCaptureSession;
	CaptureRequest.Builder mPreviewRequestBuilder; 
	CaptureRequest mPreviewRequest;
	CaptureRequest mStillRequest;
	
	File mSavedFile;
	private void setupMediaRecorder() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(AudioSource.MIC);
		mRecorder.setVideoSource(VideoSource.SURFACE);
		CamcorderProfile profile = CamcorderProfile
				.get(CamcorderProfile.QUALITY_LOW);
		mRecorder.setProfile(profile);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int orientation = ORIENTATIONS.get(rotation);
        mRecorder.setOrientationHint(orientation);

        File dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		mSavedFile = new File(dir, "my_video_" + System.currentTimeMillis()
				+ ".mpeg");

		mRecorder.setOutputFile(mSavedFile.getAbsolutePath());
        
        try {
			mRecorder.prepare();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private void createCameraPreviewSession() {
		setupMediaRecorder();
		SurfaceTexture texture = mTextureView.getSurfaceTexture();
		texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
		Surface surface = new Surface(texture);
		try {
			mPreviewRequestBuilder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			mPreviewRequestBuilder.addTarget(surface);
			Surface recorderSurface = mRecorder.getSurface();
			mPreviewRequestBuilder.addTarget(recorderSurface);
			mCamera.createCaptureSession(Arrays.asList(surface, recorderSurface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
				
				@Override
				public void onConfigured(CameraCaptureSession session) {
					mCaptureSession = session;
                    mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    mPreviewRequest = mPreviewRequestBuilder.build();
                    
                    try {
						mCaptureSession.setRepeatingRequest(mPreviewRequest, new CameraCaptureSession.CaptureCallback() {
						}, null);
					} catch (CameraAccessException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onConfigureFailed(CameraCaptureSession session) {
					
				}
			}, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		mSurfaceTexture = null;
		if (mCamera != null) {
			mCamera.close();
		}
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		
	}
}
