package com.example.tamer.trade;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by tamer on 3/11/2018.
 */

public class WatchList extends ArrayAdapter<Watch> {
    private Activity context;
    private List<Watch> watchList;
    private FirebaseAuth auth;
    public WatchList(Activity context ,List<Watch> watchList){
        super(context, R.layout.one_watch, watchList);
        this.context = context;
        this.watchList = watchList;
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public View getView(int possition, @NonNull View convertView, @NonNull ViewGroup Parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.one_watch,null);
        ImageView imageView = view.findViewById(R.id.imageViewWatch);
        TextView textView = view.findViewById(R.id.textViewWatchName);
        Button button = view.findViewById(R.id.buttonDetails);
        Watch watch = watchList.get(possition);
        //TODO set Pic
        textView.setText(watch.WatchBrand);
        button.setTag(watch.WatchID);
        return view;
    }
}
