package com.pnpdevelopers.patryk.threes.start;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class StartPresenterTest {

    @Mock
    private StartContract.View mView;

    private StartPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new StartPresenter(mView);
    }

    @Test
    public void startGameButtonClick() {
        mPresenter.startGameButtonClick();
        verify(mView).startGame();
    }

    @Test
    public void tutorialButtonClick() {
        mPresenter.tutorialButtonClick();
        verify(mView).showTutorial();
    }

    @Test
    public void muteButtonClick() {
        mPresenter.muteButtonClick();
        verify(mView).muteMusic();
    }
}