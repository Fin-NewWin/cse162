package com.phiennnguyen.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gravity;
    private Sensor magnetic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY){
            final DecimalFormat df = new DecimalFormat("0.00");
            float xaccel = sensorEvent.values[0];
            float yaccel = sensorEvent.values[1];
            float zaccel = sensorEvent.values[2];


            TextView xgrav = findViewById(R.id.grav_x);
            TextView ygrav = findViewById(R.id.grav_y);
            TextView zgrav = findViewById(R.id.grav_z);

            xgrav.setText("x: " + df.format(xaccel) + "m/s\u00B2");
            ygrav.setText("y: " + df.format(yaccel) + "m/s\u00B2");
            zgrav.setText("z: " + df.format(zaccel) + "m/s\u00B2");
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float xmag = sensorEvent.values[0];
            float ymag = sensorEvent.values[1];
            float zmag = sensorEvent.values[2];

            TextView mag = findViewById(R.id.mag);

            mag.setText(
                    "x: " + xmag + " uT"
                            + " y: " + ymag + " uT"
                            + " z: " + zmag + " uT");
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        //Do something here if sensor accuracy changes.
    }

    @Override
    protected void onResume(){
        //Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause(){
        //Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}