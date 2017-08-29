package com.awscherb.cardkeeper.ui.card_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BaseFragment;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardDetailFragment extends BaseFragment implements CardDetailContract.View {

    private static final String TAG = CardDetailFragment.class.getName();

    private static final String EXTRA_CARD_ID = TAG + ".extra_card_id";

    @Inject CardDetailContract.Presenter presenter;

    @BindView(R.id.fragment_card_detail_card_view) CardView cardView;
    @BindView(R.id.fragment_card_detail_title) TextView title;
    @BindView(R.id.fragment_card_detail_image) ImageView imageView;

    private BarcodeEncoder encoder;

    //================================================================================
    // New instance
    //================================================================================

    public static BaseFragment newInstance(int cardId) {
        BaseFragment fragment = new CardDetailFragment();
        Bundle b = new Bundle();

        b.putInt(EXTRA_CARD_ID, cardId);

        fragment.setArguments(b);
        return fragment;
    }

    //================================================================================
    // Lifecycle methods
    //================================================================================


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBaseActivity().viewComponent().inject(this);

        encoder = new BarcodeEncoder();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_detail, container, false);
        ButterKnife.bind(this, v);

        presenter.attachView(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadCard(getArguments().getInt(EXTRA_CARD_ID));
    }

    @Override
    public void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    //================================================================================
    // View methods
    //================================================================================

    @Override
    public void showCard(ScannedCode code) {
        // Set title
        title.setText(code.getTitle());

        // Set image scaleType according to barcode type
        switch (code.getFormat()) {
            // Keep QR code scare
            case QR_CODE:
            case AZTEC:
            case DATA_MATRIX:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            // Scale everything else
            default:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        // Load image
        try {
            imageView.setImageBitmap(
                    encoder.encodeBitmap(code.getText(), code.getFormat(), 200, 200));
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }
}
