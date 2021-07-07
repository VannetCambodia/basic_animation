package com.example.basic_animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.basic_animation.databinding.ActivityDragDropBinding;

public class DragDropActivity extends AppCompatActivity {

    private ActivityDragDropBinding ddBinding;
    private Button btnDrag;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ddBinding = ActivityDragDropBinding.inflate(getLayoutInflater());
        setContentView(ddBinding.getRoot());

        btnDrag = ddBinding.button;
        constraintLayout = ddBinding.constrainLayout;

        constraintLayout.setOnDragListener((v, event) -> {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    Toast.makeText(getApplicationContext(),"ACTION_DRAG_STARTED",Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Toast.makeText(getApplicationContext(),"ACTION_DRAG_ENTERED",Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Toast.makeText(getApplicationContext(),"ACTION_DRAG_EXITED",Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Toast.makeText(getApplicationContext(),"ACTION_DRAG_LOCATION",Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Toast.makeText(getApplicationContext(),"ACTION_DRAG_ENDED",Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DROP:
                    Toast.makeText(getApplicationContext(),"ACTION_DROP",Toast.LENGTH_SHORT).show();
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    ConstraintLayout container = (ConstraintLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    break;
            }
            return true;
        });

        btnDrag.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                Toast.makeText(getApplicationContext(),"ACTION_DOWN",Toast.LENGTH_LONG).show();
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(btnDrag);

                v.startDrag(data,shadowBuilder,v,0);
                v.setVisibility(View.VISIBLE);
                return true;
            }else{
                return false;
            }
        });
    }
}