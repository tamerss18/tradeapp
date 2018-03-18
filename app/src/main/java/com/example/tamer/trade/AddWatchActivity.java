package com.example.tamer.trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;



public class AddWatchActivity extends Activity {

    EditText editWatchBrand, editColor, editWatchSpecs, editSize, editDesiredWatch;
    Spinner spnrCondition;
    ImageView imageView;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    StorageReference mStorage;
    private StorageReference storageReference;
    private Uri filePath;
    private Watch watch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.activity_add_watch);
        mStorage = FirebaseStorage.getInstance().getReference("WatchPhoto/");
        progressDialog = new ProgressDialog(this);
        editWatchBrand=(EditText)findViewById(R.id.editTextwatchbrand);
        editColor=(EditText)findViewById(R.id.editcolor);
        editWatchSpecs=(EditText)findViewById(R.id.editTextSpecs);
        editSize=(EditText)findViewById(R.id.editSize);
        editDesiredWatch=(EditText)findViewById(R.id.editdesire);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(selectListener);
        spnrCondition=(Spinner)findViewById(R.id.spinnercondition);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Watches");
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                imageView.setTag(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Upload upload = new Upload(editWatchBrand.getText().toString(), downloadUrl.toString()); //taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
                            //String uploadId = mDatabase.push().getKey();
                            //mDatabase.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            @SuppressWarnings("VisibleForTests") long bytesTransferred = taskSnapshot.getBytesTransferred();
                            @SuppressWarnings("VisibleForTests") long totalByteCount = taskSnapshot.getTotalByteCount();
                            double progress = (100.0 * bytesTransferred) / totalByteCount;
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }

    public void DoneClick(View view){
        if(!CheckInfo()) return;
        watch = new Watch(databaseReference.push().getKey().toString(),
                firebaseAuth.getCurrentUser().getUid().toString(),
                editWatchBrand.getText().toString(),
                editColor.getText().toString(),
                editWatchSpecs.getText().toString(),
                editSize.getText().toString(),
                editDesiredWatch.getText().toString(),
                spnrCondition.getSelectedItem().toString(),
                imageView.getTag().toString());
        databaseReference.child(databaseReference.push().getKey()).setValue(watch);
        uploadFile();
        startActivity(new Intent(AddWatchActivity.this,FeedActivity.class));
    }

    public boolean CheckInfo() {
        if(editWatchBrand.getText().toString().isEmpty()) {
            Toast.makeText(this,"Enter Watch Brand",Toast.LENGTH_SHORT).show();
            editWatchBrand.requestFocus();
            return false;
        }
        if(editColor.getText().toString().isEmpty()) {
            Toast.makeText(this,"Enter Color",Toast.LENGTH_SHORT).show();
            editColor.requestFocus();
            return false;
        }
        if(editWatchSpecs.getText().toString().isEmpty()) {
            Toast.makeText(this,"Enter Watch Specs",Toast.LENGTH_SHORT).show();
            editWatchSpecs.requestFocus();
            return false;
        }
        if(editSize.getText().toString().isEmpty()) {
            Toast.makeText(this,"Enter Watch Size",Toast.LENGTH_SHORT).show();
            editSize.requestFocus();
            return false;
        }
        if(editDesiredWatch.getText().toString().isEmpty()) {
            Toast.makeText(this,"Enter Desired Watch",Toast.LENGTH_SHORT).show();
            editDesiredWatch.requestFocus();
            return false;
        }
        return true;
    }
}
