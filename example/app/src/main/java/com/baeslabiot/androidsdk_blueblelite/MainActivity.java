package com.baeslabiot.androidsdk_blueblelite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.baeslabiot.blueblelite_sdk.blueBLE;
import com.baeslabiot.engin.blueBLEConfig;
import com.baeslabiot.engin.blueBLEList;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity {


    blueBLE  ble;
    listDevices adapter;
    ListView list;
    Button bt_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_scan = (Button) findViewById(R.id.bt_scan);

        ble = new blueBLE();
        ble.blueBLE_init(this);

        adapter = new listDevices(getApplicationContext());
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        ble.setOnScanner(new blueBLE.bleListener() {
            @Override
            public void scanbleListener(ArrayList<blueBLEList> device) {
//                System.out.println("debug ble scan .. " + device.size());
                adapter.setAdapter(device);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void SetOnData(String t) {
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, bleBLUE_Config.class);
                myIntent.putExtra(blueBLEList.ADDRESS_DEVICE, ((blueBLEList) adapter.getItem(position)).getADDRESS());
                myIntent.putExtra(blueBLEList.NAME_DEVICE, ((blueBLEList) adapter.getItem(position)).getNAME());
                myIntent.putExtra(blueBLEList.MAJOR_DEVICE, ((blueBLEList) adapter.getItem(position)).getMAJOR());
                myIntent.putExtra(blueBLEList.MINOR_DEVICE, ((blueBLEList) adapter.getItem(position)).getMINOR());
                myIntent.putExtra(blueBLEList.POWER_DEVICE, ((blueBLEList) adapter.getItem(position)).getPOWER());
                myIntent.putExtra(blueBLEList.RSSI_DEVICE, ((blueBLEList) adapter.getItem(position)).getRSSI());
                myIntent.putExtra(blueBLEList.TIME_DEVICE, ((blueBLEList) adapter.getItem(position)).getTIMESTAMP());
                myIntent.putExtra(blueBLEList.UUID_DEVICE, ((blueBLEList) adapter.getItem(position)).getUUID());
                myIntent.putExtra(blueBLEList.BATTERY_DEVICE, ((blueBLEList) adapter.getItem(position)).getBATTERY());
                MainActivity.this.startActivity(myIntent);
            }
        });
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.scan,
                        Toast.LENGTH_SHORT).show();
                ble.scanBLE(0, MainActivity.this); // Scan Infinity loop;
            }
        });

    }

    @Override
    protected void onResume() {
        ble.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        ble.pause();
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
