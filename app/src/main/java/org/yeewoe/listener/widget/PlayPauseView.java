package org.yeewoe.listener.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import org.yeewoe.listener.R;
import org.yeewoe.listener.util.ATEUtil;


public class PlayPauseView extends FrameLayout {

    private static final Property<PlayPauseView, Integer> COLOR = new Property<PlayPauseView, Integer>(Integer.class, "color") {
        @Override
        public Integer get(PlayPauseView v) {
            return v.getCircleColor();
        }

        @Override
        public void set(PlayPauseView v, Integer value) {
            v.setCircleColor(value);
        }
    };

    private static final long PLAY_PAUSE_ANIMATION_DURATION = 200;

    private final PlayPauseDrawable mDrawable;
    private final Paint mPaint = new Paint();
    private int mDrawableColor;
    public boolean isDrawCircle;
    public int circleAlpha;

    private AnimatorSet mAnimatorSet;
    private int mBackgroundColor;
    private int mWidth;
    private int mHeight;

    public PlayPauseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlayPause);
        isDrawCircle = typedArray.getBoolean(R.styleable.PlayPause_isCircleDraw, true);
        circleAlpha = typedArray.getInt(R.styleable.PlayPause_circleAlpha, 255);
        mBackgroundColor = ATEUtil.getThemeAccentColor(context);
        mDrawableColor = ATEUtil.getThemeAccentColor(context);
        typedArray.recycle();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(circleAlpha);
        mPaint.setColor(mBackgroundColor);
        mDrawable = new PlayPauseDrawable(context, mDrawableColor);
        mDrawable.setCallback(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // final int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
        mWidth = w;
        mHeight = h;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            setClipToOutline(true);
        }
    }

    public void setCircleColor(@ColorInt int color) {
        mBackgroundColor = color;
        invalidate();
    }

    public void setDrawableColor(@ColorInt int color) {
        mDrawableColor = color;
        mDrawable.setDrawableColor(color);
        invalidate();
    }

    public void setCircleAlpah(int alpah) {
        circleAlpha = alpah;
        invalidate();
    }

    private int getCircleColor() {
        return mBackgroundColor;
    }

    public int getDrawableColor() {
        return mDrawableColor;
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mBackgroundColor);
        final float radius = Math.min(mWidth, mHeight) / 2f;
        if (isDrawCircle) {
            mPaint.setColor(mBackgroundColor);
            mPaint.setAlpha(circleAlpha);
            canvas.drawCircle(mWidth / 2f, mHeight / 2f, radius, mPaint);
        }
        mDrawable.draw(canvas);
    }

    private boolean mIsPlay;

    public boolean isPlay() {
        return mIsPlay;
    }

    //此时为待暂停标识
    public void Play() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        mAnimatorSet = new AnimatorSet();
        mIsPlay = true;
        mDrawable.setmIsPlay(mIsPlay);
        final Animator pausePlayAnim = mDrawable.getPausePlayAnimator();
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        pausePlayAnim.start();
    }

    //此时为为待播放标识
    public void Pause() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        mAnimatorSet = new AnimatorSet();
        mIsPlay = false;
        mDrawable.setmIsPlay(mIsPlay);
        final Animator pausePlayAnim = mDrawable.getPausePlayAnimator();
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.setDuration(PLAY_PAUSE_ANIMATION_DURATION);
        pausePlayAnim.start();
    }

}
