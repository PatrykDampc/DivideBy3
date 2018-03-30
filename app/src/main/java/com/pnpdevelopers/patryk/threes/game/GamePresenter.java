package com.pnpdevelopers.patryk.threes.game;

public class GamePresenter implements GameContract.Presenter {
    GameContract.View mView;

    public GamePresenter(GameContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startGame() {
        // cala petla tutaj
        mView.showNumber();
    }

    @Override
    public void numberClick() {
        //if
        success();
        fail();
    }

    @Override
    public void numberSwipe(){

    }

    @Override
    public void fail() {
        mView.backToStartActivity();
    }

    @Override
    public void success() {
        mView.increaseProgress();
        //check for next level;
        mView.nextLevel();
    }
}
