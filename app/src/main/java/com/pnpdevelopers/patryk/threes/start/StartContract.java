package com.pnpdevelopers.patryk.threes.start;

public interface StartContract {

    interface View{
        void startGame();
        void showTutorial();
        void muteMusic();
        void showScore();
        void showRecord();
    }

    interface Presenter{
        void startGameButtonClick();
        void tutorialButtonClick();
        void muteButtonClick();
    }
}
