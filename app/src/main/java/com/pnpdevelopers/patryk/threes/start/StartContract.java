package com.pnpdevelopers.patryk.threes.start;

public interface StartContract {

    interface View{
        void startGame();
        void showTutorial();
        void muteUnmuteMusic();
        void showScore();
        void showRecord();
    }

    interface Presenter{
        void startGameButtonClick();
        void tutorialButtonClick();
        void musicButtonClick();
        void mute();
        void unMute();
    }
}
