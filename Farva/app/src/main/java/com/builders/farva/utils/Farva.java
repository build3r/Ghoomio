package com.builders.farva.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by Shabaz on 7/25/2015.
 */
public class Farva extends Application {
   public static Context staticContext;
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        staticContext = this;
        //Parse.initialize(this, "Qc0LJhchCAoVE39ZP5MSld84p1zbJdEZxttEejve", "G4saa4pFdp5IM3masiepbScB3nyepN0VyqdGKMr6");
        Log.e("Being Called", "yes");
    }
}
