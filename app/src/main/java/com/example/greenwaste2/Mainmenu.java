package com.example.greenwaste2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Mainmenu extends AppCompatActivity {
//    private ImageView imageview;
//    private EditText itemname;
//    private EditText itemdescription;
//    private EditText addressname;
//    private Button additem;
//    private Button userprofile;
//    private Button adview;
//    private Button signout;
//    private ItemList itemList;
//    private ProgressBar progressbar;

    private Uri imageuri;
    private StorageReference storagereference;
    private DatabaseReference databasereference;
    private FirebaseAuth auth;


    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

//        imageview = (ImageView) findViewById(R.id.image_view);
//        itemname = (EditText) findViewById(R.id.item_name);
//        itemdescription = (EditText) findViewById(R.id.item_description);
//        addressname = (EditText) findViewById(R.id.address_name);
//        additem = (Button) findViewById(R.id.add_item);
//        userprofile = (Button) findViewById(R.id.user_profile);
//        adview = (Button) findViewById(R.id.ad_view);
//        signout = (Button) findViewById(R.id.sign_out);

        storagereference = FirebaseStorage.getInstance().getReference("ItemsUpload");
        databasereference = FirebaseDatabase.getInstance().getReference("Items Upload");


        //To Select image from the ActionButton
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SelectImage();
//            }
//        });


//        // To add item
//        additem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ItemUploadFile();
//
//            }
//        });
//
//
//        // To User ProfilePage
//        userprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Mainmenu.this, UserProfile.class);
//                startActivity(intent);
//            }
//        });
//
//        //To SignOut
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Login.class));
//                finish();
//            }
//        });

    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Mainmenu.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_CAMERA) {
//                Bundle bundle = data.getExtras();
//                final Bitmap bmp = (Bitmap) bundle.get("data");
//                imageview.setImageBitmap(bmp);
//            } else if (requestCode == SELECT_FILE) {
//                Uri selectedImageUri = data.getData();
//                imageview.setImageURI(selectedImageUri);
//            }
//        }
//    }

    // To Upload Items
    private String getFileExtension(Uri uri) { // to get the extension of the image picked (i.e jpg, png, etc.)
        ContentResolver contentresolver = getContentResolver();
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(contentresolver.getType(uri));

    }

    // To Upload
//    private void ItemUploadFile() {
//        if (imageuri != null){
//            StorageReference filerefrence = storagereference.child(System.currentTimeMillis()
//            + "." + getFileExtension(imageuri));
//            filerefrence.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Handler handler = new Handler();
//                    handler.postDelayed((new Runnable() {
//                        @Override
//                        public void run() {
//                            progressbar.setProgress(0);
//                        }
//                    }), 500);
//                    Toast.makeText(Mainmenu.this, "Item Uploaded Successfully", Toast.LENGTH_LONG).show();
//                    ItemList itemList = new ItemList(itemname.getText().toString().trim(), itemdescription.getText().toString().trim(), addressname.getText().toString().trim(),
//                            taskSnapshot.getStorage().getDownloadUrl().toString());
//
//                    String itemId = databasereference.push().getKey();
//                    databasereference.child(itemId).setValue(itemList);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Mainmenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                    progressbar.setProgress((int) progress);
//                }
//            });
//        }else {
//            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
//        }
//    }

}
