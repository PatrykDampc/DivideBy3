package com.pnpdevelopers.patryk.threes.start;

public class StartPresenter implements StartContract.Presenter{
    StartContract.View mView;

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

    @Override
    public void mute() {

    }

    @Override
    public void unMute() {

    }
}
