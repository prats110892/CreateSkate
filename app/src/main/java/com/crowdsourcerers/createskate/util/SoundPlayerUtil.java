package com.crowdsourcerers.createskate.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.crowdsourcerers.createskate.R;

/**
 * A singleton object that deals with playing different sounds.
 * Created by batman on 30/10/15.
 */
public class SoundPlayerUtil {
    private static SoundPlayerUtil mSoundPlayerUtil;

    MediaPlayer mBaseTrack, mFirstTrack, mSecondTrack;

    /**
     * a method that returns the reference to the singleton object for SoundPlayer Util
     * @return - the reference to the SoundPlayerUtil singleton object
     */
    public static SoundPlayerUtil getInstance() {
        if (mSoundPlayerUtil == null) {
            mSoundPlayerUtil = new SoundPlayerUtil();
        }

        return mSoundPlayerUtil;
    }

    /**
     * a method to initialize the media players in the SoundPlayerUtil
     * @param context - the context in which the media players will be instantiated
     */
    public void initSoundPlayer(Context context) {
        mBaseTrack = MediaPlayer.create(context, R.raw.from_eden);
        mBaseTrack.setLooping(true);
    }


    /**
     * a method to play the sound. Track opening logic included here
     */
    public void playSound() {
        if (!mBaseTrack.isPlaying()) {
            mBaseTrack.start();
        }
    }


    /**
     * a method to pause the tracks currently playing
     */
    public void pauseSound() {
        if (mBaseTrack.isPlaying()) {
            mBaseTrack.pause();
        }
    }

    /**
     * a way to reset and release all media players
     */
    public void releaseSoundPlayers() {
        mBaseTrack.stop();
        mBaseTrack.release();
    }

    protected SoundPlayerUtil() {}
}
