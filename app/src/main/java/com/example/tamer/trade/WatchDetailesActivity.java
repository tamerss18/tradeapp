package com.example.tamer.trade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WatchDetailesActivity extends AppCompatActivity {
    private ImageView imgPhoto;
    private TextView txtBrand, txtColor, txtSpec, txtSize, txtDesired, txtCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_detailes);

        init();
    }

    private void init() {
        Watch watch = new Watch();
        watch.setWatchID(getIntent().getStringExtra("WatchId"));
        watch.setUserId(getIntent().getStringExtra("UserId"));
        watch.setColor(getIntent().getStringExtra("Color"));
        watch.setWatchSpecs(getIntent().getStringExtra("WatchSpecs"));
        watch.setSize(getIntent().getStringExtra("Size"));
        watch.setDesiredWatch(getIntent().getStringExtra("DesiredWatch"));
        watch.setCondition(getIntent().getStringExtra("Condition"));
        watch.setPhontoUrl(getIntent().getStringExtra("PhontoUrl"));
        imgPhoto = (ImageView)findViewById(R.id.imWatchDetails);
        txtBrand = (TextView) findViewById(R.id.txtBrandDet);
        txtColor = (TextView) findViewById(R.id.txtColorDet);
        txtSpec = (TextView) findViewById(R.id.txtWSpecDet);
        txtSize = (TextView) findViewById(R.id.txtSizeDet);
        txtDesired = (TextView) findViewById(R.id.txtDesiredDet);
        txtCondition = (TextView) findViewById(R.id.txtConditionDet);
        txtBrand.setText(watch.getWatchBrand());
        txtColor.setText(watch.getColor());
        txtSpec.setText(watch.getWatchSpecs());
        txtSize.setText(watch.getSize());
        txtDesired.setText(watch.getDesiredWatch());
        txtCondition.setText(watch.getCondition());



    }
}
