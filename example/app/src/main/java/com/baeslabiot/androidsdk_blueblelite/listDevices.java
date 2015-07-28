package com.baeslabiot.androidsdk_blueblelite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baeslabiot.engin.blueBLEList;
import android.text.format.Time;

import java.util.ArrayList;

/**
 * Created by Pariwat on 14/07/2015.
 */
public class listDevices extends BaseAdapter {

    ArrayList<blueBLEList> data = new ArrayList<blueBLEList>();
    LayoutInflater inflater;
    Context context;

    public listDevices(Context context){
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.Name.setText(data.get(position).getNAME());
        mViewHolder.Address.setText(data.get(position).getADDRESS());
        mViewHolder.RSSI.setText(data.get(position).getRSSI()+"");
        mViewHolder.MAJOR.setText(data.get(position).getMAJOR()+"");
        mViewHolder.MINOR.setText(data.get(position).getMINOR()+"");
        mViewHolder.UUID.setText(data.get(position).getUUID());
        try{
            Time time = new Time();
            time.set(Long.valueOf(data.get(position).getTIMESTAMP()));
            mViewHolder.TIME.setText(time.format("%d/%m/%Y %H:%M:%S"));
        }catch(Exception e){}
        mViewHolder.TxPower.setText(data.get(position).getPOWER()+"");
        mViewHolder.BATTERY.setText(data.get(position).getBATTERY()+"");
        return convertView;
    }

    public void setAdapter(ArrayList<blueBLEList> adp){
        this.data = adp;
    }

    private class  ViewHolder{
        TextView Name,Address,RSSI,TIME,MINOR,MAJOR,UUID,TxPower,BATTERY;
        public ViewHolder(View view){
            Name = (TextView) view.findViewById(R.id.Name);
            Address = (TextView) view.findViewById(R.id.Address);
            RSSI = (TextView)view.findViewById(R.id.RSSI);
            TIME = (TextView) view.findViewById(R.id.TIME);
            MINOR = (TextView) view.findViewById(R.id.MINOR);
            MAJOR = (TextView) view.findViewById(R.id.MAJOR);
            UUID = (TextView) view.findViewById(R.id.UUID);
            TxPower = (TextView) view.findViewById(R.id.txPower);
            BATTERY = (TextView) view.findViewById(R.id.battery);
        }
    }



}
