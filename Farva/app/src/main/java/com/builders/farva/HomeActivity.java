package com.builders.farva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String menu_items[]={"Home","Your Walks","About","Contact Us"};
    DrawerLayout drawerLayout;
    RecyclerView menu_recycler_view,path_list_display;
    RecyclerView.Adapter adapter,path_list_adapter;
    RecyclerView.LayoutManager layoutManager,path_list_layout_manager;
    DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        menu_recycler_view=(RecyclerView)findViewById(R.id.menu_recycler_view);
        menu_recycler_view.setHasFixedSize(true);

        adapter=new MenuAdapter(getApplicationContext(),menu_items,"Vishan Seru","Vishanseru9@gmail.com");

        layoutManager=new LinearLayoutManager(this);
        menu_recycler_view.setAdapter(adapter);
        menu_recycler_view.setLayoutManager(layoutManager);
        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_drawer,R.string.close_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                //super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                //super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView,float slideOffset)
            {
                super.onDrawerSlide(drawerView,0);
            }
        };

        drawer.setDrawerListener(drawerToggle);

        drawerToggle.syncState();
        //the parse thing should be added to the splashscreen

        path_list_display=(RecyclerView)findViewById(R.id.path_list_display);
        path_list_display.setHasFixedSize(true);

        path_list_adapter = new PathListAdapter(getApplicationContext());
        path_list_layout_manager=new LinearLayoutManager(this);
        path_list_display.setAdapter(path_list_adapter);
        path_list_display.setLayoutManager(path_list_layout_manager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        SearchView sv = (SearchView)menu.findItem(R.id.custom_search_button).getActionView();
        /*sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return false;
            }
        });
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            }
        });*/
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
                Toast.makeText(this,"Back Pressed",Toast.LENGTH_LONG).show();
                return false;
            case R.id.add_new_path:
                Intent i=new Intent(getApplicationContext(),NewPathActivity.class);
                startActivity(i);
            default:
                return false;
        }


    }
}
