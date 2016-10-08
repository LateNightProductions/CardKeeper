package com.awscherb.cardkeeper.ui.cards;

import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;
import com.google.zxing.BarcodeFormat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardsPresenterTest {

    @Mock private ScannedCodeService scannedCodeService;
    @Mock private CardsContract.View view;
    private CardsPresenter presenter;
    private List<ScannedCode> codes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Setup codes list
        ScannedCode code = new ScannedCode();
        code.setId(1234L);
        code.setFormat(BarcodeFormat.AZTEC);
        code.setText("1234");
        code.setTitle("My title");

        codes = new ArrayList<>();
        codes.add(code);

        presenter = new CardsPresenter(view, scannedCodeService);
    }

    @Test
    public void test_loadCodes() {
        when(scannedCodeService.listAllScannedCodes()).thenReturn(Observable.just(codes));

        // Load the cards
        presenter.loadCards();

        verify(scannedCodeService).listAllScannedCodes();
        verify(view).showCards(codes);
    }

    @Test
    public void test_addCode() {
        ScannedCode sc = new ScannedCode();
        sc.setId(5678L);

        when(scannedCodeService.addScannedCode(sc)).thenReturn(Observable.just(sc));

        presenter.addNewCard(sc);

        verify(scannedCodeService).addScannedCode(sc);

    }

    @Test
    public void test_deleteCode() {
        ScannedCode sc = new ScannedCode();
        sc.setId(7890L);

        when(scannedCodeService.deleteScannedCode(sc)).thenReturn(Observable.just(null));

        presenter.deleteCard(sc);

        verify(scannedCodeService).deleteScannedCode(sc);
    }

}
