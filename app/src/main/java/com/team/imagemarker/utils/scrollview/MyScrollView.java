package com.team.imagemarker.utils.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 带滚动监听的ScrollView
 * Created by Lmy on 2017/4/2.
 * email 1434117404@qq.com
 */

public class MyScrollView extends ScrollView {

	public interface ScrollViewListener {
		void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
	}

	private ScrollViewListener scrollViewListener = null;

	public MyScrollView(Context context) {
		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
}