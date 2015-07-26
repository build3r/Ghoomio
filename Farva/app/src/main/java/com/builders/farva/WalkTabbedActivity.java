package com.builders.farva;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.melnykov.fab.FloatingActionButton;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;

import java.util.Locale;


public class WalkTabbedActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Toolbar toolbar;
    static Double start_latitude,start_longitude;
    FloatingActionButton fab;
    String path_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_tabbed);

        Intent intent=getIntent();
        start_latitude=intent.getDoubleExtra("start_latitude",0.0);
        start_longitude=intent.getDoubleExtra("start_longitude",0.0);
        path_title=intent.getStringExtra("path_title");
        fab=(FloatingActionButton)findViewById(R.id.fab);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        setTitle(path_title);

        //toolbar=(Toolbar)findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        ParseObject story=new ParseObject("Story");
        story.put("ownerId","1");
        story.put("storyName",path_title);

        ParseObject path=new ParseObject("path");


    }


    public void create_poi(View v)
    {
        //Toast.makeText(getApplicationContext(),"Yaaay",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1888);        //1888 IS FOR CAMERA REQUEST
    }

    public void onActivityResult(int requestcode,int resultcode, Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode == 1888 && resultcode == RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");

            Intent i = new Intent(getApplicationContext(), AddDataToInterest.class);
            i.putExtra("poi_image", bp);
            startActivity(i);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_walk_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position)
            {
                case 0: return PlaceholderFragment.newInstance(position);
                case 1: return RecyclerViewFragment.newInstance(position);
                default: return PlaceholderFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                  }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnMapReadyCallback {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.map_fragment_tabbed, container, false);
            MapFragment mapFragment=(MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(this);
            return rootView;
        }

        @Override
        public void onMapReady(GoogleMap map)
        {
            map.setMyLocationEnabled(true);
            LatLng point=new LatLng(start_latitude,start_longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(point)
                    .zoom(17)
                    .bearing(90)
                    .tilt(40)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            map.addMarker(new MarkerOptions().position(point).title("Your Path Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        }
    }

    public static class RecyclerViewFragment extends Fragment
    {
        RecyclerView recycler_view;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;

        public static RecyclerViewFragment newInstance(int sectionNumber) {
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            Bundle args = new Bundle();
            args.putInt("section_number", sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public RecyclerViewFragment()
        {

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.recycler_fragment_tabbed,container,false);
            recycler_view = (RecyclerView)rootView.findViewById(R.id.poi_recycler_view);
            layoutManager =  new LinearLayoutManager(getActivity());

            adapter= new POIAdapter(getActivity());
            recycler_view.setAdapter(adapter);
            recycler_view.setLayoutManager(layoutManager);
            return rootView;
        }
    }

}
