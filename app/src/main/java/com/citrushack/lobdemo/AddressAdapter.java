package com.citrushack.lobdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context mContext;
    private List<Address> mAddresses;

    public AddressAdapter(Context context, List<Address> addresses) {
        mContext = context;
        mAddresses = addresses;
    }

    // Add a list of addresses to the adapter and notify it of the changes
    public void addAll(List<Address> addresses) {
        mAddresses.addAll(addresses);
        notifyItemRangeChanged(mAddresses.size() - addresses.size(), mAddresses.size());
    }

    // Clear all addresses from the adapter and notify it of the changes
    public void clearAll() {
        notifyItemRangeRemoved(0, mAddresses.size());
        mAddresses.clear();
    }

    // Inflate a layout from xml and return the holder
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout
        View addressView = inflater.inflate(R.layout.item_address, parent, false);

        // Return a new viewholder
        ViewHolder viewHolder = new AddressAdapter.ViewHolder(addressView);
        return viewHolder;
    }

    // Populate data to view through holder
    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, int position) {
        Address address = mAddresses.get(position);

        // Set the view holder's elements
        holder.tvDescription.setText(address.getName());
        holder.tvAddress.setText(address.getAddress_line1());
        String cityStateZip = address.getAddress_city() + ", " + address.getAddress_state() + " " + address.getAddress_zip();
        holder.tvCityStateZip.setText(cityStateZip);
    }

    // Get the number of addresses in the adapter
    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_description) TextView tvDescription;
        @BindView(R.id.tv_address_line1) TextView tvAddress;
        @BindView(R.id.tv_city_state_zip) public TextView tvCityStateZip;

        public ViewHolder(View item) {
            // Store item to access context from any viewholder
            super(item);

            // Bind the view elements to the member variables
            ButterKnife.bind(this, item);
        }
    }
}
