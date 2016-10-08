package com.awscherb.cardkeeper.ui.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.dagger.component.DaggerCardsComponent;
import com.awscherb.cardkeeper.dagger.module.CardsPresenterModule;
import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BaseApplication;
import com.awscherb.cardkeeper.ui.base.BaseFragment;
import com.awscherb.cardkeeper.ui.scan.ScanActivity;
import com.awscherb.cardkeeper.ui.scan.ScanFragment;
import com.google.zxing.BarcodeFormat;

import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class CardsFragment extends BaseFragment implements CardsContract.View {

    public static final int REQUEST_GET_CODE = 3;

    @Inject
    CardsContract.Presenter presenter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CardsAdapter scannedCodeAdapter;
    FloatingActionButton fab;

    //================================================================================
    // New Instance
    //================================================================================

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    //================================================================================
    // Lifecycle methods
    //================================================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerCardsComponent.builder()
                .cardsPresenterModule(new CardsPresenterModule(this))
                .servicesComponent(((BaseApplication) getActivity().getApplication()).getServicesComponent())
                .build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cards, container, false);
        if (v == null) {
            return null;
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_cards_recycler);
        fab = (FloatingActionButton) v.findViewById(R.id.fragment_cards_fab);

        layoutManager = new LinearLayoutManager(getActivity());
        scannedCodeAdapter = new CardsAdapter(getActivity(), presenter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scannedCodeAdapter);

        fab.setOnClickListener(v1 -> startActivityForResult(
                new Intent(getActivity(), ScanActivity.class), REQUEST_GET_CODE));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadCards();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GET_CODE && resultCode == RESULT_OK) {
            ScannedCode scannedCode = new ScannedCode();
            scannedCode.setId(System.currentTimeMillis());
            scannedCode.setText(data.getStringExtra(ScanFragment.EXTRA_BARCODE_TEXT));
            scannedCode.setFormat((BarcodeFormat) data.getSerializableExtra(ScanFragment.EXTRA_BARCODE_FORMAT));

            EditText input = new EditText(getActivity());
            input.setHint(R.string.dialog_card_name_hint);
            input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.app_name)
                    .setView(input)
                    .setPositiveButton(R.string.action_add,
                            (dialog, which) -> {
                                scannedCode.setTitle(input.getText().toString());
                                presenter.addNewCard(scannedCode);
                            })
                    .setNegativeButton(R.string.action_cancel,
                            (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    //================================================================================
    // View methods
    //================================================================================

    @Override
    public void showCards(List<ScannedCode> codes) {
        scannedCodeAdapter.swapObjects(codes);
    }

}
