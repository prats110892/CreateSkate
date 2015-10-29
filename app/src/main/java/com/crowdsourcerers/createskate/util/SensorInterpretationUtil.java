package com.crowdsourcerers.createskate.util;

import android.hardware.SensorEvent;

/**
 * A class that takes the sensor data and gives output based on the interpretation
 * Created by batman on 28/10/15.
 */
public class SensorInterpretationUtil {
    private static final double THRESHOLD = 0.5;

    public static boolean isMoving(SensorEvent sensorEvent) {
        float mXAcceleration = sensorEvent.values[0];
        float mYAcceleration = sensorEvent.values[1];
        float mZAcceleration = sensorEvent.values[2];

        double totalAcceleration = Math.sqrt((mXAcceleration * mXAcceleration) +
                (mYAcceleration * mYAcceleration) + (mZAcceleration * mZAcceleration));

        return totalAcceleration >= THRESHOLD;
    }
}
