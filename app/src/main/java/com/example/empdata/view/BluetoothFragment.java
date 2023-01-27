package com.example.empdata.view;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;

import com.example.empdata.R;

import java.util.ArrayList;


public class BluetoothFragment extends Fragment {
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ListView scannedListView;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());
        scannedListView = view.findViewById(R.id.bluetooth_device_view);
        Switch bluetoothSwitch = view.findViewById(R.id.bluetooth_switch);

        bluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Turn on Bluetooth
                if (checkBluetoothPermission()) {
                    bluetoothAdapter.enable();
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.BLUETOOTH_SCAN}, 0);
                            return;
                        }
                    }
                    bluetoothAdapter.startDiscovery();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    getActivity().getApplicationContext().registerReceiver(bluetoothReceiver, filter);
                    arrayAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,stringArrayList);
                    scannedListView.setAdapter(arrayAdapter);

                }
            }else {
                // Turn off Bluetooth
                bluetoothAdapter.disable();
                requireContext().unregisterReceiver(bluetoothReceiver);
            }
        });

        return view;
    }

    private boolean checkBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),new String[] {Manifest.permission.BLUETOOTH}, BLUETOOTH_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

   private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(checkBluetoothPermission()){
                stringArrayList.add(device.getName());
                Log.d("Taggg",device.getName());}
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };
}

