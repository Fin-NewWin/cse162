package phiennguyen.lab3.activity.services;

import android.app.IntentService;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

import phiennguyen.lab3.activity.utils.Constant;

public class DetectedActivityIntentService extends IntentService {
    protected static final String TAG = DetectedActivityIntentService.class.getSimpleName();


    public DetectedActivityIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent){
        Log.d(TAG, TAG + "onHandIntent()");
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        List<DetectedActivity> detectedActivities = result.getProbableActivities();
        for(DetectedActivity activity : detectedActivities){
            broadcastActivity(activity);
        }
    }
    private void broadcastActivity(DetectedActivity activity){
        Intent intent = new Intent(Constant.BROADCAST_DETECTED_ACTIVITY);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
