package com.pnpdevelopers.patryk.threes.start;

import android.content.Context;

import com.pnpdevelopers.patryk.threes.function.GameMusic;

public class StartPresenter implements StartContract.Presenter{
    StartContract.View mView;
    Context context;


    public StartPresenter(StartContract.View view) {
        this.mView = view;

    }

    ////        Presenter methods       ////
    @Override
    public void startGameButtonClick() {
        mView.startGame();
    }

    @Override
    public void tutorialButtonClick() {
        mView.showTutorial();
    }

    @Override
    public void musicButtonClick() {
        mView.muteUnmuteMusic();
    }

}
