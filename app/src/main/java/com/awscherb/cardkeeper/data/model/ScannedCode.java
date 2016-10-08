package com.awscherb.cardkeeper.data.model;

import com.google.zxing.BarcodeFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ScannedCode extends RealmObject implements BaseModel {

    @PrimaryKey
    private long id;

    private String format;

    private String text;

    private String title;

    //================================================================================
    // Getters
    //================================================================================

    @Override
    public long getId() {
        return id;
    }

    public BarcodeFormat getFormat() {
        return BarcodeFormat.valueOf(format);
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    //================================================================================
    // Setters
    //================================================================================

    public void setId(long id) {
        this.id = id;
    }

    public void setFormat(BarcodeFormat format) {
        this.format = format.toString();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
