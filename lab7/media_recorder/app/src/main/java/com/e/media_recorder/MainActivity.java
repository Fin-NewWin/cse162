package com.e.media_recorder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.e.media_recorder.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private TextureView mPreview;
    private MediaRecorder mMediaRecorder;
    private File mOutputFile;

    private boolean isRecording = false;
    private static final String TAG = "Recorder";
    private Button captureButton;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreview = findViewById(R.id.surface_view);
        captureButton = findViewById(R.id.button_capture);
        editText = findViewById(R.id.video_name);

        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.RECORD_AUDIO",
                "android.permission.CAMERA"};

        int permsRequestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }
    }

    public void onCaptureClick (View view) {
        if (isRecording){
            // BEGIN_INCLUDE(stop_release_media_recorder)
            mMediaRecorder.stop();
            // stop recording and release camera
            //releaseMediaRecorder(); //release the MediaRecorder object
            mMediaRecorder.release();
            //mCamera.lock(); //take camera access back from MediaRecorder

            //inform the user that recording has stopped
            setCaptureButtonText("START");
            isRecording = false;
            releaseCamera();

        } else {

            if (prepareVideoRecorder()) {
                //Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();
                setCaptureButtonText("STOP");
                isRecording = true;
            } else {
                //prepare didn't work, release the camera
                releaseMediaRecorder();
            }
            //END_INCLUDE(prepare_start_media_recorder)
        }
    }

    private void setCaptureButtonText(String title){
        captureButton.setText(title);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //if we are using MediaRecorder, release it first
        releaseMediaRecorder();
        //release the camera immediately on pause event
        releaseCamera();
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null){
            mMediaRecorder.release();
            //clear recorder configuration
            mMediaRecorder.reset();
            //release the recorder object

            mMediaRecorder = null;
            //Lock camera for later use i.e taking it back from MediaRecorder
            //MediaRecorder doesn't need it anymore and we will release it if the activity pauses
            mCamera.lock();
        }
    }

    private void releaseCamera(){
        if (mCamera != null) {
            //release the camera for other applications
            mCamera.release();
            mCamera = null;
        }
    }

    private boolean prepareVideoRecorder(){
        // BEGIN_INCLUDE (configure_preview)
        // mCamera = CameraHelper.getDefaultCameraInstance();
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();

        mCamera.setParameters(parameters);
        try{
            mCamera.setPreviewTexture(mPreview.getSurfaceTexture());
        } catch (IOException e) {
            Log.e(TAG, "Surface texture is unavailable or unsuitable" + e.getMessage());
            return false;
        }

        mMediaRecorder = new MediaRecorder();

        //Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        //Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //Use the same size for recording profile
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        mMediaRecorder.setMaxDuration(60000); //60 second limitation
        //mMediaRecorder.setMaxFileSize(5000000); //Approx 5 MB

        //Step 3: Set a CamcorderProfile (requires API level 8 or higher)
        mMediaRecorder.setProfile(profile);

        //Step 4: Set output file
        mOutputFile = getOutputMediaFile();
        if (mOutputFile == null) {
            return false;
        }

        mMediaRecorder.setOutputFile(mOutputFile.getAbsolutePath());
        // END_INCLUDE (configure_media_recorder)

        //Step 5: Prepare configured MediaRecorder
        try{
            mMediaRecorder.prepare();
        } catch (IllegalStateException e){
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e){
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    public File getOutputMediaFile(){
        if(!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss", Locale.US).format(new Date());
        String videoName = editText.getText().toString();
        String fileName = videoName + "_" + timeStamp + ".mp4";

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), "Lab3_recordings"
        );mMediaRecorder.release();

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs())  {
                Log.d("Lab3_Recordings", "failed to create directory");
                return null;
            }
        }

        return new File(mediaStorageDir, fileName);
    }

}