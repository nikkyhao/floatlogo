package com.example.floatlogo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


public class FloatView extends ImageView {
    //��ͼ�õ�
    private Paint mypaint ;
    private int myx = 0;
    private int myy = 0;
    
    
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float mStartY;
    private OnClickListener mClickListener;
    private WindowManager windowManager = (WindowManager) getContext()
	    .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // ��windowManagerParams����Ϊ��ȡ��ȫ�ֱ��������Ա����������ڵ�����
    private WindowManager.LayoutParams windowManagerParams = ((FloatApplication) getContext()
	    .getApplicationContext()).getWindowParams();

    public FloatView(Context context) {
	super(context);
	mypaint = new Paint();
	new Timer().schedule(new TimerTask() {
	    @Override
	    public void run() {

		mhander.sendEmptyMessage(0x123);
	    }
	},0, 100);
    }
    
    Handler mhander = new Handler(){
	@Override
	public void handleMessage(Message msg){
	    if(msg.what == 0x123){
		myx++; myy++;
		invalidate();
	    }
	}
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	// ��ȡ��״̬���ĸ߶�
	Rect frame = new Rect();
	getWindowVisibleDisplayFrame(frame);
	int statusBarHeight = frame.top;
	System.out.println("statusBarHeight:" + statusBarHeight);
	// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
	x = event.getRawX();
	y = event.getRawY() - statusBarHeight; // statusBarHeight��ϵͳ״̬���ĸ߶�
	Log.i("tag", "currX" + x + "====currY" + y);
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN: // ������ָ�������¶���
	    // ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
	    mTouchX = event.getX();
	    mTouchY = event.getY();
	    mStartX = x;
	    mStartY = y;
	    Log.i("tag", "startX" + mTouchX + "====startY" + mTouchY);
	    break;
	case MotionEvent.ACTION_MOVE: // ������ָ�����ƶ�����
	    updateViewPosition();
	    break;
	case MotionEvent.ACTION_UP: // ������ָ�����뿪����
	    updateViewPosition();
	    mTouchX = mTouchY = 0;
	    if ((x - mStartX) < 5 && (y - mStartY) < 5) {
		if (mClickListener != null) {
		    mClickListener.onClick(this);
		}
	    }
	    break;
	}
	return true;
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
	this.mClickListener = l;
    }

    private void updateViewPosition() {
	// ���¸�������λ�ò���
	windowManagerParams.x = (int) (x - mTouchX);
	windowManagerParams.y = (int) (y - mTouchY);
	windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
	invalidate();
    }

    int i = 1;
    @Override
    protected void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	super.onDraw(canvas);
	Log.d("onDraw", "onDraw������"+i+"��ִ��"); i++;
	mypaint.setColor(Color.RED);
	canvas.drawRect(myx, myy, getWidth()/2, getHeight()/2, mypaint);  
    }

}