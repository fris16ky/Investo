package com.senior.capstone.Investo;

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

public class BillsAdapter extends BaseAdapter {
    DbHelper dbHelper;
    Context context;
    ArrayList<Bills> billsArrayList;
    BillsAdapter billsAdapter;

    public BillsAdapter(Context context, ArrayList<Bills> billsArrayList) {
        dbHelper = new DbHelper(context.getApplicationContext());
        this.context = context;
        this.billsArrayList = billsArrayList;
        this.billsAdapter = this;
    }

    @Override
    public int getCount() {
        return billsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return billsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NumberFormat format = new DecimalFormat("$#,###");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.monthly_bill_adapter, null);

        TextView tv_adapter_monthly_bill_note = (TextView) convertView.findViewById(R.id.tv_adapter_monthly_bill_note);
        TextView tv_adapter_monthly_bill_amount = (TextView) convertView.findViewById(R.id.tv_adapter_monthly_bill_amount);
        TextView tv_adapter_monthly_bill_date = (TextView) convertView.findViewById(R.id.tv_adapter_monthly_bill_date);

        Button bills_del_btn = (Button) convertView.findViewById(R.id.bills_del_btn);

        Bills bills = billsArrayList.get(position);

        bills_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long itemID = bills.getId();
                dbHelper.deleteBill(String.valueOf(itemID));
                billsAdapter.notifyDataSetChanged();

                Intent intent = new Intent(context, OverviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        tv_adapter_monthly_bill_amount.setText(format.format(bills.getAmount()));
        tv_adapter_monthly_bill_note.setText(String.valueOf(bills.getNote()));
        tv_adapter_monthly_bill_date.setText(String.valueOf("Due " + bills.getDate()));

        return convertView;
    }
}
