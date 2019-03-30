package com.r.permproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String[] app = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int MY_PERMISSIONS_REQUEST = 1;

    public static boolean hasPermissions(Context context, Activity activity, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else {
                    ActivityCompat.requestPermissions(activity, permissions,
                            MY_PERMISSIONS_REQUEST);
                }

            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(MainActivity.this, MainActivity.this, app)) {
                    ActivityCompat.requestPermissions(MainActivity.this, app, MY_PERMISSIONS_REQUEST);
                }
            }
        });
    }

    public void showAlertDialogButtonClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My title");
        builder.setMessage("Open settings to grant permision");

        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getBaseContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);


            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        boolean showRationale = shouldShowRequestPermissionRationale(permission);
                        if (!showRationale) {

                            showAlertDialogButtonClicked();


                        }

                    } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(in);

                    } else {

                        requestPermissions(
                                permissions,
                                MY_PERMISSIONS_REQUEST);
                    }
                    return;
                }

            }


        }
    }
}
