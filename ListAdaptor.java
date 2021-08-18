package com.example.midpos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListAdaptor extends ArrayAdapter<Prodect> {
    ArrayList<Prodect> mypro = new ArrayList<>();
    Context context;
    int resource;

    public ListAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Prodect> mypro) {
        super(context, resource, mypro);
        this.context = context;
        this.resource = resource;
        this.mypro = mypro;
    }

    @Override
    public int getCount() {
        return mypro.size();

    }
    /*
    @Override
    public String getItem(int position){

        return mypro.get(position).
    }*/

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView prodect_name = convertView.findViewById(R.id.prodect_name);
        TextView prodect_barcode = convertView.findViewById(R.id.prodect_bacode);
        TextView prodect_quntity = convertView.findViewById(R.id.prodect_quntity);
        TextView prodect_price = convertView.findViewById(R.id.prodect_price);
        TextView prodect_total_price = convertView.findViewById(R.id.total_price_of_prod);
        Prodect current_prodect = getItem(position);
        prodect_name.setText(current_prodect.getProdect_name());
        prodect_barcode.setText(String.valueOf(current_prodect.getProdect_barcode()));
        prodect_quntity.setText(String.valueOf(current_prodect.getProdect_quntity()));
        prodect_price.setText(String.valueOf(current_prodect.getProdect_price()));
        prodect_total_price.setText(String.valueOf(current_prodect.getProdect_photo()));


        return convertView;
    }
}
