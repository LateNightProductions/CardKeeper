package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardsPresenterTest {

    @Mock private ScannedCodeService scannedCodeService;
    @Mock private CardsContract.View view;
    private CardsContract.Presenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new CardsPresenter(view, scannedCodeService);
    }

    @Test
    public void test_loadCodes() {
        when(scannedCodeService.listAllScannedCodes()).thenReturn(Observable.just(new ArrayList<>()));

        presenter.loadCards();

        verify(view).showCards(any());
    }

    @Test
    public void test_addCode() {
        when(scannedCodeService.addScannedCode(any(ScannedCode.class))).thenReturn(Observable.just(new ScannedCode()));

        presenter.addNewCard(any(ScannedCode.class));

        verify(view).onCardAdded(any(ScannedCode.class));
    }

    @Test
    public void test_deleteCode() {
        when(scannedCodeService.deleteScannedCode(any(ScannedCode.class))).thenReturn(Observable.just(null));

        presenter.deleteCard(any(ScannedCode.class));

        verify(view).onCardDeleted();
    }

}
