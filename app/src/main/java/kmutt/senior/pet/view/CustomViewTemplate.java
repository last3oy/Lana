package kmutt.senior.pet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class CustomViewTemplate extends View {

    public CustomViewTemplate(Context context) {
        super(context);
        init();
    }

    public CustomViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs);
    }

    public CustomViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
