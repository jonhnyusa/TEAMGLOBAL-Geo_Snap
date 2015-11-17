package edu.ltu.ase.projec2.teamglobal_geo_snap;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import com.redinput.compassview.CompassView;

public class CompassFrameView {
    //CompassView compass = (CompassView) findViewById(R.id.compass);
    //@Overrid
    // e

    Activity activity;
    View v;
    protected View onCreate(Activity activity) {
        this.activity = activity;
        //Typeface rt=Typeface.createFromAsset(getAssets(),"font/Carleton.ttf");
        //setContentView(R.layout.activity_main);

        LayoutInflater li = activity.getLayoutInflater();
        v = li.inflate(R.layout.compassview, null);

        CompassView compass = (CompassView) v.findViewById(R.id.compass);

        compass.setDegrees(57);
        compass.setBackgroundColor(Color.YELLOW);
        compass.setLineColor(Color.RED);
        compass.setShowMarker(false);
        compass.setRangeDegrees(270);

        compass.setOnCompassDragListener(new CompassView.OnCompassDragListener() {
            @Override
            public void onCompassDragListener(float degrees) {
                // Do what you want with the degrees
            }
        });

        return v;
    }

}