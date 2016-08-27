package com.awscherb.cardkeeper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awscherb.cardkeeper.R;
import com.awscherb.cardkeeper.data.CoreData;
import com.awscherb.cardkeeper.model.ScannedCode;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;


public class ScannedCodeAdapter extends BaseAdapter<ScannedCode, ScannedCodeAdapter.ViewHolder> {

    private Context context;
    private BarcodeEncoder encoder;

    public ScannedCodeAdapter(Context context) {
        super(new ArrayList<>());
        this.context = context;
        encoder = new BarcodeEncoder();
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

        try {
            holder.imageView.setImageBitmap(
                    encoder.encodeBitmap(code.getText(), code.getFormat(), 200, 200));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnLongClickListener(v -> {
            CoreData.getScannedCodeService().deleteScannedCode(code).subscribe();
            return true;
        });

    }

    //================================================================================
    // ViewHolder
    //================================================================================

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageView = (ImageView) itemView.findViewById(R.id.adapter_code_image);
        }
    }
}
