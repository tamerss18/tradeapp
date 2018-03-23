package com.example.tamer.trade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StreamActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ListView listViewWatches;
    private List<Watch> watchList;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        init();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Watches");
        listViewWatches = (ListView) findViewById(R.id.ListView);
        //listViewWatches.setOnItemClickListener(itemClickListener);
        watchList = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
    }
}
