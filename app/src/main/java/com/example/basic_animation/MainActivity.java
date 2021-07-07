package com.example.basic_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.basic_animation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private Button btnMove;
    private Button btnZoom;
    private Button btnBlink;
    private Button btnRotate;
    private Button btnFade;
    private Button btnSlide;
    private Button btnStopAnimation;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        btnMove = mBinding.btnMove;
        btnBlink = mBinding.btnBlink;
        btnZoom = mBinding.btnZoom;
        btnRotate = mBinding.btnRotate;
        btnFade = mBinding.btnFade;
        btnSlide = mBinding.btnSlide;
        imageView = mBinding.imageView;
        btnStopAnimation = mBinding.btnStop;

        btnMove.setOnClickListener(v -> Animation.moveAnimation(getApplicationContext(),imageView));

        btnBlink.setOnClickListener(v -> Animation.blinkAnimation(getApplicationContext(),imageView));

        btnZoom.setOnClickListener(v -> Animation.zoomAnimation(getApplicationContext(),imageView));

        btnRotate.setOnClickListener(v -> Animation.rotateAnimation(getApplicationContext(),imageView));

        btnFade.setOnClickListener(v -> Animation.fadeAnimation(getApplicationContext(),imageView));

        btnSlide.setOnClickListener(v -> Animation.slidAnimation(getApplicationContext(),imageView));

        btnStopAnimation.setOnClickListener(v -> Animation.stopAnimation(imageView));

    }
}