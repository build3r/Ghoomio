package com.builders.farva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dell on 7/6/2015.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    String menu_titles[];
    String name_this;
    String email_this;
    Context con;
    //header is 0 and menu item is 1
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        int Holderid;
        TextView name;
        TextView email;
        TextView menu_item;

        public ViewHolder(View itemView,int ViewType)
        {
            super(itemView);

            if(ViewType==1)
            {
                menu_item=(TextView)itemView.findViewById(R.id.row_item_name);
                Holderid=1;
            }
            else
            {
                name=(TextView)itemView.findViewById(R.id.name);
                email=(TextView)itemView.findViewById(R.id.email);
                Holderid=0;
            }
        }
    }

    MenuAdapter(Context c,String menu_items[],String name,String email)
    {
        con=c;
        menu_titles=menu_items;
        name_this=name;
        email_this=email;
    }
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_row,parent,false);
            ViewHolder vh1=new ViewHolder(v,viewType);
            return vh1;
        }
        else if(viewType==0)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vh0=new ViewHolder(v,viewType);
            return vh0;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MenuAdapter.ViewHolder holder, int position) {
        if(holder.Holderid==1)
        {
            holder.menu_item.setText(menu_titles[position - 1]);
            holder.menu_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(con,holder.menu_item.getText(),Toast.LENGTH_LONG).show();
                    Log.e("Item", holder.menu_item.getText().toString());
                    holder.menu_item.setSelected(true);
                }
            });


        }
        else
        {
            holder.name.setText(name_this);
            holder.email.setText(email_this);
        }
    }

    @Override
    public int getItemCount() {
        return menu_titles.length + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(isPositionHeader(position))
        {
            return 0;
        }

        return 1;
    }

    private boolean isPositionHeader(int position)
    {
        return position==0;
    }
}
