package com.example.sixteen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Dashboard extends AppCompatActivity {

    Button camera;
    Button upload;
    Button buy;
    ImageView photo;
    private DatabaseReference reference;
    private FirebaseDatabase rootNode;
    Bitmap image;
    private LocationManager locationManager;
    double latitude;
    double longitude;
    private static final int REQUEST_LOCATION = 1;

    private StorageReference mStorageRef;

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private void gps_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait, Location Seems Off. Turn On To Find Post Related To Your Location.")
                .setMessage("Please Select High Priority!")
                .setCancelable(false)
                .setPositiveButton("Sure",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("Whatever",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        camera = (Button)findViewById(R.id.camera);
        upload = (Button)findViewById(R.id.upload);
        photo = (ImageView) findViewById(R.id.image);
        buy = (Button) findViewById(R.id.page);
        List<Address> addresses ;
        String pata = new String();
        if (!isLocationEnabled()) {
            gps_dialog();
        } else {
            try {
                @SuppressLint("MissingPermission") Location locationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (locationGPS != null) {
                    latitude = locationGPS.getLatitude();
                    longitude = locationGPS.getLongitude();
                    // Toast.makeText(getApplicationContext(), latitude + " : " + longitude, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Finding...", Toast.LENGTH_SHORT).show();
                }
                System.out.println(longitude + " : " + latitude);
                if (latitude != 0.0 && longitude != 0.0) {
                    Geocoder gcd = new Geocoder(this, Locale.getDefault());

                    try {
                        addresses = gcd.getFromLocation(latitude, longitude, 1);
                        pata = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            }
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 0);
            }
        });
        Intent intent = getIntent();
        final String user_username = intent.getStringExtra("username");
        final String user_name = intent.getStringExtra("name");
        final String user_email = intent.getStringExtra("email");
        final String user_password = intent.getStringExtra("password");
        final String address = pata;
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(user_username,user_email,user_name,address);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Final.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            photo.setImageBitmap(image);
        }
    }

    private void upload(final String username, final String email, final String name, final String address) {
        final ProgressBar p = findViewById(R.id.progressbar);



        p.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        final String random = UUID.randomUUID().toString();
        StorageReference imageRef = mStorageRef.child("image/" + random);
        rootNode = FirebaseDatabase.getInstance();
        final String[] url = new String[1];
//        rootNode = FirebaseDatabase.getInstance();


        byte[] b = stream.toByteArray();
        imageRef.putBytes(b)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                url[0] = String.valueOf(downloadUri);
                                reference = rootNode.getReference("pics");
                                ImageHelp imageHelp = new ImageHelp(name,username,address,email,url[0]);
                                url[0] = url[0].replace("/","").replace("%","").replace(".","").replace("#","").replace("$","").replace("[","").replace("]","");
                                reference.child(url[0]).setValue(imageHelp);
                                Toast.makeText(Dashboard.this, "Photo Uploaded and address is "+address + "and url is "+url[0] , Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(Dashboard.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
