package com.pnpdevelopers.patryk.threes.start;

public interface StartContract {

    interface View{
        void startGame();
        void showTutorial();
        void muteMusic();
    }

    interface Presenter{
        void startGameButtonClick();
        void tutorialButtonClick();
        void muteButtonClick();

    }
}
