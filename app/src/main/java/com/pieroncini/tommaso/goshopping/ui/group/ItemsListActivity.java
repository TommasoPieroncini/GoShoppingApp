package com.pieroncini.tommaso.goshopping.ui.group;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pieroncini.tommaso.goshopping.data.models.Group;
import com.pieroncini.tommaso.goshopping.data.models.Image;
import com.pieroncini.tommaso.goshopping.data.models.Item;
import com.pieroncini.tommaso.goshopping.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.ui.base.BaseActivity;
import com.pieroncini.tommaso.goshopping.ui.main.GroupsListActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class ItemsListActivity extends BaseActivity implements IItemsListView {

    @BindView(R.id.listView)
    ListView listView;

    private ArrayList<Item> items;
    private Context context = null;
    private static Group currentGroup;
    private static String trueGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        currentGroup = ((MyApplication) getApplicationContext()).getCurrentGroup();
        trueGroupName = currentGroup.getName();
        setTitle(currentGroup.getName());
        listView = (ListView) findViewById(R.id.listView);
        context = this;
        refresh();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items_list_activity, menu);
        return true;
    }

    // Long Menu!
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all_entries) {
            safetyPopUp("all_entries_delete");
            return true;
        }

        if (id == R.id.action_refresh) {
            refresh();
            return true;
        }

        if (id == R.id.action_set_group_picture) {
            choosePicSource();
        }

        if (id == R.id.action_delete_group) {
            safetyPopUp("group_delete");
        }

        if (id == R.id.action_add_item) {
            startActivity(new Intent(this, AddItemActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // choosing source for group picture
    private void choosePicSource() {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        final Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        builder.setMessage("")
                .setPositiveButton("Camera",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                startActivityForResult(camera, 2);
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                startActivityForResult(gallery, 3);
                                d.dismiss();
                            }
                        });
        builder.create().show();
    }

    // receiving the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 2) {
                if (data != null) {
                    Bitmap groupPic = (Bitmap) data.getExtras().get("data");
                    Log.e("log", "You took a picture");
                    String name = "groupPic-" + (currentGroup.getName() + currentGroup.getAdmin().getCreatedAt()) + ".jpg";
                    Image image = new Image(groupPic, name);
                }
            }

            if (requestCode == 3) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                Log.e("filePath", filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap groupPic = fixRotation(BitmapFactory.decodeFile(filePath, options), filePath);
                String name = "groupPic-" + (currentGroup.getName() + currentGroup.getCreatedAt()) + ".jpg";
                Image image = new Image(groupPic, name);
            }
        }
    }

    // fixing image orientation
    public Bitmap fixRotation(Bitmap orig, String imagePath){
        ExifInterface exif = null;
        int rotate = 0;
        try {
            exif = new ExifInterface(imagePath);
        } catch (Exception e) {
            Log.e("log_error", "image not found " + e);
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), matrix, true);
    }

    // Group deletion safety dialog
    private void safetyPopUp(final String flag) {
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        String message = "";
        if (flag.equals("group_delete")) {
            message = "Are you sure you want to delete this group?";
        } else if (flag.equals("all_entries_delete")) {
            message = "Are you sure you want to erase all entries?";
        }

        builder.setMessage(message)
                .setPositiveButton("Yes, I'm sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                if (flag.equals("group_delete")) {
                                    try {

                                        d.dismiss();
                                        Intent leave = new Intent (getApplicationContext(),
                                                GroupsListActivity.class);
                                        startActivity(leave);
                                    } catch (Exception e){
                                        Log.e("log_tag","failed to execute delete group");
                                    }
                                } else if (flag.equals("all_entries_delete")) {
                                    try {

                                    } catch (Exception e){
                                        Log.e("log_tag","failed to execute delete all items");
                                    }
                                    refresh();
                                }
                                d.dismiss();
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

    // Refreshes the list of items
    public void refresh(){
        try {

        }catch(Exception e){
            Log.e("log_tag", "failed to execute getData");
        }
        ArrayAdapter<Item> adapter = new ItemsListAdapter(context, items);
        listView.setAdapter(adapter);
        Log.e("TEST", "refresh");
    }
}






