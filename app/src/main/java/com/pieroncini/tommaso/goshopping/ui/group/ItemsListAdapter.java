package com.pieroncini.tommaso.goshopping.ui.group;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pieroncini.tommaso.goshopping.data.models.Item;
import com.pieroncini.tommaso.goshopping.R;

import java.util.ArrayList;

/**
 * Created by Tommaso on 8/12/2016.
 */
// Custom implementation of the ArrayAdapter class for my own ListView population
public class ItemsListAdapter extends ArrayAdapter<Item> {

    public ItemsListAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    // Declaring ViewHolder properties
    private static class ViewHolder {
        private TextView itemName;
        private ImageView itemImage;
        private TextView itemCreator;
        private TextView itemQuantity;
        private Button itemDelete;
        private CheckBox itemCheck;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);

        // ViewHolder behavior if it is empty
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items_list_single_item, parent, false);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.textView9);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.imageView5);
            viewHolder.itemCreator = (TextView) convertView.findViewById(R.id.textView10);
            viewHolder.itemQuantity = (TextView) convertView.findViewById(R.id.textView11);
            viewHolder.itemDelete = (Button) convertView.findViewById(R.id.button11);
            viewHolder.itemCheck = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        } else {
            // ViewHolder behavior if it is full
            Log.e("using the holder", "NOW");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // ViewHolder behavior at all requests

        viewHolder.itemName.setText(item.getName());
        viewHolder.itemCreator.setText("by " + item.getCreator().getUsername());
        viewHolder.itemQuantity.setText("Quantity: " + item.getQuantity());
        viewHolder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safetyPopUp(position, item);
            }
        });
        viewHolder.itemCheck.setChecked(item.getCheck().getValue());
        viewHolder.itemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // item.setCheck(!item.getCheck().getValue(), );
                } catch (Exception e) {
                    Log.e("Log_Tag", "failed to execute itemChecked");
                }
            }
        });
        Bitmap imgBitMp = item.getImage().getBitmap();
        if (imgBitMp != null) {
            viewHolder.itemImage.setImageBitmap(imgBitMp);
        } else {
            viewHolder.itemImage.setImageResource(R.drawable.shopping_bag);
        }

        viewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPopUp(position, item);
            }
        });

        return convertView;
    }

    // Item deletion safety dialog
    private void safetyPopUp(final Integer position, final Item item){
        final AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
        String message = "Do you want to delete this item?";
        builder.setMessage(message)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                try {
                                    // ItemsListActivity.refresh();
                                } catch (Exception e) {
                                    Log.e("Failed to delete item", "Error: " + e);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    // Item notes and picture dialog
    private void infoPopUp(final Integer position, final Item item) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_list_item);
        TextView info = (TextView) dialog.findViewById(R.id.textView2);
        if (!item.getNotes().equals("")) {
            info.setText(item.getNotes());
        }
        ImageView photo = (ImageView) dialog.findViewById((R.id.imageView4));
        if (item.getImage() != null) {
            photo.setImageBitmap(item.getImage().getBitmap());
        }
        dialog.show();
    }
}
