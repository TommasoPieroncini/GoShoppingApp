package com.pieroncini.tommaso.goshopping.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pieroncini.tommaso.goshopping.Models.Group;
import com.pieroncini.tommaso.goshopping.R;

import java.util.ArrayList;

/**
 * Created by Tommaso on 3/1/2016.
 */
// Custom ArrayAdapter to populate the ListView of groups in my style
public class GroupsAdapter extends ArrayAdapter<Group> {
    public GroupsAdapter(Context context, ArrayList<Group> groups) {
        super(context, 0, groups);
    }

    private static class ViewHolder {
        private TextView groupName;
        private ImageView groupImage;
        private TextView groupCreator;
        private TextView groupMembers;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = getItem(position);

        // ViewHolder info
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.groups_list_single_group, parent, false);
            viewHolder.groupName = (TextView) convertView.findViewById(R.id.textView5);
            viewHolder.groupImage = (ImageView) convertView.findViewById(R.id.imageView3);
            viewHolder.groupCreator = (TextView) convertView.findViewById(R.id.textView6);
            viewHolder.groupMembers = (TextView) convertView.findViewById(R.id.textView7);
            convertView.setTag(viewHolder);
        } else {
            Log.e("using the holder", "NOW");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.groupName.setText(group.getName());
        viewHolder.groupCreator.setText("by " + group.getMembers()[0]);
        viewHolder.groupMembers.setText("Members: " + group.getMembersString());
        Bitmap imgBitMp = group.getGroupImage();
        if (imgBitMp != null) {
            viewHolder.groupImage.setImageBitmap(imgBitMp);
        }

        return convertView;
    }
}
