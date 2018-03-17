 package com.example.tamer.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

     private  FirebaseAuth firebaseAuth;
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
         listViewWatches = (ListView)findViewById(R.id.ListView);
         watchList = new ArrayList<>();
         storageReference = FirebaseStorage.getInstance().getReference();


         BulidList();
     }

     public void BulidList(){
        watchList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot SnapShot:dataSnapshot.getChildren() ){
                    Watch watch = SnapShot.getValue(Watch.class);
                    watchList.add(watch);
                }
                Collections.reverse(watchList);
                WatchList adapter= new WatchList(FeedActivity.this,watchList);
                listViewWatches.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

     @Override
     public boolean onCreatePanelMenu(int featureId, Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main,menu);
         return super.onCreatePanelMenu(featureId, menu);
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id=item.getItemId();
         if (id == R.id.add_watch){
             startActivity(new Intent(FeedActivity.this,AddWatchActivity.class));
             return true;
         }
         if (id == R.id.sign_out){
             firebaseAuth.signOut();
             startActivity(new Intent(FeedActivity.this,LogIn.class));
             return true;
         }
         return super.onOptionsItemSelected(item);
     }

     public void onItemClick(int mPosition)
     {/*
         ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);
         Car car = db.getAllCars().get(mPosition);

         Intent i = new Intent(this, CarDetailesActivity.class);
         i.putExtra("company", car.getCompany());
         i.putExtra("model", car.getModel());
         i.putExtra("year", car.getYear());
         i.putExtra("engine", car.getEngine());
         i.putExtra("horsePower", car.getHorsePower());
         i.putExtra("condition", car.getCondition());
         i.putExtra("phone", car.getPhone());
         startActivity(i);

*/



         //Toast.makeText(CustomListView, "" + tempValues.getModel() + "Image:" + tempValues.getImage() + " Url:" + tempValues.getYear(), Toast.LENGTH_LONG).show();
     }
 }
