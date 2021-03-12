package com.example.myapplication.animator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.transition.Scene;

import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;

public class AnimatorActivity extends AppCompatActivity {
    AnimatorSet animatorSet1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        getWindow().setSharedElementEnterTransition(new ChangeTransform());
        getWindow().setSharedElementExitTransition(new ChangeTransform());

        Button button1 = findViewById(R.id.animatorButton1);
        ValueAnimator animation1 = ValueAnimator.ofFloat(0f, 600f);
        animation1.setDuration(1000);
        animation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                float animatedValue = (float)updatedAnimation.getAnimatedValue();
                button1.setTranslationX(animatedValue);
            }
        });

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(button1, "translationY", 600f);
        animation2.setDuration(1000);

        animatorSet1 = new AnimatorSet();
        animatorSet1.play(animation1).before(animation2);

        // get the element that receives the click event
        View imgContainerView = findViewById(R.id.animatorImageView1);

        // get the common element for the transition in this activity
        View androidRobotView = imgContainerView;

        // define a click listener
        imgContainerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnimatorActivity.this, SharedTransitionActivity.class);
                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="robot"
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(AnimatorActivity.this, androidRobotView, "robot");
                // start the new activity
                startActivity(intent, options.toBundle());
            }
        });


    }

    FlingAnimation fling;
    SpringAnimation springAnim;
    ScrollView scrollView;
    Transition transition1;
    Scene anotherScene;
    ViewGroup sceneRoot2;
    TextView textView1;
    @Override
    protected void onResume() {
        super.onResume();
        scrollView = findViewById(R.id.animatorScrollView1);

        Button button4 = findViewById(R.id.animatorButton4);
        Button buttonSc = findViewById(R.id.animatorScrollView1Button1);
        springAnim = new SpringAnimation(button4, DynamicAnimation.TRANSLATION_X,50);
        SpringAnimation springAnim2 = new SpringAnimation(buttonSc, DynamicAnimation.TRANSLATION_X);
        springAnim.setStartVelocity(1000);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                springAnim2.animateToFinalPosition(value);
            }
        });

        fling = new FlingAnimation(button4, DynamicAnimation.TRANSLATION_X);
        fling.setStartVelocity(1000)
                .setFriction(1.1f);

        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        Scene aScene = Scene.getSceneForLayout(sceneRoot, R.layout.a_scene, this);
        anotherScene = Scene.getSceneForLayout(sceneRoot, R.layout.b_scene, this);
        transition1 = new AutoTransition();

        sceneRoot2 = (ViewGroup) findViewById(R.id.scene_container);
        textView1 = new TextView(this);
        textView1.setText("new Line3");
        textView1.setId(R.id.text_view3);
        textView1.setTextSize(30);
        TransitionManager.beginDelayedTransition(sceneRoot2, transition1);

    }

    boolean vis = false;
    public void onViewGroupChangeClick(View view){
        if(!vis){
            findViewById(R.id.animatorButton2).setVisibility(View.VISIBLE);
            springAnim.start();
            vis = true;
        }
        else{
            findViewById(R.id.animatorButton2).setVisibility(View.GONE);
            vis=false;
        }
    }

    public void onButton1Click(View view){
//        animatorSet1.start();
        TransitionManager.go(anotherScene, transition1);
    }

    boolean vis2 = false;
    public void onButton2Click(View view){
//        fling.start();
        if(!vis2){
            sceneRoot2.addView(textView1,2);
            vis2 = true;
        }
        else{
            sceneRoot2.removeView(textView1);
            vis2 = false;
        }
    }

    boolean vis3 = false;
    public void onButton3Click(View view){
        if(!vis3){
            sceneRoot2.addView(textView1,2);
            vis3 = true;
        }
        else{
            sceneRoot2.removeView(textView1);
            vis3 = false;
        }
    }
}