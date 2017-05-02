package com.example.hannah.wemunize;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hannah on 3/16/17.
 */

public class CoverViewAdapter extends BaseAdapter {
    private ArrayList<Images> data;
    private AppCompatActivity activity;
    public CoverViewAdapter( AppCompatActivity context, ArrayList<Images> list){
        this.activity = context;
        this.data = list;
    }
    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public Images getItem(int position){
        return data.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.gameImage.setImageResource(data.get(position).getImage());
        return convertView;
    }
    private static class ViewHolder{
        private TextView gameName;
        private ImageView gameImage;
        public ViewHolder(View v){
            gameImage = (ImageView) v.findViewById(R.id.image);

        }
    }

}
