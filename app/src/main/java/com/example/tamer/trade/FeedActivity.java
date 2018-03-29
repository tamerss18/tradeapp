 package com.example.tamer.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

 public class FeedActivity extends Activity {

     private FirebaseAuth firebaseAuth;
     private ListView listViewWatches;
     private List<Watch> watchList;
     private DatabaseReference databaseReference;
     private StorageReference storageReference;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_feed);

         init();
     }

     private void init() {
         firebaseAuth = FirebaseAuth.getInstance();
         databaseReference = FirebaseDatabase.getInstance().getReference().child("Watches");
         listViewWatches = (ListView) findViewById(R.id.ListView);
         listViewWatches.setOnItemClickListener(itemClickListener);
         watchList = new ArrayList<>();
         storageReference = FirebaseStorage.getInstance().getReference();
         //BulidList();
     }

     @Override
     protected void onStart() {
         super.onStart();
         BulidList();
     }

     public void BulidList() {
         databaseReference.addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 watchList.clear();
                 for (DataSnapshot SnapShot : dataSnapshot.getChildren()) {
                     Watch watch = SnapShot.getValue(Watch.class);
                     watchList.add(watch);
                 }
                 //Collections.reverse(watchList);
                 WatchList adapter = new WatchList(FeedActivity.this, watchList);
                 listViewWatches.setAdapter(adapter);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
     }

     @Override
     public boolean onCreatePanelMenu(int featureId, Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);
         return super.onCreatePanelMenu(featureId, menu);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if (id == R.id.add_watch) {
             startActivity(new Intent(FeedActivity.this, AddWatchActivity.class));
             return true;
         }
         if (id == R.id.sign_out) {
             firebaseAuth.signOut();
             startActivity(new Intent(FeedActivity.this, LogIn.class));
             return true;
         }
         return super.onOptionsItemSelected(item);
     }

     AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                 Watch watch = watchList.get(i);

                 Intent intent = new Intent(FeedActivity.this, WatchDetailesActivity.class);

                 intent.putExtra("WatchId",watch.getWatchID());
                 intent.putExtra("UserId", watch.getUserId());
                 intent.putExtra("WatchBrand", watch.getWatchBrand());
                 intent.putExtra("Color", watch.getColor());
                 intent.putExtra("WatchSpecs", watch.getWatchSpecs());
                 intent.putExtra("Size", watch.getSize());
                 intent.putExtra("DesiredWatch", watch.getDesiredWatch());
                 intent.putExtra("Condition", watch.getCondition());
                 intent.putExtra("PhontoUrl", watch.getPhontoUrl());

                 startActivity(intent);


                 //Toast.makeText(CustomListView, "" + tempValues.getModel() + "Image:" + tempValues.getImage() + " Url:" + tempValues.getYear(), Toast.LENGTH_LONG).show();

         }
     };



     }

