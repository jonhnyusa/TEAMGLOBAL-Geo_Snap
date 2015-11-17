package edu.ltu.ase.projec2.teamglobal_geo_snap;


import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.util.AttributeSet;

public class GeoCamera extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CompassFrameView Compass = new CompassFrameView(this);

        setContentView(R.layout.main);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        ArDisplayView arDisplay = new ArDisplayView(getApplicationContext(), this);
        arViewPane.addView(arDisplay);

        CompassFrameView arCompass = new CompassFrameView();
        //CompassFrameView arCompass = new CompassFrameView(getApplicationContext());
        arViewPane.addView(arCompass.onCreate(this));


        OverlayView arContent = new OverlayView(getApplicationContext());
        arViewPane.addView(arContent);

    }
}