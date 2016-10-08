package com.awscherb.cardkeeper.ui.cards;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.data.model.ScannedCode;
import com.awscherb.cardkeeper.ui.base.BaseAdapter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;


public class CardsAdapter extends BaseAdapter<ScannedCode, CardsAdapter.ViewHolder> {

    private Context context;
    private BarcodeEncoder encoder;
    private CardsContract.Presenter presenter;

    public CardsAdapter(Context context, CardsContract.Presenter presenter) {
        super(new ArrayList<>());
        this.context = context;
        encoder = new BarcodeEncoder();
        this.presenter = presenter;
    }

    //================================================================================
    // Adapter methods
    //================================================================================

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_code, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, ScannedCode code) {

        // Set title
        holder.title.setText(code.getTitle());

        // Set image scaleType according to barcode type
        switch (code.getFormat()) {
            // Keep QR code scare
            case QR_CODE:
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            // Scale everything else
            default:
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        // Load image
        try {
            holder.imageView.setImageBitmap(
                    encoder.encodeBitmap(code.getText(), code.getFormat(), 200, 200));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // Setup delete
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.adapter_scanned_code_delete_message)
                    .setPositiveButton(R.string.action_delete,
                            (dialog, which) -> presenter.deleteCard(code))
                    .setNegativeButton(R.string.action_cancel, null)
                    .show();

            return true;
        });

    }

    //================================================================================
    // ViewHolder
    //================================================================================

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.adapter_code_card_view);
            title = (TextView) itemView.findViewById(R.id.adapter_code_title);
            imageView = (ImageView) itemView.findViewById(R.id.adapter_code_image);
        }
    }
}
