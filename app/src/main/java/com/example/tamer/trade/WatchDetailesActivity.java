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
        watch.WatchID=(getIntent().getStringExtra("WatchId"));
        watch.UserId=(getIntent().getStringExtra("UserId"));
        watch.Color=(getIntent().getStringExtra("Color"));
        watch.WatchSpecs=(getIntent().getStringExtra("WatchSpecs"));
        watch.Size=(getIntent().getStringExtra("Size"));
        watch.DesiredWatch=(getIntent().getStringExtra("DesiredWatch"));
        watch.Condition=(getIntent().getStringExtra("Condition"));
        //watch.PhontoUrl=(getIntent().getStringExtra("PhontoUrl"));
        imgPhoto = (ImageView)findViewById(R.id.imWatchDetails);
        txtBrand = (TextView) findViewById(R.id.txtBrandDet);
        txtColor = (TextView) findViewById(R.id.txtColorDet);
        txtSpec = (TextView) findViewById(R.id.txtWSpecDet);
        txtSize = (TextView) findViewById(R.id.txtSizeDet);
        txtDesired = (TextView) findViewById(R.id.txtDesiredDet);
        txtCondition = (TextView) findViewById(R.id.txtConditionDet);
        txtBrand.setText(watch.WatchBrand);
        txtColor.setText(watch.Color);
        txtSpec.setText(watch.WatchSpecs);
        txtSize.setText(watch.Size);
        txtDesired.setText(watch.DesiredWatch);
        txtCondition.setText(watch.Condition);



    }
}
