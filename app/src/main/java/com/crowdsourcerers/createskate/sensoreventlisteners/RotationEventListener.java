package com.crowdsourcerers.createskate.sensoreventlisteners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.crowdsourcerers.createskate.R;
import com.crowdsourcerers.createskate.ui.MainActivity;
import com.crowdsourcerers.createskate.util.SoundPlayerUtil;

/**
 * A class that listens to rotation events around the XYZ axes.
 * Created by batman on 31/10/15.
 */
public class RotationEventListener implements SensorEventListener {
    public static final String TAG = RotationEventListener.class.getSimpleName();
    private static final double THRESHOLD = 165.0;

    private MainActivity mUIContext;
    private SoundPlayerUtil mSoundPlayerUtil;

    private double mLastAzimuth = -1;

    public RotationEventListener(MainActivity activity) {
        mSoundPlayerUtil = SoundPlayerUtil.getInstance(activity);
        mUIContext = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mLastAzimuth == -1) {
            mLastAzimuth = event.values[0];
            return;
        }


        float newAzimuth = event.values[0];
        if (Math.abs(newAzimuth - mLastAzimuth) >= THRESHOLD) {
            mUIContext.updateText(R.string.kickturn_performed);
            mSoundPlayerUtil.playSound(SoundPlayerUtil.KICKTURN);
            Log.d(TAG, "KickTurn Performed");
            mLastAzimuth = -1;
        }
        mLastAzimuth = newAzimuth;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
