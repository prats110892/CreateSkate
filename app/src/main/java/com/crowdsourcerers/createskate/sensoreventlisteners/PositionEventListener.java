package com.crowdsourcerers.createskate.sensoreventlisteners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.crowdsourcerers.createskate.R;
import com.crowdsourcerers.createskate.ui.MainActivity;
import com.crowdsourcerers.createskate.util.SoundPlayerUtil;

import java.util.LinkedList;

/**
 * A method that handles the position of the object in X,Y,Z plane.
 * Created by batman on 30/10/15.
 */
public class PositionEventListener implements SensorEventListener {
    public static final String TAG = PositionEventListener.class.getSimpleName();
    private static final float THRESHOLD = 6f;

    private MainActivity mUIContext;
    private SoundPlayerUtil mSoundPlayerUtil;

    private LinkedList<Float> mZAccelerations = new LinkedList<>();

    private boolean mRecordingPositions = false;
    private Handler mDelayedExecutor = new Handler();
    private Runnable mDelayedCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRecordingPositions) {
                mRecordingPositions = false;
                Log.d(TAG, "Stopping Recording. Failed Ollie");
            }
        }
    };

    public PositionEventListener(MainActivity activity) {
//        mSoundPlayerUtil = SoundPlayerUtil.getInstance();
        mUIContext = activity;
        mSoundPlayerUtil = SoundPlayerUtil.getInstance(activity);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float zAcceleration = event.values[2];
        if (mRecordingPositions) {
            mZAccelerations.add(zAcceleration);
            Log.d(TAG, "Recording. Z Acceleration: " + zAcceleration);
            if (zAcceleration >= THRESHOLD) {
                Log.d(TAG, "Stopping Recording. Final Z Acceleration: " + zAcceleration);
                mRecordingPositions = false;
                runOllieTask();
            }
            return;
        }

        if (!mRecordingPositions && zAcceleration >= THRESHOLD) {
            mRecordingPositions = true;
            mZAccelerations.clear();
            mZAccelerations.add(zAcceleration);
            Log.d(TAG, "Starting Record. Z Acceleration: " + zAcceleration);
            mDelayedExecutor.postDelayed(mDelayedCheckRunnable, 2000);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void runOllieTask() {
        IsItOllieTask isItOllieTask = new IsItOllieTask();
        isItOllieTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mZAccelerations);
    }

    /**
     * an async task that checks in the background if the trick performed was an ollie or not.
     */
    public class IsItOllieTask extends AsyncTask<LinkedList<Float>, Void, Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Log.d(TAG, "Ollie Performed");
                mUIContext.updateText(R.string.ollie_performed);
                mSoundPlayerUtil.playSound(SoundPlayerUtil.OLLIE);
            }
            mZAccelerations.clear();
        }

        @SafeVarargs
        @Override
        protected final Boolean doInBackground(LinkedList<Float>... params) {
            return checkForOllie(params[0]);
        }

        public Boolean checkForOllie(LinkedList<Float> recordedValues) {
            Log.d(TAG, "Recorded Values: " + recordedValues.toString());

            boolean initialCheck = false, freeFallCheck = false, finalCheck = false;
            initialCheck = (Math.floor(recordedValues.getFirst()) > 0f);
            finalCheck = (Math.floor(recordedValues.getLast()) > 0f);
            for (int i = 1; i <= (recordedValues.size() - 2); i++) {
                if (Math.floor(recordedValues.get(i)) <= 0f) {
                    freeFallCheck = true;
                    break;
                }
            }

            return initialCheck && freeFallCheck && finalCheck;
        }
    }
}
