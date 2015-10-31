package com.crowdsourcerers.createskate.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.crowdsourcerers.createskate.R;
import com.crowdsourcerers.createskate.util.SensorManagerUtil;
import com.crowdsourcerers.createskate.util.SoundPlayerUtil;

/**
 * Main Activity handling all the sensing etc right now.
 * Created by batman on 27/10/15.
 */
public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private TextView mStatusText;

    private SensorManagerUtil mSensorManagerUtil;

    private SoundPlayerUtil mSoundPlayerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManagerUtil.registerSensorsAndEventListeners();
    }

    @Override
    protected void onPause() {
        mSoundPlayerUtil.releaseSoundPlayers();
        mSensorManagerUtil.unregisterSensorsAndEventListeners();
        super.onPause();
    }

    /**
     * a method to update the text values based on the latest events.
     * @param textID - the resource ID of the string.
     */
    public void updateText(int textID) {
        mStatusText.setText(textID);
    }

    private void init() {
        //The main text label on the screen that gives visual feedback.
        //linking the object with the TextView in the UI
        mStatusText = (TextView) findViewById(R.id.cs_status_text);

        //Initializing the SoundPlayerUtil
        mSoundPlayerUtil = SoundPlayerUtil.getInstance(this);

        //Initializing the SensorManagerUtil
        mSensorManagerUtil = SensorManagerUtil.getInstance(this);

    }
}
