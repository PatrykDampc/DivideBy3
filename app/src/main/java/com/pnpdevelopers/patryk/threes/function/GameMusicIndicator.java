package com.pnpdevelopers.patryk.threes.function;


import android.widget.ImageView;

import com.pnpdevelopers.patryk.threes.R;


public class GameMusicIndicator {

    private PreferenceManager preferenceManager;
    private ImageView musicView;

    public GameMusicIndicator(PreferenceManager preferenceManager, ImageView musicView) {
        this.musicView = musicView;
        this.preferenceManager = preferenceManager;
    }

    public void setUpMusicIndicator(){
        if(preferenceManager.isMusicOn()){
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
        } else {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
        }

    }

    public void musicTndicatorSwitch(){
        if(preferenceManager.isMusicOn()) {
            musicView.setImageResource(R.drawable.ic_music_note_off_white_36dp);
        } else {
            musicView.setImageResource(R.drawable.ic_music_note_white_36dp);
        }
        preferenceManager.getEditor().apply();
    }

}
