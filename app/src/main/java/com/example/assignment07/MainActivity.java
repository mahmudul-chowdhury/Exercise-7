package com.example.assignment07;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private boolean torchOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void led(View view) {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for(String id: cameraManager.getCameraIdList()){
                CameraCharacteristics cameraChar = cameraManager.getCameraCharacteristics(id);
                if(cameraChar.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
                    if(!torchOn){
                        cameraManager.setTorchMode(id, true);
                    }
                    else{
                        cameraManager.setTorchMode(id, false);
                    }
                    torchOn = !torchOn;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void run(View view) {

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        //update the ui
        TextView sensorValue = findViewById(R.id.showkor);
        sensorValue.setText("X: " + x + " Y: " + y + " Z: " + z);

        ConstraintLayout backgroudlayout = findViewById(R.id.backgroundabc);
        if(z > -0.3 & z < 0.3){
            //if device is on level change the background to green to
            backgroudlayout.setBackgroundColor(Color.GREEN);
        }
        else{
            backgroudlayout.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}