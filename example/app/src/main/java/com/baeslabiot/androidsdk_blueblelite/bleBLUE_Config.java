package com.baeslabiot.androidsdk_blueblelite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.voice.AlwaysOnHotwordDetector;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baeslabiot.blueblelite_sdk.blueBLE;
import com.baeslabiot.engin.blueBLEConfig;
import com.baeslabiot.engin.blueBLEList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pariwat on 21/07/2015.
 */
public class bleBLUE_Config extends Activity {

    blueBLE ble;
    String ADDRESS;
    String UUID;
    String Name;
    int major,minor,power,rssi;
    String time;
    String Data;

    ListView listService;
    listDetail adap;
    private List<BluetoothGattService> gattServices;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_detail);
        Intent intent = getIntent();
        ADDRESS = intent.getStringExtra(blueBLEList.ADDRESS_DEVICE);
        Name = intent.getStringExtra(blueBLEList.NAME_DEVICE);
        UUID = intent.getStringExtra(blueBLEList.UUID_DEVICE);
        major = intent.getIntExtra(blueBLEList.MAJOR_DEVICE, 0);
        minor = intent.getIntExtra(blueBLEList.MINOR_DEVICE, 0);
        power = intent.getIntExtra(blueBLEList.POWER_DEVICE, 0);
        rssi = intent.getIntExtra(blueBLEList.RSSI_DEVICE, 0);
        time = intent.getStringExtra(blueBLEList.TIME_DEVICE);
        dialog = ProgressDialog.show(this, "",
                "Connecting a device.......", false);
        {
            try{
                Time time = new Time();
                time.set(Long.valueOf(this.time));
                this.time = time.format("%d/%m/%Y %H:%M:%S");
            }catch(Exception e){}
        }

        ((TextView) findViewById(R.id.name)).setText(Name);
        ((TextView) findViewById(R.id.address)).setText(ADDRESS);
        ((TextView) findViewById(R.id.uuid)).setText(UUID);
        ((TextView) findViewById(R.id.time)).setText(time);
        ((TextView) findViewById(R.id.rssi)).setText(String.valueOf(rssi));

        listService = (ListView) findViewById(R.id.list_services);
        adap = new listDetail(this);

        ble = new blueBLE();
        ble.blueBLE_init(this);
        listService.setAdapter(adap);

        System.out.println("debug ble ADDRESS: " + ADDRESS);
        System.out.println("debug ble UUID: " + UUID);

        ((Button) findViewById(R.id.edName)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertBox("DEVICE NAME: ", Name, new complete() {
                    @Override
                    public void OnCompleted(EditText text) {
                        ble.setConf(blueBLEConfig.ble_set_name, text.getText() + "");
                        Name = text.getText() + "";
                        ((TextView) findViewById(R.id.name)).setText(Name);
                        finish();

                    }
                },12);
            }
        });


        ((Button) findViewById(R.id.edUUID)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID = UUID.replace("-","");

                AlertBox("UUID: ", UUID, new complete() {
                    @Override
                    public void OnCompleted(EditText text) {
                        ble.setConf(blueBLEConfig.ble_set_uuid, text.getText() + "");
                        UUID = text.getText() + "";
                        ((TextView) findViewById(R.id.uuid)).setText(UUID);
                        finish();
                    }
                }, 32);
            }
        });

        ((Button) findViewById(R.id.edMajor)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertBox("MAJOR", major + "", new complete() {
                    @Override
                    public void OnCompleted(EditText text) {
                        ble.setConf(blueBLEConfig.ble_set_major, Integer.parseInt(text.getText() + ""));
                        major = Integer.parseInt(text.getText() + "");
                        finish();
                    }
                }, -1);
            }
        });

        ((Button) findViewById(R.id.edMinor)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertBox("MINOR", minor + "", new complete() {
                    @Override
                    public void OnCompleted(EditText text) {
                        ble.setConf(blueBLEConfig.ble_set_minor, Integer.parseInt(text.getText() + ""));
                        minor = Integer.parseInt(text.getText() + "");
                        finish();
                    }
                }, -1);
            }
        });

        ((Button) findViewById(R.id.edDATA)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                AlertBox("DATA", Data + "", new complete() {
                    @Override
                    public void OnCompleted(EditText text) {
                        ble.setConf(blueBLEConfig.ble_set_data, text.getText() + "");
                        Data = text.getText() + "";
                        ((TextView) findViewById(R.id.data)).setText(Data);
                        finish();
                    }
                }, 200);
            }
        });



    }

    public void AlertBox(String textBox,String Data,final complete con,final int size){
        // custom dialog
        final Dialog dialog = new Dialog(bleBLUE_Config.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ((TextView) dialog.findViewById(R.id.text)).setText(textBox);

        final EditText text = (EditText) dialog.findViewById(R.id.editText);
        text.setText(Data);
        if(size>0)
            text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size)});
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TextView) dialog.findViewById(R.id.sizetext)).setText(String.valueOf(s.length())+"/"+size);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        if(size<0)
            ((TextView) dialog.findViewById(R.id.sizetext)).setVisibility(View.GONE);
        else
            ((TextView) dialog.findViewById(R.id.sizetext)).setText(Data.length()+"/"+size);

        Button dialogButton = (Button) dialog.findViewById(R.id.cancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                bleBLUE_Config.this.dialog.show();
                con.OnCompleted(text);
            }
        });

        dialog.show();
    }

    public interface complete{
        public void OnCompleted(EditText text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ble.connect(ADDRESS, new blueBLE.ConLisetener() {
            @Override
            public void Connection(int status, blueBLEList blueBLEList) {
                if (status == 1) {
                    System.out.println("debug ble connected succ; ");
                    gattServices = ble.getGattServices();
                    adap.setData(gattServices);
                    adap.notifyDataSetChanged();
                    ble.getData(new blueBLE.bleListener() {
                        @Override
                        public void scanbleListener(ArrayList<blueBLEList> adapter) {}
                        @Override
                        public void SetOnData(String t) {
                            System.out.println("debug Data: "+t);
                            Data = t;
                            ((TextView) findViewById(R.id.data)).setText(Data);
                            dialog.cancel();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), R.string.Disconnect,
                            Toast.LENGTH_SHORT).show();
                    System.out.println("debug Connect Fail");
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ble.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
