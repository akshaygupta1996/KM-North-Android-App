package connect.shopping.akshay.kmnorth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import connect.shopping.akshay.kmnorth.adaptor.HorizontalMenuAdaptor;

/**
 * Created by Akshay on 18-07-2017.
 */

public class CenterLockHorizontalScrollview extends HorizontalScrollView {
    Context context;
    int prevIndex = 0;

    public CenterLockHorizontalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setSmoothScrollingEnabled(true);

    }

    public void setAdapter(Context context, HorizontalMenuAdaptor mAdapter) {

        try {
            fillViewWithAdapter(mAdapter);
        } catch (ZeroChildException e) {

            e.printStackTrace();
        }
    }

    private void fillViewWithAdapter(HorizontalMenuAdaptor mAdapter)
            throws ZeroChildException {




        if (getChildCount() == 0) {
            throw new ZeroChildException(
                    "CenterLockHorizontalScrollView must have one child");
        }
        if (getChildCount() == 0 || mAdapter == null)
            return;

        ViewGroup parent = (ViewGroup) getChildAt(0);

        parent.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            parent.addView(mAdapter.getView(i, null, parent));
        }
    }

    public void setCenter(int index) {

        ViewGroup parent = (ViewGroup) getChildAt(0);

        View preView = parent.getChildAt(prevIndex);
        preView.setBackgroundColor(Color.parseColor("#64CBD8"));
        android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        preView.setLayoutParams(lp);

        View view = parent.getChildAt(index);
        view.setBackgroundColor(Color.RED);

        int screenWidth = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth();

        int scrollX = (view.getLeft() - (screenWidth / 2))
                + (view.getWidth() / 2);
        this.smoothScrollTo(scrollX, 0);
        prevIndex = index;
    }

}