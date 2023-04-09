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

public class BudgetAdapter extends BaseAdapter {
    DbHelper dbHelper;
    Context context;
    ArrayList<Budget> budgetArrayList;
    BudgetAdapter budgetAdapter;

    public BudgetAdapter(Context context, ArrayList<Budget> budgetArrayList) {
        dbHelper = new DbHelper(context.getApplicationContext());
        this.context = context;
        this.budgetArrayList = budgetArrayList;
        this.budgetAdapter = this;
    }

    @Override
    public int getCount() {
        return this.budgetArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return budgetArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NumberFormat format = new DecimalFormat("$#,###");

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.budget_list_item, null);


        TextView tv_budget_amount = (TextView) convertView.findViewById(R.id.tv_budget_amount);
        TextView tv_budget_note = (TextView) convertView.findViewById(R.id.tv_budget_note);

        Button budget_del_btn = (Button) convertView.findViewById(R.id.budget_del_btn);

        Budget budget = budgetArrayList.get(position);

        tv_budget_amount.setText(format.format(budget.getAmount()));
        tv_budget_note.setText(budget.getNote());

        budget_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long itemID = budget.getId();
                dbHelper.deleteBudget(String.valueOf(itemID));
                budgetAdapter.notifyDataSetChanged();

                Intent intent = new Intent(context, OverviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
