package com.baeslabiot.androidsdk_blueblelite;

import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pariwat on 22/07/2015.
 */
public class listDetail extends BaseAdapter {

    private List<BluetoothGattService> gattServices;
    LayoutInflater inflater;
    Context context;

    public  listDetail(Context context){
        this.context = context;
        inflater = LayoutInflater.from(this.context);

    }

    public void setData(List<BluetoothGattService> gattServices){
        this.gattServices = gattServices;
    }

    @Override
    public int getCount() {
        if(gattServices != null)
            return gattServices.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return gattServices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.detail_serivce, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.Name.setText("UnKnown service");
        mViewHolder.UUID.setText(gattServices.get(position).getUuid()+"");
        mViewHolder.datastring.setText("");
        mViewHolder.dataarray.setText("");
        return convertView;
    }

    private class  ViewHolder{
        TextView Name,UUID,datastring,dataarray;
        public ViewHolder(View view){
            Name = (TextView) view.findViewById(R.id.Name);
            UUID = (TextView) view.findViewById(R.id.UUID);
            dataarray = (TextView) view.findViewById(R.id.dataarray);
            datastring = (TextView) view.findViewById(R.id.datastring);

        }
    }


}
