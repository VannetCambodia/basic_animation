package com.example.basic_animation;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Animation {

    public static void blinkAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.blink_animation);
        imageView.startAnimation(animation);
    }

    public static void moveAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.move_animation);
        imageView.startAnimation(animation);
    }

    public static void zoomAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.zoom_animation);
        imageView.startAnimation(animation);
    }

    public static void rotateAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_animation);
        imageView.startAnimation(animation);
    }

    public static void fadeAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade_animation);
        imageView.startAnimation(animation);
    }

    public static void slidAnimation(Context context, ImageView imageView){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_animation);
        imageView.startAnimation(animation);
    }

    public static void stopAnimation(ImageView imageView){
        imageView.clearAnimation();
    }

    public static void drawableAnimation(){
        // I will initial Later
    }

}
