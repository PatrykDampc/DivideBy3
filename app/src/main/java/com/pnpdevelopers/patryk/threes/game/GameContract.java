package com.pnpdevelopers.patryk.threes.game;

public interface GameContract {
    interface View{
        void showNumber();
        void increaseProgress();
        void nextLevel();
        void backToStartActivity();
    }

    interface Presenter{
        void startGame();
        void numberClick();
        void fail();
        void success();
    }
}
