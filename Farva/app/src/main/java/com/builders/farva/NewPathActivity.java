package com.builders.farva;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.builders.farva.utils.LocationEvent;
import com.builders.farva.utils.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/*
Add a forward button on the right of the action bar when there is atleast one of each(origin,destination and interest) marker. The forward
button when clicked will lead you to the next activity. All the various coordinates should also be transferred appropriately to the next
activity via put extra in intents and the zoom out after a marker has been plotted also needs to be implemented.
Then finally add the tutorial overlay explaining the role of the various buttons and then all the work will be over on this activity.
* */
public class NewPathActivity extends AppCompatActivity implements OnMapReadyCallback {

    public int choice,origin_count=0,dest_count=0,poi_count=0;      //choice==1 is origin select choice==2 is destination select choice==3 is interest select
    private Toolbar toolbar,bottom_toolbar;
    Marker origin_marker,destination_marker;
    GoogleMap map;
    Location location;
    double latitude,longitude,origin_latitude,origin_longitude,destination_latitude,destination_longitude;
    Button submit_button;
    ImageView origin_select,destination_select,poi_select;
    ArrayList<String> poi_latitudes = new ArrayList<String>();
    ArrayList<String> poi_longitudes = new ArrayList<String>();
    ImageView forward_button;
    EditText path_name;
    Button start_button;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_path);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        locationManager = LocationManager.getLocationManagerInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        origin_select=(ImageView)findViewById(R.id.origin_select);
        destination_select=(ImageView)findViewById(R.id.destination_select);
        poi_select=(ImageView)findViewById(R.id.poi_select);

        path_name=(EditText)findViewById(R.id.path_title);
        start_button=(Button)findViewById(R.id.start_button);

        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.plot_path_map);
        mapFragment.getMapAsync(this);


    }


    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();

        locationManager.mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        if (locationManager.mGoogleApiClient.isConnected()) {
            locationManager.mGoogleApiClient.disconnect();
        }
    }


    public void onEvent(LocationEvent
                                event){
        Toast.makeText(this, event.mLocation+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_path, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return false;
        }

        }

    @Override
    public void onMapReady(final GoogleMap map) {
        Toast.makeText(this,"Map Ready",Toast.LENGTH_LONG).show();
        map.setMyLocationEnabled(true);
        //for now lets just center the map to say Indiranagar via static coordinates
        //only for the hackathon though and if this fused location api thing doesnyt work
        latitude=12.973879;
        longitude=77.641087;

        LatLng Indiranagar=new LatLng(latitude,longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Indiranagar, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Indiranagar)
                .zoom(17)
                .bearing(90)

                .tilt(40)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.addMarker(new MarkerOptions().position(Indiranagar).title("Your Path Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        /*map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(choice==1)
                {
                    if(origin_count==0)
                    {
                        origin_marker=map.addMarker(new MarkerOptions().position(latLng).title("Your Path Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        //Toast.makeText(getApplicationContext(), "To finalize origin press Submit Button!", Toast.LENGTH_LONG).show();
                        origin_latitude = latLng.latitude;
                        origin_longitude = latLng.longitude;
                        Log.e("Origin Coordinates", origin_latitude + " " + origin_longitude);
                        origin_count=1;
                    }
                    else
                    {
                        origin_marker.remove();
                        origin_marker=map.addMarker(new MarkerOptions().position(latLng).title("Your Path Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        //Toast.makeText(getApplicationContext(), "To finalize origin press Submit Button!", Toast.LENGTH_LONG).show();
                        origin_latitude = latLng.latitude;
                        origin_longitude = latLng.longitude;
                        Log.e("Origin Coordinates", origin_latitude + " " + origin_longitude);
                        origin_count=1;
                    }
                }
                else if(choice==2)
                {
                    if(dest_count==0)
                    {
                        destination_marker=map.addMarker(new MarkerOptions().position(latLng).title("Your Path Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        //Toast.makeText(getApplicationContext(), "To finalize destination press Submit Button!", Toast.LENGTH_LONG).show();
                        destination_latitude = latLng.latitude;
                        destination_longitude = latLng.longitude;
                        Log.e("Destination Coordinates", destination_latitude + " " + destination_longitude);
                        dest_count=2;
                    }
                    else
                    {
                        destination_marker.remove();
                        destination_marker=map.addMarker(new MarkerOptions().position(latLng).title("Your Path Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        //Toast.makeText(getApplicationContext(), "To finalize destination press Submit Button!", Toast.LENGTH_LONG).show();
                        destination_latitude = latLng.latitude;
                        destination_longitude = latLng.longitude;
                        Log.e("Destination Coordinates", destination_latitude + " " + destination_longitude);
                        dest_count=2;
                    }
                }
                else if(choice==3)
                {
                    map.addMarker(new MarkerOptions().position(latLng).title("Point of Interest").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    Toast.makeText(getApplicationContext(),"Add as many points as you like",Toast.LENGTH_LONG).show();
                    Log.e("POI Latitude"," " + latLng.latitude);
                    poi_latitudes.add(String.valueOf(latLng.latitude));
                    poi_longitudes.add(String.valueOf(latLng.longitude));
                    poi_count++;
                    //Log.e("POI Coordinates",poi_latitudes.get(poi_done-1) + " " + poi_longitudes.get(poi_done-1));
                    if(poi_count>0)
                    {
                        forward_button.setVisibility(View.VISIBLE);
                    }
                }
            }
        });*/
    }


    public void start_tour_creation(View v)
    {
        String path_title_written=path_name.getText().toString();
        if(path_title_written==null || path_title_written.length()<4)
        {
            Toast.makeText(getApplicationContext(),"Please Enter a valid Path Name",Toast.LENGTH_LONG).show();
        }
        else
        {

            Location loc=locationManager.getLocation();
            latitude=loc.getLatitude();
            longitude=loc.getLongitude();
            Intent i=new Intent(NewPathActivity.this,WalkTabbedActivity.class);
            i.putExtra("start_latitude",latitude);
            i.putExtra("start_longitude",longitude);
            i.putExtra("path_title",path_title_written);
            startActivity(i);
        }
    }



}
