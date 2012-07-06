package com.jirbo.v4vctest;

import android.app.*;
import android.content.*;    // Intent
import android.content.pm.ActivityInfo;
import android.os.*;         // Bundle, Environment
import android.view.*;       // MotionEvent
import android.view.View.*;
import android.widget.*;
import android.util.*;

import java.io.*;
import java.util.*;

import com.jirbo.adcolony.*;

public class V4VCTest extends Activity 
  implements AdColonyV4VCListener
{
  final static String APP_ID  = "app4dc1bc42a5529";
  final static String ZONE_ID = "z4dc1fabebecec";

  Properties properties;
  AdColonyVideoAd ad;
  String vc_name = "credits";
  int    total_amount;

  /** Called when the activity is first created. */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);

    // App-specific - load our accumulated total amount of virtual currency.
    loadProperties();

    // Configure ADC once early on before any other ADC calls.
    AdColony.configure( this, "1.0", APP_ID, ZONE_ID );

    // Disable rotation if not on a tablet-sized device (note: not 
    // necessary to use AdColony).
    if ( !AdColony.isTablet() )
    {
      setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
    }

    // Notify this object about confirmed virtual currency.
    AdColony.addV4VCListener( this );

    setContentView( R.layout.main );
    setResultsText();

    Button  video_button = (Button) findViewById(R.id.video_button);

    video_button.setOnClickListener( 
        new OnClickListener() 
        {
          public void onClick( View v )
          {
            // Create a video ad object.
            ad = new AdColonyVideoAd();

            // Debug pop-up showing the number of plays today and the playcap.
            String status = "Plays:"+ad.getV4VCPlays()+"/"+ad.getV4VCPlayCap();
            Toast.makeText( V4VCTest.this, status, Toast.LENGTH_SHORT ).show();

            // "null" below can be an AdColonyVideoListener that implements
            // the onAdColonyVideoFinished() method.  "true" indicates that
            // we want an automatic post video pop-up with the reward amount.
            ad.offerV4VC( null, true );
          }
        });
  }

  // App-specific - load our accumulated total amount of virtual currency.
  void loadProperties()
  {
    properties = new Properties();
    try
    {
      properties.load( openFileInput("vc_info.properties") );
      vc_name = properties.getProperty( "vc_name", "credits" );
      total_amount = Integer.parseInt( properties.getProperty("total_amount","0") );
    }
    catch (Exception err)
    {
      vc_name = "credits";
      total_amount = 0;
    }
  }

  // App-specific - display the confirmed amount of VC.
  void setResultsText()
  {
    TextView results = (TextView) findViewById(R.id.results_view);
    results.setText( "Earn game currency by watching videos!\nTotal amount earned: " 
        + total_amount + " " + vc_name + "." );

    properties.setProperty( "vc_name", vc_name );
    properties.setProperty( "total_amount", ""+total_amount );

    try
    {
      OutputStream outfile = openFileOutput( "vc_info.properties", 0 );
      properties.store( outfile, "vc info" );
      outfile.close();
    }
    catch (Exception err)
    {
    }
  }

  public void onV4VCResult( boolean success, String name, int amount )
  {
    if (success)
    {
      vc_name = name;
      total_amount += amount;
      setResultsText();
    }
  }
}

