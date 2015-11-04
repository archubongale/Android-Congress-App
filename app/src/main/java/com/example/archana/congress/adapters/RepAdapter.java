package com.example.archana.congress.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archana.congress.R;
import com.example.archana.congress.models.Representative;

import java.util.ArrayList;

/**
 * Created by Guest on 11/3/15.
 */
public class RepAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Representative> mRepresentatives;

    public RepAdapter (Context context, ArrayList<Representative> representatives) {
        mContext = context;
        mRepresentatives = representatives;
    }


    public int getCount() {
        return mRepresentatives.size();
    }


    public Object getItem(int position) {
        return mRepresentatives.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.representative_list, null);
            holder = new ViewHolder();
            holder.mRepLayout = (RelativeLayout) convertView.findViewById(R.id.repLayout);
            holder.mRepName = (TextView) convertView.findViewById(R.id.repName);
            holder.mRepParty = (TextView) convertView.findViewById(R.id.repParty);
            holder.mRepGender = (TextView) convertView.findViewById(R.id.repGender);
            holder.mRepBirthday = (TextView) convertView.findViewById(R.id.repBirthday);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Representative rep = mRepresentatives.get(position);

        holder.mRepName.setText(rep.getName());
        holder.mRepParty.setText(rep.getParty());
        holder.mRepGender.setText(rep.getGender());
        holder.mRepBirthday.setText(rep.getBirthday());

        if (rep.getParty().equals("D")) {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#800099FF"));
        } else if (rep.getParty().equals("R")) {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#80CC0000"));
        } else {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#806C6C6C"));
        }

        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout mRepLayout;
        TextView mRepName;
        TextView mRepBirthday;
        TextView mRepGender;
        TextView mRepParty;
    }

}
