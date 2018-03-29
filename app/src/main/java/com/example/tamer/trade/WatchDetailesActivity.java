package com.example.tamer.trade;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WatchDetailesActivity extends Activity {
    private List<Users> users;
    private ImageView imgPhoto;
    private TextView txtBrand, txtColor, txtSpec, txtSize, txtDesired, txtCondition;
    private FirebaseDatabase db;
    private DatabaseReference watchsRef;
    private FirebaseAuth fba;

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
        imgPhoto = (ImageView)findViewById(R.id.imWatchDetails);
        Picasso.with(this).load(watch.getPhontoUrl()).into(imgPhoto);
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
        db = FirebaseDatabase.getInstance();
        watchsRef = db.getReference("Users");
        fba = FirebaseAuth.getInstance();
        users = new ArrayList<>();

        watchsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users.clear();
                for (DataSnapshot artistSnapShot:dataSnapshot.getChildren()){
                    Users user = artistSnapShot.getValue(Users.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void sendSMS(View view) {

        String phoneNumber = null;
        for(Users user: users)
        {
            if (user.getId().equals(fba.getCurrentUser().getUid()))
            {
                phoneNumber = user.getPhoneNumber();
            }
        }
        // check if user exists in users and get his pone number from DB
        //if (fba.getCurrentUser().getEmail())

        if (phoneNumber != null)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                    + phoneNumber)));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Incorrect phone number!", Toast.LENGTH_SHORT).show();
        }
    }
}
