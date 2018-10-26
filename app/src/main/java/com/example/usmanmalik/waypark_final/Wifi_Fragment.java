package com.example.usmanmalik.waypark_final;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class Wifi_Fragment extends Fragment implements AdapterView.OnItemClickListener {
    private WifiManager wifiManager;
    private ListView listView;
    private Button buttonscan;
    private int size = 0;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.wifi_fragment,container,false);
        context =view.getContext();
        Button button=view.findViewById(R.id.ScanBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            scanWifi();
            }
        });
        listView = view.findViewById(R.id.wifiList);
        listView.setOnItemClickListener(this);
        wifiManager = (WifiManager) container.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(view.getContext(), "Wifi is disabeld, Enabling the Wifi", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        scanWifi();
        return view;

    }
    public void scanWifi() {

        arrayList.clear();
        context.registerReceiver(wifiRecevier, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show();

    }

    BroadcastReceiver wifiRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            for (ScanResult scanResult : results) {
                Log.d("wifi", scanResult.SSID + ".." + scanResult.capabilities);
                arrayList.add(scanResult.SSID );
                adapter.notifyDataSetChanged();
            }
            context.unregisterReceiver(this);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", arrayList.get(position));
        wifiConfig.preSharedKey = String.format("\"%s\"", "gullubutt420");

        WifiManager wifiManager = (WifiManager)context.getSystemService(WIFI_SERVICE);
//remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }
}
