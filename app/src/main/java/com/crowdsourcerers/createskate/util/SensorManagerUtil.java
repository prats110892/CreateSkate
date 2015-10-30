package com.crowdsourcerers.createskate.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.crowdsourcerers.createskate.sensoreventlisteners.AccelerometerEventListener;
import com.crowdsourcerers.createskate.ui.MainActivity;

/**
 * A singleton object that manages all the sensors and their event listeners
 * Created by batman on 30/10/15.
 */
public class SensorManagerUtil {
    private static SensorManagerUtil mSensorManagerUtil;

    private SensorManager mSensorManager;

    private AccelerometerEventListener mAccelerometerEventListener;


    /**
     * method to ge the reference to the SensorMangerUtil singleton object.
     * @param context - the context in which this utility is being used
     * @return - the reference to the SensorMangerUtil singleton.
     */
    public static SensorManagerUtil getInstance(Context context) {
        if (mSensorManagerUtil == null) {
            mSensorManagerUtil = new SensorManagerUtil();
            mSensorManagerUtil.mSensorManager = (SensorManager) context.
                    getSystemService(Context.SENSOR_SERVICE);

            mSensorManagerUtil.mAccelerometerEventListener = new
                    AccelerometerEventListener((MainActivity) context);
        }

        return mSensorManagerUtil;
    }

    /**
     * a method to register all the sensors to their event listeners
     */
    public void registerSensorsAndEventListeners() {
        mSensorManager.registerListener(mAccelerometerEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * a method to unregister all sensors from their event listeners
     */
    public void unregisterSensorsAndEventListeners() {
        mSensorManager.unregisterListener(mAccelerometerEventListener);
    }

    private SensorManagerUtil() {}
}
