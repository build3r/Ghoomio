package com.builders.farva;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


public class AddDataToInterest extends ActionBarActivity {

    ImageView poi_image;
    EditText poi_name,poi_description;
    Bitmap poi_image_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_to_interest);
        //ActionBar actionbar=getActionBar();
        //actionbar.setBackgroundDrawable(new ColorDrawable(R.color.ColorPrimaryLight));
        //actionbar.setTitle(Html.fromHtml("<font color=\"white\">" + getString(R.string.title_activity_add_data_to_interest) + "</font>"));
        final android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        poi_image=(ImageView)findViewById(R.id.poi_image);
        poi_name=(EditText)findViewById(R.id.enter_poi_name);
        poi_description=(EditText)findViewById(R.id.enter_poi_description);
        Intent intent=getIntent();
        poi_image_bitmap=(Bitmap)intent.getParcelableExtra("poi_image");
        if(poi_image!=null)
        {
            Toast.makeText(getApplicationContext(),"Image Retrieved",Toast.LENGTH_LONG).show();
        }
        poi_image.setImageBitmap(poi_image_bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_data_to_interest, menu);
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

    public void submit_data_online(View v)
    {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        String poi_name_string=poi_name.getText().toString();
        String poi_description_string=poi_description.getText().toString();
        poi_image_bitmap.compress(Bitmap.CompressFormat.PNG,50,out);
        byte[] image=out.toByteArray();
        ParseFile file=new ParseFile("image1.png",image);
        file.saveInBackground();

        ParseObject imgupload = new ParseObject("ImageUpload");
        // Create a column named "ImageName" and set the string
        imgupload.put("ImageName", "Interest_image");
        // Create a column named "ImageFile" and insert the image
        imgupload.put("ImageFile", file);
        // Create the class and the columns
        imgupload.saveInBackground();
        // Show a simple toast message
        Toast.makeText(getApplicationContext(), "Image Uploaded",Toast.LENGTH_SHORT).show();
        finish();
    }
}
