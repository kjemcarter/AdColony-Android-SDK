package com.jirbo.multizonetest;

import android.app.*;
import android.content.*;    // Intent
import android.os.*;         // Bundle, Environment
import android.view.*;       // MotionEvent
import android.view.View.*;
import android.widget.*;
import android.util.*;

import com.jirbo.adcolony.*;

import java.io.*;
import java.util.*;

public class MultiZoneTest extends Activity 
{
  final static public String APP_ID = "app4dc1bc42a5529";
  final static public String ZONE_1  = "z4dc1bc79c5fc9";
  final static public String ZONE_2  = "z4dc1bd434abc9";
  final static public String ZONE_3  = "z4e24b20e64f57";

  /** Called when the activity is first created. */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);

    AdColony.configure( this, "1.0",
        APP_ID,
        ZONE_1, ZONE_2, ZONE_3
      );

    setContentView( R.layout.main );

    final Button zone_1 = (Button) findViewById(R.id.zone1);
    final Button zone_2 = (Button) findViewById(R.id.zone2);
    final Button zone_3 = (Button) findViewById(R.id.zone3);

    zone_1.setOnClickListener(
        new OnClickListener() 
        {
          public void onClick( View v )
          {
            AdColonyVideoAd ad = new AdColonyVideoAd( ZONE_1 );
            ad.show( null );
          }
        });

    zone_2.setOnClickListener( 
        new OnClickListener() 
        {
          public void onClick( View v )
          {
            AdColonyVideoAd ad = new AdColonyVideoAd( ZONE_2 );
            ad.show( null );
          }
        });

    zone_3.setOnClickListener( 
        new OnClickListener() 
        {
          public void onClick( View v )
          {
            AdColonyVideoAd ad = new AdColonyVideoAd( ZONE_3 );
            ad.show( null );
          }
        });
  }

  public void onPause()
  {
    super.onPause();
    AdColony.pause();  // necessary for correct session length reporting
  }

  public void onResume()
  {
    super.onResume();
    AdColony.resume( this );  // necessary for correct session length reporting
  }

}

