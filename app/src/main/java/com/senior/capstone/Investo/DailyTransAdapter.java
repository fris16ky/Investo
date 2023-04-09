package com.senior.capstone.Investo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DailyTransAdapter extends BaseAdapter {
    DbHelper dbHelper;
    Context context;
    ArrayList<DailyTrans> dailyTransArrayList;
    DailyTransAdapter dailyTransAdapter;

    public DailyTransAdapter(Context context, ArrayList<DailyTrans> dailyTransArrayList) {
        dbHelper = new DbHelper(context.getApplicationContext());
        this.context = context;
        this.dailyTransArrayList = dailyTransArrayList;
        this.dailyTransAdapter = this;
    }

    @Override
    public int getCount() {
        return this.dailyTransArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyTransArrayList.get((position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NumberFormat format = new DecimalFormat("$#,###");

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.daily_trans_adapter, null);

        TextView tv_adapter_daily_trans_amount = (TextView) convertView.findViewById(R.id.tv_adapter_daily_trans_amount);
        TextView tv_adapter_daily_trans_note = (TextView) convertView.findViewById(R.id.tv_adapter_daily_trans_note);

        Button daily_trans_del_btn = (Button) convertView.findViewById(R.id.daily_trans_del_btn);

        DailyTrans dailyTrans = dailyTransArrayList.get(position);

        tv_adapter_daily_trans_amount.setText((format.format(dailyTrans.getAmount())));
        tv_adapter_daily_trans_note.setText(String.valueOf(dailyTrans.getNote()));

        daily_trans_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long itemID = dailyTrans.getId();
                dbHelper.deleteDailyTrans(String.valueOf(itemID));
                dailyTransAdapter.notifyDataSetChanged();

                Intent intent = new Intent(context, OverviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }



}
