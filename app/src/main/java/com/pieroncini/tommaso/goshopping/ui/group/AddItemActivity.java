package com.pieroncini.tommaso.goshopping.ui.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pieroncini.tommaso.goshopping.data.models.Group;
import com.pieroncini.tommaso.goshopping.data.models.Image;
import com.pieroncini.tommaso.goshopping.MyApplication;
import com.pieroncini.tommaso.goshopping.R;
import com.pieroncini.tommaso.goshopping.ui.base.BaseActivity;

import butterknife.BindView;

// TODO: remove and substitute with fragment/dialog - also add save item functionality
public class AddItemActivity extends BaseActivity {

    @BindView(R.id.editText9)
    EditText itemName;

    @BindView(R.id.editText11)
    EditText itemQuantity;

    @BindView(R.id.editText12)
    EditText itemNotes;

    private Group currentGroup;
    private String groupName;
    private String username;
    private Intent toItemsList;

    @BindView(R.id.textView8)
    TextView pictureTaken;

    private Bitmap photo;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = ((MyApplication) getApplicationContext()).getUsername();
        currentGroup = ((MyApplication) getApplicationContext()).getCurrentGroup();
        setTitle("New Item for " + currentGroup.getName());
        groupName = currentGroup.getName() + currentGroup.getAdmin();
        Button newItemSubmit = (Button) findViewById(R.id.button9);
        ImageView takePicture = (ImageView) findViewById(R.id.button10);
        toItemsList = new Intent(getApplicationContext(), ItemsListActivity.class);

        // setting up soft input keyboard to pop up
        itemName.requestFocus();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        // if picture button is pressed
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemName.getText().toString().equals("")) {
                    Intent cameraIntent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 2);
                } else {
                    Toast.makeText(getApplicationContext(), "You need to specify the name of the item first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        itemNotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit();
                }
                return true;
            }
        });


        newItemSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @Override
    protected void setUp() {

    }

    // On submission of the item, send quantity, name, picture and notes to the server
    public void submit() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (data != null) {
                photo = (Bitmap) data.getExtras().get("data");
                pictureTaken.setText("Picture Taken!");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(toItemsList);
        }
        return true;
    }
}
