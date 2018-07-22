package com.devman.QRscanUI.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devman.QRscanUI.R;
import com.devman.QRscanUI.model.login.User;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.parceler.Parcels;

import java.io.IOException;

public class ScanQrActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textPost;
    private ImageView btnBack;
    private SurfaceView cameraView;
    private BarcodeDetector barcode;
    private CameraSource cameraSource;
    private SurfaceHolder holder;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        initData();
        initInstance();
    }

    private void initData() {
        user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
    }

    private void initInstance() {

        textPost = findViewById(R.id.textPost);
        btnBack = findViewById(R.id.btnBack);
        cameraView = findViewById(R.id.cameraPre);
        btnBack.setOnClickListener(this);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(ScanQrActivity.this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if (!barcode.isOperational()) {
            showToast("Sorry, Couldn't setup the detector");
            finish();
        }
        cameraSource = new CameraSource.Builder(ScanQrActivity.this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(ScanQrActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ScanQrActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1001);
                    return;
                }
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodeArray = detections.getDetectedItems();
                if (barcodeArray.size() != 0){
                    textPost.post(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(ScanQrActivity.this, WebResultScanActivity.class);
                            intent.putExtra("urlResult", barcodeArray.valueAt(0).displayValue);
                            intent.putExtra("user", Parcels.wrap(user));
                            cameraSource.stop();
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1001:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(ScanQrActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onClick (View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private void showToast (String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

