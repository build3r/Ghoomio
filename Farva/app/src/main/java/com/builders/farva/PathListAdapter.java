package com.builders.farva;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Dell on 7/26/2015.
 */
public class PathListAdapter extends RecyclerView.Adapter<PathListAdapter.ViewHolder>
{
    Context c;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView path_image;
        TextView path_time,path_length,path_tags,path_name;
        View outer_layout;

        public ViewHolder(View itemView, int ViewType)
        {
            super(itemView);
            path_image=(ImageView)itemView.findViewById(R.id.path_image);
            path_time=(TextView)itemView.findViewById(R.id.path_time);
            path_name=(TextView)itemView.findViewById(R.id.path_name);
            path_length=(TextView)itemView.findViewById(R.id.path_length);
            path_tags=(TextView)itemView.findViewById(R.id.path_tags);
            outer_layout=(RelativeLayout)itemView.findViewById(R.id.outer_layout);
        }
    }

    PathListAdapter(Context con)
    {
        c=con;
    }

    public PathListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_component,parent,false);
        ViewHolder vh1=new ViewHolder(v,viewType);
        return vh1;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.path_name.setText("Path Number"+ String.valueOf(position));
        holder.outer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,"Item " + position + "Clicked",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
