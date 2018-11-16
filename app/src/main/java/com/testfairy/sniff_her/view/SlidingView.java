package com.testfairy.sniff_her.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testfairy.sniff_her.R;
import com.testfairy.sniff_her.entity.Dog;
import com.testfairy.sniff_her.entity.Owner;
import com.testfairy.sniff_her.utility.ObjectUtil;
import com.testfairy.sniff_her.utility.StringUtil;

import java.util.Date;

public class SlidingView extends FrameLayout {

    public interface OnSlideListener {
        void onLeftSlide();
        void onRightSlide();
    }

    private interface ResetSlide {
        void reset();
    }

    public SlidingView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private OnSlideListener slideListener;
    private ResetSlide resetSlide;

    private ImageView slideRoot;
    private TextView ownerName;
    private TextView ownerHobies;
    private TextView ownerFavoriteMovie;
    private TextView dogGender;

    public SlidingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SlidingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(@NonNull Context context) {
        View inflate = inflate(context, R.layout.sliding_view, this);

        ownerName = inflate.findViewById(R.id.owner_name);
        ownerHobies = inflate.findViewById(R.id.owner_hobbies);
        ownerFavoriteMovie = inflate.findViewById(R.id.owner_fav_movie);
        dogGender = inflate.findViewById(R.id.dog_gender);
        slideRoot = inflate.findViewById(R.id.slide);

        slideRoot.setDrawingCacheEnabled(false);

        slideRoot.setOnTouchListener(new OnTouchListener() {
            private boolean moving = false;

            private float rootStartViewX = 0;
            private float slideRootInitialX = 0;
            private float slideRootInitialY = 0;
            private int touchStartViewX = 0;
            private int touchStartViewY = 0;

            private int[] lastKnownPosition = new int[2];
            private int[] currentPosition = new int[2];
            private float currentAngle = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                if (moving && event.getAction() != MotionEvent.ACTION_UP) {
                    lastKnownPosition[0] = currentPosition[0];
                    lastKnownPosition[1] = currentPosition[1];
                    currentPosition[0] = touchStartViewX + x;
                    currentPosition[1] = touchStartViewY + y;
                    currentAngle = (rootStartViewX  +  x) / 15f;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slideRootInitialX = slideRoot.getX();
                        slideRootInitialY = slideRoot.getY();
                        touchStartViewX = (int) slideRoot.getX() - x;
                        touchStartViewY = (int) slideRoot.getY() - y;
                        rootStartViewX = getX() - x;

                        lastKnownPosition = new int[2];
                        currentPosition = new int[2];
                        currentAngle = 0f;

                        moving = true;

                        resetSlide = new ResetSlide() {
                            @Override
                            public void reset() {
                                setSlidePosition(slideRootInitialX, slideRootInitialY);
                                setSlideRotation(0);

                                moving = false;
                            }
                        };
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (moving) {
//                            Log.d("Last Position", "" + lastKnownPosition[0] + " , " + lastKnownPosition[1]);
//                            Log.d("Current Position", "" + currentPosition[0] + " , " + currentPosition[1]);

                            setSlidePosition(currentPosition[0], currentPosition[1]);
                            setSlideRotation(currentAngle);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                        Log.d("Last Position", "" + lastKnownPosition[0] + " , " + lastKnownPosition[1]);
//                        Log.d("Current Position", "" + currentPosition[0] + " , " + currentPosition[1]);

                        if (slideListener != null) {
                            if (currentAngle > 10) {
//                                Log.d("Slide", "right");

                                slideListener.onRightSlide();
                            } else if (currentAngle < -10) {
//                                Log.d("Slide", "left");

                                slideListener.onLeftSlide();
                            }
                        }

                        reset();
                        break;
                }

                return true;
            }
        });
    }

    private void setSlidePosition(float x, float y) {
        slideRoot.setX(x);
        slideRoot.setY(y);
    }

    private void setSlideRotation(float angle) {
//        Log.d("Angle", "" + angle);

        slideRoot.setRotation(angle);
    }

    public void setOnSlideListener(@Nullable OnSlideListener listener) {
        this.slideListener = listener;
    }

    public void setDetails(@NonNull Dog dog, @NonNull Owner owner) {
        ObjectUtil.assertNotNull(dog, owner);

        ownerName.setText(String.format("%s%s", getContext().getString(R.string.owner_name_static), owner.getName()));
        ownerHobies.setText(String.format("%s%s", getContext().getString(R.string.hobbies_static), StringUtil.join(owner.getHobies(), ", ")));
        ownerFavoriteMovie.setText(String.format("%s%s", getContext().getString(R.string.favorite_movie_static), owner.getFavoriteMovie()));
        dogGender.setText(String.format("%s%s", getContext().getString(R.string.dog_gender_static), dog.getGender().toString()));

        Glide.with(getContext())
                .load(dog.getPicture())
                .into(slideRoot);
    }

    public void reset() {
        if (resetSlide != null) {
            resetSlide.reset();
            resetSlide = null;
        }
    }

}
