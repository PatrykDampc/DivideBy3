package com.pnpdevelopers.patryk.threes.start;

public interface StartContract {

    interface View{
        void startGame();
        void showTutorial();
        void muteUnmuteMusic();
        void showScore();
        void showRecord();
        void mute();
        void unMute();
    }

    interface Presenter{
        void startGameButtonClick();
        void tutorialButtonClick();
        void musicButtonClick();

    }
}
