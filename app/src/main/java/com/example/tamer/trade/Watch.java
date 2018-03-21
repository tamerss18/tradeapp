package com.example.tamer.trade;

/**
 * Created by tamer on 3/9/2018.
 */

public class Watch {

    public String WatchID;
    public String UserId;
    public String WatchBrand;
    public String Color;
    public String WatchSpecs;
    public String Size;
    public String DesiredWatch;
    public String Condition;
    public String PhontoUrl;

    public Watch(String watchID, String userId, String watchBrand, String color, String watchSpecs, String size, String desiredWatch, String condition, String phontoUrl) {
        WatchID = watchID;
        UserId = userId;
        WatchBrand = watchBrand;
        Color = color;
        WatchSpecs = watchSpecs;
        Size = size;
        DesiredWatch = desiredWatch;
        Condition = condition;
        PhontoUrl = phontoUrl;
    }

    public Watch(){}

}
