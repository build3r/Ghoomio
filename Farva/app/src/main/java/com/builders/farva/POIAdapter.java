package com.builders.farva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Dell on 7/26/2015.
 */
public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder>{
        Context c;


    public static class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView poi_image;
            TextView poi_name,poi_description,poi_tags;
            View outer_layout;

            public ViewHolder(View itemView, int viewType)
            {
                super(itemView);
                poi_image=(ImageView)itemView.findViewById(R.id.poi_image);
                poi_description=(TextView)itemView.findViewById(R.id.poi_description);
                poi_name=(TextView)itemView.findViewById(R.id.poi_name);
                poi_tags=(TextView)itemView.findViewById(R.id.poi_tags);
                outer_layout=(RelativeLayout)itemView.findViewById(R.id.outer_layout);

            }
        }

    POIAdapter(Context con)
    {
        c=con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.walk_component_item,parent,false);
        ViewHolder vh1=new ViewHolder(v,viewType);
        return vh1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.poi_name.setText("Item Number"+position);
        holder.poi_description.setText("ha ha ha");
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
