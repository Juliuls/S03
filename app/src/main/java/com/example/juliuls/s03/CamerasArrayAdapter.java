package com.example.juliuls.s03;

import android.content.Context;
import android.graphics.Camera;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CamerasArrayAdapter extends ArrayAdapter<Cameras> {

    private List<Cameras> items;
    private Context mContext;

    CamerasArrayAdapter(Context context, List<Cameras> camerasLis ) {
        super( context, 0, camerasLis );
        items = camerasLis;
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View newView = convertView;

        if (newView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            newView = inflater.inflate(R.layout.item_list, parent, false);
        }

        TextView textView = (TextView) newView.findViewById(R.id.cameraName);

        Cameras cameras = items.get(position);

        textView.setText(cameras.getName());

        return newView;
    }


}
