package com.abbp.istockmobilesalenew;

//import android.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.DialogFragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class DeviceListFragment extends DialogFragment {

    private Printama.OnConnectPrinter onConnectPrinter;
    private Set<BluetoothDevice> bondedDevices;
    private String mPrinterName;
    private Button saveButton;
    private Button testButton;
    private int inactiveColor;
    private int activeColor;

    public DeviceListFragment() {
        // Required empty public constructor
    }

    public static DeviceListFragment newInstance() {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_list, container, false);
    }

    public void setOnConnectPrinter(Printama.OnConnectPrinter onConnectPrinter) {
        this.onConnectPrinter = onConnectPrinter;
    }


    public void setDeviceList(Set<BluetoothDevice> bondedDevices) {
        this.bondedDevices = bondedDevices;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testButton = view.findViewById(com.anggastudio.printama.R.id.btn_test_printer);
        testButton.setOnClickListener(v -> testPrinter());
        saveButton = view.findViewById(com.anggastudio.printama.R.id.btn_save_printer);
        saveButton.setOnClickListener(v -> savePrinter());
        mPrinterName = Pref.getString(Pref.SAVED_DEVICE);

        RecyclerView rvDeviceList = view.findViewById(com.anggastudio.printama.R.id.rv_device_list);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>(bondedDevices);
        DeviceListAdapter adapter = new DeviceListAdapter(bluetoothDevices, mPrinterName);
        rvDeviceList.setAdapter(adapter);
        adapter.setOnConnectPrinter(printerName -> {
            this.mPrinterName = printerName;
            toggleButtons();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setColor();
        toggleButtons();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setColor() {
        if (getContext() != null) {
            if (this.activeColor == 0) {
                this.activeColor = ContextCompat.getColor(getContext(), com.anggastudio.printama.R.color.colorGreen);
            }
            if (this.inactiveColor == 0) {
                this.inactiveColor = ContextCompat.getColor(getContext(), com.anggastudio.printama.R.color.colorGray5);
            }
        }

    }

    private void testPrinter() {
        Printama.with(getActivity(), mPrinterName).printTest("hello");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void toggleButtons() {
        if (getContext() != null) {
            if (mPrinterName != null) {
                testButton.setBackgroundColor(activeColor);
                saveButton.setBackgroundColor(activeColor);
            } else {
                testButton.setBackgroundColor(inactiveColor);
                saveButton.setBackgroundColor(inactiveColor);
            }
        }
    }

    private void savePrinter() {
        Pref.setString(Pref.SAVED_DEVICE, mPrinterName);
        if (onConnectPrinter != null) {
            onConnectPrinter.onConnectPrinter(mPrinterName);
        }
        dismiss();
    }

    public void setColorTheme(int activeColor, int inactiveColor) {
        if (activeColor != 0) {
            this.activeColor = activeColor;
        }
        if (inactiveColor != 0) {
            this.inactiveColor = inactiveColor;
        }
    }
}
