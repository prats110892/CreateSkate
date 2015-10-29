package com.crowdsourcerers.createskate;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.crowdsourcerers.createskate.util.SensorInterpretationUtil;

/**
 * Main Activity handling all the sensing etc right now.
 * Created by batman on 27/10/15.
 */
public class MainActivity extends Activity  implements SensorEventListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView mStatusText;
    private Sensor mLinearAccelerator;
    private SensorManager mSensorManager;
    private MediaPlayer mSoundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLinearAccelerator, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        mSoundPlayer.release();
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (SensorInterpretationUtil.isMoving(event)) {
            Log.d(TAG, "Moving");
            mStatusText.setText(R.string.moving);
            playSound();
        } else {
            Log.d(TAG, "Stopped");
            pauseSound();
            mStatusText.setText(R.string.stopped);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void init() {
        //The main text label on the screen that gives visual feedback.
        //linking the object with the TextView in the UI
        mStatusText = (TextView) findViewById(R.id.cs_status_text);

        initSoundPlayer();
        initSensors();
    }

    private void initSoundPlayer() {
        mSoundPlayer = MediaPlayer.create(this, R.raw.from_eden);
        mSoundPlayer.setLooping(true);
    }

    private void initSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLinearAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }


    private void pauseSound() {
        if (mSoundPlayer.isPlaying()) {
            mSoundPlayer.pause();
        }
    }

    private void playSound() {
        if (!mSoundPlayer.isPlaying()) {
            mSoundPlayer.start();
        }
    }
}
