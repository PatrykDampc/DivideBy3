package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.media.MediaPlayer;

import com.pnpdevelopers.patryk.threes.util.MyApplication;

import java.util.Random;

import static com.pnpdevelopers.patryk.threes.function.PreferenceManager.MUSIC_KEY;

public class GameMusic {
    private Context context = MyApplication.getAppContext();
    private MediaPlayer mediaPlayer;
    private PreferenceManager preferenceManager = new PreferenceManager();
    private Random random = new Random();

    public void setUpMusic(int audioFileId, boolean startFromBeginning){
        mediaPlayer = MediaPlayer.create(context, audioFileId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        if(!startFromBeginning) {
            startMusicFromRandom();
        }
        setUpMusicMutedOrNot();
    }

    public void musicMuteSwitch(){
        if(preferenceManager.isMusicOn()) {
            preferenceManager.getEditor().putBoolean(MUSIC_KEY, false);
        } else {
            preferenceManager.getEditor().putBoolean(MUSIC_KEY, true);
        }
        preferenceManager.getEditor().apply();
        setUpMusicMutedOrNot();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void setUpMusicMutedOrNot(){
        if(preferenceManager.isMusicOn()){
            unMuteMusic();
        } else {
            muteMusic();
        }
    }

    public boolean isMusicOn(){
        return preferenceManager.isMusicOn();
    }

    public void startMusicFromRandom(){
        mediaPlayer.start();
        mediaPlayer.seekTo(random.nextInt(100000));
    }

    public void muteMusic(){
        mediaPlayer.setVolume(0,0);
    }

    public void unMuteMusic(){
        mediaPlayer.setVolume(0.3f,0.3f);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
