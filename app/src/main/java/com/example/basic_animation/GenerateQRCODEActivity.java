package com.example.basic_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basic_animation.databinding.ActivityGenerateQrcodeactivityBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCODEActivity extends AppCompatActivity {

    private ActivityGenerateQrcodeactivityBinding qrBinding;
    private Button btnGenerateQrCode;
    private Button btnFingerPrint;
    private ImageView imageView;
    private TextInputEditText textInputEditText;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrBinding = ActivityGenerateQrcodeactivityBinding.inflate(getLayoutInflater());
        setContentView(qrBinding.getRoot());

        btnGenerateQrCode = qrBinding.btnQr;
        btnFingerPrint = qrBinding.btnFingerPrint;
        imageView = qrBinding.imageView2;
        textInputEditText = qrBinding.editText;

        btnGenerateQrCode.setOnClickListener(v -> generateQRCODE());

        btnFingerPrint.setOnClickListener(v -> startActivity(new Intent(this, BioAuthentication.class)));
    }

    private void generateQRCODE(){
        if(TextUtils.isEmpty(textInputEditText.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please Input some text",Toast.LENGTH_SHORT).show();
        }else{
            // below line is for getting the window service
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // Initializing a variable for default display
            Display display = windowManager.getDefaultDisplay();

            // Creating a variable for point whic
            Point point = new Point();
            display.getSize(point);

            // Getting width and height of a point
            int width = point.x;
            int height = point.y;

            // Generate dimension from width and height
            int dimen = width < height ? width:height;

            // Setting this dimensions inside our qr code
            // encoder to generate our qr code
            qrgEncoder = new QRGEncoder(textInputEditText.getText().toString(), null, QRGContents.Type.TEXT,dimen);
            try {
                //Getting our qrcode in the form of bitmap
                bitmap = qrgEncoder.encodeAsBitmap();

                // the bitmap is set inside our image
                // view using .setImageBitmap method
                imageView.setImageBitmap(bitmap);
            }catch (WriterException e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fingerPrintBottomSheet(){
        BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetDialogFragment.newInstance();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), BottomSheetDialogFragment.TAG);
    }
}