package com.sizer.model;


import android.net.Uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScanData {
    private final String pattern = "dd_MM_yyyy_HH_mm_ss";
    private final SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);

    private List<Uri> frames = new ArrayList<>();
    private String scanId;

    public ScanData() {
        this.scanId = generateScanId();
    }

    public List<Uri> getFrames() {
        return frames;
    }

    public void setFrames(List<Uri> frames) {
        this.frames = frames;
    }

    public void addFrame(Uri uri) {
        frames.add(uri);
    }

    public String generateScanId() {
        return df.format(new Date());
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
}
