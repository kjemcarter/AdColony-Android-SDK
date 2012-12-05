package com.jirbo.videointerstitialtest;

import android.app.*;
import android.content.*;    // Intent
import android.content.pm.ActivityInfo;
import android.os.*;         // Bundle, Environment
import android.view.*;       // MotionEvent
import android.view.View.*;
import android.widget.*;
import android.util.*;

import com.jirbo.adcolony.*;

import java.io.*;
import java.util.*;

public class VideoInterstitialTest extends Activity 
  implements AdColonyVideoListener
{
  final static String APP_ID  = "app4dc1bc42a5529";
  final static String ZONE_ID = "z4dc1bc79c5fc9";

  /** Called when the activity is first created. */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);

    // Ad Colony Demo Video
    AdColony.configure( this, "1.0", APP_ID, ZONE_ID );

    // Ads don't play on Gen 1 devices by default because of the danger of crashing
    // the app due to running out of memory.  Uncomment the following line to
    // have your ads run on Gen 1 devices - if they run three times in a row on a
    // Gen 1 device without the app crashing then it will work on all devices.
    //
    // AdColony.enable( true );

    // Disable rotation if not on a tablet-sized device (note: not 
    // necessary to use AdColony).
    if ( !AdColony.isTablet() )
    {
      setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
    }

    setContentView( R.layout.main );

    final Button video_button = (Button) findViewById(R.id.video_button);

    video_button.setOnClickListener( 
        new OnClickListener() 
        {
          public void onClick( View v )
          {
            AdColonyVideoAd ad = new AdColonyVideoAd();
            ad.show( VideoInterstitialTest.this );
          }
        } );
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

  public void onAdColonyVideoStarted()
  {
    // No action.
  }

  public void onAdColonyVideoFinished()
  {
    Toast.makeText( this, "Video Finished", Toast.LENGTH_SHORT ).show();
  }

}


