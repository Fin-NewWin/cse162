package phiennguyen.lab3.activity.services;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import phiennguyen.lab3.activity.utils.Constant;


public class ActivityDetectionService extends Service {
    private static final String TAG = ActivityDetectionService.class.getSimpleName();

    private PendingIntent mPendingIntent;
    private ActivityRecognitionClient mActivityRecognitionClient;

    public ActivityDetectionService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        Log.d(TAG, "onStartCommand()");
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        Intent mIntentService = new Intent(this, DetectedActivityIntentService.class);

        mPendingIntent = PendingIntent.getService(this,
                1, mIntentService, PendingIntent.FLAG_UPDATE_CURRENT);
        requestActivityUpdatesHandler();
        return START_STICKY;
    }

    public void requestActivityUpdatesHandler(){
        Log.d(TAG, "requestActivityUpdatesHandler()");
        if(mActivityRecognitionClient != null){
            Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
                    Constant.DETECTION_INTERVAL_IN_MILLISECONDS,
                    mPendingIntent);
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void result){
                    Log.d(TAG, "Successfully requested activity updates");
                }
            });
            task.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e){
                    Log.d(TAG, "Successfully requested activity updates");
                }
            });
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        removeActivityUpdatesHandler();
    }

    public void removeActivityUpdatesHandler(){
        if(mActivityRecognitionClient != null){
            Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
                    mPendingIntent);
            task.addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void result){
                    Log.d(TAG, "Removed activity updates successfully!");
                }
            });
            task.addOnFailureListener(new OnFailureListener(){
               @Override
                public void onFailure(@NonNull Exception e){
                   Log.e(TAG, "Failed to remove activity updates!");
               }
            });
        }
    }
}
