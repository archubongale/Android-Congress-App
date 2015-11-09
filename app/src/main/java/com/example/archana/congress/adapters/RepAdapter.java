package com.example.archana.congress.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.archana.congress.R;
import com.example.archana.congress.models.Representative;

import java.util.ArrayList;
import java.util.List;

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
            holder.mRepPhone = (TextView) convertView.findViewById(R.id.repPhone);
            holder.mRepWebsite = (TextView) convertView.findViewById(R.id.repWebsite);
            holder.mRepOffice = (TextView) convertView.findViewById(R.id.repOffice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Representative rep = mRepresentatives.get(position);

        holder.mRepName.setText(rep.getName());
        holder.mRepParty.setText(rep.getParty());
        holder.mRepGender.setText(rep.getGender());
        holder.mRepBirthday.setText(rep.getBirthday());
        holder.mRepPhone.setText(rep.getPhone());
        holder.mRepWebsite.setText(rep.getWebsite());
        holder.mRepOffice.setText(rep.getOffice());



        if (rep.getParty().equals("D")) {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#800099FF"));
        } else if (rep.getParty().equals("R")) {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#80CC0000"));
        } else {
            holder.mRepLayout.setBackgroundColor(Color.parseColor("#806C6C6C"));
        }

        holder.mRepPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneString = rep.getPhone().replaceAll("[^0-9]","");
                Uri phone = Uri.parse("tel:" + phoneString);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phone);
                if(isIntentSafe(callIntent)) {
                    mContext.startActivity(callIntent);
                }
            }
        });

        holder.mRepWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteString = rep.getWebsite();
                Uri website = Uri.parse(websiteString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, website);

                if(isIntentSafe(mapIntent)) {
                    mContext.startActivity(mapIntent);
                }
            }
        });

        holder.mRepOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String officeString = rep.getOffice().replaceAll(" ", "+");
                Uri office = Uri.parse("geo:0,0?q=" + officeString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, office);

                if(isIntentSafe(mapIntent)) {
                    mContext.startActivity(mapIntent);
                }
            }
        });



        return convertView;
    }

    private boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    public static class ViewHolder {
        RelativeLayout mRepLayout;
        TextView mRepName;
        TextView mRepBirthday;
        TextView mRepGender;
        TextView mRepParty;
        TextView mRepPhone;
        TextView mRepWebsite;
        TextView mRepOffice;
    }

}
