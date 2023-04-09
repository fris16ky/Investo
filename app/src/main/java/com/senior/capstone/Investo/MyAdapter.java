package com.senior.capstone.Investo;

/**
 * This is more or less a test/implemention of a custom adapter to a list view
 * Use this as an example for further development
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return this.userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_item, null);

        TextView tv_adapter_id = (TextView) convertView.findViewById(R.id.tv_adapter_id);
        TextView tv_adapter_firstName = (TextView) convertView.findViewById(R.id.tv_adapter_firstName);
        TextView tv_adapter_lastName = (TextView) convertView.findViewById(R.id.tv_adapter_lastName);
        TextView tv_adapter_email = (TextView) convertView.findViewById(R.id.tv_adapter_email);
        TextView tv_adapter_username = (TextView) convertView.findViewById(R.id.tv_adapter_username);
        TextView tv_adapter_password = (TextView) convertView.findViewById(R.id.tv_adapter_password);

        User user = userArrayList.get(position);

        tv_adapter_id.setText(String.valueOf("ID: " + user.getId()));
        tv_adapter_firstName.setText(String.valueOf("First name: " + user.getFirst_name()));
        tv_adapter_lastName.setText(String.valueOf("Last name: " + user.getLast_name()));
        tv_adapter_email.setText(String.valueOf("Email: " + user.getEmail()));
        tv_adapter_username.setText(String.valueOf("Username: " + user.getUsername()));
        tv_adapter_password.setText(String.valueOf("Password: " + user.getPassword()));

        return convertView;
    }
}
