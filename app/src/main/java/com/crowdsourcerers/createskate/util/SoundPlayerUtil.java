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

    public static final int MOVING = 0x1000;
    public static final int OLLIE = 0x1001;
    public static final int KICKTURN = 0x1002;

    private Context mContext;
    private MediaPlayer mPlayer;

    private int mTricksPerformed = 0;

    /**
     * a method that returns the reference to the singleton object for SoundPlayer Util
     * @return - the reference to the SoundPlayerUtil singleton object
     */
    public static SoundPlayerUtil getInstance(Context context) {
        if (mSoundPlayerUtil == null) {
            mSoundPlayerUtil = new SoundPlayerUtil();
            mSoundPlayerUtil.mContext = context;
        }

        return mSoundPlayerUtil;
    }

    /**
     * a method to play the sound. Track opening logic included here
     * @param mode - Mode for which the play sound was called
     */
    public void playSound(int mode) {
        if (mTricksPerformed == 0) {
            mTricksPerformed = mode == MOVING ? 1 : 0;
        } else {
            if (mode != MOVING) {
                mTricksPerformed++;
            } else {
                return;
            }
        }

        if (mTricksPerformed > 3) {
            mTricksPerformed = 3;
        }

        switch (mTricksPerformed) {
            case 1:
                if (mPlayer != null) {
                    mPlayer.release();
                }
                mPlayer = MediaPlayer.create(mContext, R.raw.base_track);
                mPlayer.setLooping(true);
                mPlayer.start();
                break;
            case 2:
                int seekToPos = mPlayer.getCurrentPosition();
                mPlayer.release();
                mPlayer = MediaPlayer.create(mContext, R.raw.track_1);
                mPlayer.setLooping(true);
                mPlayer.seekTo(seekToPos);
                mPlayer.start();
                break;
            case 3:
                int seekToPos1 = mPlayer.getCurrentPosition();
                mPlayer.release();
                mPlayer = MediaPlayer.create(mContext, R.raw.track_2);
                mPlayer.setLooping(true);
                mPlayer.seekTo(seekToPos1);
                mPlayer.start();
                break;
            default:
                break;
        }
    }


    /**
     * a method to pause the tracks currently playing
     */
    public void pauseSound() {
        if (mPlayer!= null && mPlayer.isPlaying()) {
            mPlayer.pause();
            mTricksPerformed = 0;
        }
    }

    /**
     * a way to reset and release all media players
     */
    public void releaseSoundPlayers() {
        mTricksPerformed = 0;
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    protected SoundPlayerUtil() {}
}
