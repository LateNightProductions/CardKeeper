package com.awscherb.cardkeeper.model;

import com.google.zxing.BarcodeFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ScannedCode extends RealmObject implements BaseModel {

    @PrimaryKey
    private long id;

    private String text;

    private String format;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BarcodeFormat getFormat() {
        return BarcodeFormat.valueOf(format);
    }

    public void setFormat(BarcodeFormat format) {
        this.format = format.toString();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
