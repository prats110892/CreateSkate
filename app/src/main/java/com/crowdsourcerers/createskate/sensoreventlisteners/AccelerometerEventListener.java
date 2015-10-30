package com.crowdsourcerers.createskate.sensoreventlisteners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.crowdsourcerers.createskate.ui.MainActivity;
import com.crowdsourcerers.createskate.R;
import com.crowdsourcerers.createskate.util.SoundPlayerUtil;

/**
 * An implementation of the SensorEventListener interface that listens to Accelerometers events
 * Created by batman on 30/10/15.
 */
public class AccelerometerEventListener implements SensorEventListener {
    public static final String TAG = AccelerometerEventListener.class.getSimpleName();
    private static final double THRESHOLD = 0.5;

    private SoundPlayerUtil mSoundPlayerUtil;
    private MainActivity mUIContext;

    public AccelerometerEventListener(MainActivity activity) {
        mSoundPlayerUtil = SoundPlayerUtil.getInstance();
        mUIContext = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isMoving(event)) {
            mUIContext.updateText(R.string.moving);
            mSoundPlayerUtil.playSound();
        } else {
            mSoundPlayerUtil.pauseSound();
            mUIContext.updateText(R.string.stopped);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Function that detects if the object is in motion or not
     * @param sensorEvent - the object containing the sensor motion.
     * @return true if object in motion, false otherwise
     */
    public boolean isMoving(SensorEvent sensorEvent) {
        float xAcc = sensorEvent.values[0];
        float yAcc = sensorEvent.values[1];
        float zAcc = sensorEvent.values[2];

        Log.d(TAG, "Acceleration Data: X - " + xAcc + ", Y - " + yAcc + ", Z - " + zAcc);
        double totalAcceleration = Math.sqrt((xAcc * xAcc) + (yAcc * yAcc) + (zAcc * zAcc));
        return totalAcceleration >= THRESHOLD;
    }
}
