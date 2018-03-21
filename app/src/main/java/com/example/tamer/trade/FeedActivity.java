 package com.example.tamer.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
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
         watchList.clear();
         databaseReference.addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
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

                 Intent intent = new Intent(FeedActivity.this, TempActivity.class);
                 /*
                 intent.putExtra("WatchId",watch.WatchID);
                 intent.putExtra("UserId", watch.UserId);
                 intent.putExtra("WatchBrand", watch.WatchBrand);
                 intent.putExtra("Color", watch.Color);
                 intent.putExtra("WatchSpecs", watch.WatchSpecs);
                 intent.putExtra("Size", watch.Size);
                 intent.putExtra("DesiredWatch", watch.DesiredWatch);
                 intent.putExtra("Condition", watch.Condition); */
                 //intent.putExtra("PhontoUrl", watch.PhontoUrl);

                 startActivity(intent);


                 //Toast.makeText(CustomListView, "" + tempValues.getModel() + "Image:" + tempValues.getImage() + " Url:" + tempValues.getYear(), Toast.LENGTH_LONG).show();

         }
     };

     public void onClickWatchItemButton(View view) {
     }

     }

