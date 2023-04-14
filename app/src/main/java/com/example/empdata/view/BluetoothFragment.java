package com.example.empdata.view;


import android.Manifest;
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
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.empdata.R;

import java.util.ArrayList;


public class BluetoothFragment extends Fragment {
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ListView scannedListView;
    ArrayList<String> stringArrayList = new ArrayList<>();
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
                        bluetoothAdapter.startDiscovery();

                    }
                    if (bluetoothAdapter.isDiscovering()) {
                        Toast.makeText(requireContext(), "Bluetooth Discovery NOT Started", Toast.LENGTH_LONG).show();

                        // Bluetooth is currently discovering new devices

                    } else {
                        // Bluetooth is not currently discovering new devices
                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        requireContext().registerReceiver(bluetoothReceiver, filter);
                        Toast.makeText(requireContext(), "Bluetooth Discovery Started", Toast.LENGTH_LONG).show();
                    }

                    arrayAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stringArrayList);
                    scannedListView.setAdapter(arrayAdapter);
                }
            } else {

                // Turn off Bluetooth
                bluetoothAdapter.disable();
                requireContext().unregisterReceiver(bluetoothReceiver);

            }
        });
        return view;
    }


    private boolean checkBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.BLUETOOTH}, BLUETOOTH_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(requireContext(), "Bluetooth Receiver is called", Toast.LENGTH_LONG).show();
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (checkBluetoothPermission()) {
                    stringArrayList.add(device.getName());
                    Log.d("Tagger", device.getName());
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };
}

