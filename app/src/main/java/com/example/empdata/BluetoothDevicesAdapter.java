package com.example.empdata;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BluetoothDevicesAdapter extends RecyclerView.Adapter<BluetoothDevicesAdapter.ViewHolder> {
    private final List<BluetoothDevice> deviceList;

    public BluetoothDevicesAdapter(List<BluetoothDevice> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice device = deviceList.get(position);
        try{
            holder.deviceNameTextView.setText(device.getName());
            holder.deviceAddressTextView.setText(device.getAddress());
        }catch (SecurityException e){e.printStackTrace();}

    }

    @Override
    public int getItemCount() {
        return deviceList.size();}

    public void addDevice(BluetoothDevice device) {
        deviceList.add(device);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        TextView deviceAddressTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.device_name_text_view);
            deviceAddressTextView = itemView.findViewById(R.id.device_address_text_view);
        }
    }
}
