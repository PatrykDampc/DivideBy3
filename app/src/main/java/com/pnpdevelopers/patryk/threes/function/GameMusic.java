package com.pnpdevelopers.patryk.threes.function;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;

import static com.pnpdevelopers.patryk.threes.function.PreferenceManager.MUSIC_KEY;

public class GameMusic {
    private Context context;
    private MediaPlayer mediaPlayer;
    private PreferenceManager preferenceManager;

    private Random random = new Random();

    public GameMusic(Context context, PreferenceManager preferenceManager) {
        this.context = context;
        this.preferenceManager = preferenceManager;
    }

    public void setUpMusic(int audioFileId, boolean startFromBeginning){
        mediaPlayer = MediaPlayer.create(context, audioFileId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        if(!startFromBeginning) {
            startMusicFromRandom();
        }
        setUpMusicMutedOrNot();

    }

    public void musicMuteSwitch(GameMusicIndicator gameMusicIndicator){
        if(preferenceManager.isMusicOn()) {
            preferenceManager.getEditor().putBoolean(MUSIC_KEY, false);
        } else {
            preferenceManager.getEditor().putBoolean(MUSIC_KEY, true);
        }
        gameMusicIndicator.musicTndicatorSwitch();
        preferenceManager.getEditor().apply();
        setUpMusicMutedOrNot();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void setUpMusicMutedOrNot(){
        if(preferenceManager.isMusicOn()){
            mediaPlayer.setVolume(0.3f,0.3f);
        } else {
            mediaPlayer.setVolume(0,0);
        }
    }

    public void startMusicFromRandom(){
        mediaPlayer.start();
        mediaPlayer.seekTo(random.nextInt(100000));
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
