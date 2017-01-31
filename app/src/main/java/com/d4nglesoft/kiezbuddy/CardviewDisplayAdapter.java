package com.d4nglesoft.kiezbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Chewbacca on 24.01.2017.
 */

public class CardviewDisplayAdapter extends RecyclerView.Adapter<CardviewDisplayAdapter.VoucherViewHolder> {
    private List<VoucherDetailsModel> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class VoucherViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public View view;
        public VoucherDetailsModel currentItem;
        public ImageView cardview_image;
        public TextView cardview_location_name;
        public TextView cardview_deal_description;
        public ImageView cardview_favorite;
        public TextView cardview_distance;

        public VoucherViewHolder(View v) {
            super(v);

            v.setOnCreateContextMenuListener(this);

            view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), VoucherDetailsView.class);
                    intent.putExtra("VoucherId", currentItem.getId());
                    v.getContext().startActivity(intent);
                }
            });
            cardview_image = (ImageView) v.findViewById(R.id.cardview_image);
            cardview_location_name = (TextView) v.findViewById(R.id.cardview_location_name);
            cardview_deal_description = (TextView) v.findViewById(R.id.cardview_deal_description);
            cardview_favorite = (ImageView) v.findViewById(R.id.cardview_favorite);
            cardview_distance = (TextView) v.findViewById(R.id.cardview_distance);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ((Activity) v.getContext()).getMenuInflater().inflate(R.menu.menu_voucher_details, menu);
        }

    }

    public CardviewDisplayAdapter(Context c) {
        DBHelper dbHelper = DBHelper.getInstance(c);
        mDataset = dbHelper.getAllVouchers(); //TODO Filter ? Nach Benefits ?
    }

    @Override
    public VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voucher_listitem, parent, false);

        VoucherViewHolder vh = new VoucherViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VoucherViewHolder holder, final int position) {
        holder.currentItem = mDataset.get(position);

        //TODO Check Valid / Redeemed / Locked

        int mod = position % 7;
        switch (mod) {
            case 1:
                holder.cardview_image.setImageResource(R.drawable.bar1);
                holder.cardview_deal_description.setText("Zwei Bier zum Preis von einem");
                holder.cardview_location_name.setText("Prinzenbar");
                break;
            case 2:
                holder.cardview_image.setImageResource(R.drawable.bar2);
                holder.cardview_deal_description.setText("1 Pizza gratis, beim Kauf von zwei weiteren zum vollen Preis");
                holder.cardview_location_name.setText("Kiezgrill");
                break;
            case 3:
                holder.cardview_image.setImageResource(R.drawable.bar3);
                holder.cardview_deal_description.setText("Besonderer Genuss + 15 Minuten");
                holder.cardview_location_name.setText("Pauli-Eck 69");
                break;
            case 4:
                holder.cardview_image.setImageResource(R.drawable.bar4);
                holder.cardview_deal_description.setText("Große Pommes zum Preis der kleinen - nur Dienstags und Donnerstags");
                holder.cardview_location_name.setText("Kiezcurry - der einzig wahre Grill auf deinem Kiez");
                break;
            case 5:
                holder.cardview_image.setImageResource(R.drawable.bar5);
                holder.cardview_deal_description.setText("Big Mac aus der Tonne");
                holder.cardview_location_name.setText("Burger King");
                break;
            case 6:
                holder.cardview_image.setImageResource(R.drawable.bar6);
                holder.cardview_deal_description.setText("24 Stunden gratis Aufenthalt");
                holder.cardview_location_name.setText("Davidwache");
                break;
            case 0:
                holder.cardview_image.setImageResource(R.drawable.bar7);
                holder.cardview_deal_description.setText("Gratis Rundgang - Ladies Only");
                holder.cardview_location_name.setText("Herbertstraße");
                break;
        }

//        holder.cardview_image.setImageResource(R.drawable.bar2); // TODO
//        holder.cardview_location_name.setText(holder.currentItem.getLocationName());
//        if (position < 20) {
//            for (int i = 0; i < position; i++) {
//                holder.cardview_deal_description.setText(holder.cardview_deal_description.getText() + "Biers ");
//            }
//        }

        holder.cardview_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext().getApplicationContext(), "Changed Favorite...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private String getDisplayDistance(String geoLocation) {
        // TODO
        return geoLocation;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
