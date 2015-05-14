package com.it.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener{
	//创建变量
	private ViewPager viewpager;
	//字体列表
	private List<TextView> list;
	//创建一个线程，定时翻页
	private Thread thread;
	//handler
	private Handler mhandler;
	//初始位置
	private int Current=Integer.MAX_VALUE/2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		list = new ArrayList<TextView>();
		initlist();
		viewpager.setAdapter(new Mypageradapter(list));
		viewpager.setOnPageChangeListener(this);
		viewpager.setCurrentItem(Current);
		initthread();
		thread.start();
	}


	private void initthread() {
		mhandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
					viewpager.setCurrentItem(msg.what);
				super.handleMessage(msg);
			}
		};
		//自动翻页线程，
		thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//创建定时器
				Timer t=new Timer();
				//定时器的定时任务
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//handler的发送的MSG.what就是ViewPager的位置
						mhandler.sendEmptyMessage(Current+1);
					}
					//定时器间隔时间
				}, 0, 1000);
			}
		});
	}


	private void initlist() {
		int color[]={Color.BLUE,Color.GREEN,Color.YELLOW,Color.WHITE,Color.GRAY};
		for (int i = 0; i < 5; i++) {
			TextView v = new TextView(MainActivity.this);
			v.setWidth(getWindowManager().getDefaultDisplay().getWidth());
			v.setHeight(getWindowManager().getDefaultDisplay().getHeight());
			v.setBackgroundColor(color[i]);
			v.setText("页面" + (i + 1));
			v.setTextColor(0xFFFF0000);
			v.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
			list.add(v);
		}
	}

	class Mypageradapter extends PagerAdapter {
		private List<TextView> list;

		public Mypageradapter(List<TextView> list) {
			// TODO Auto-generated constructor stub
			this.list=list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//设置长度为整形最大值
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			//获取需要加载视图的父控件
			ViewParent parent = list.get(position%list.size()).getParent();
			//判断父控件是否为空，若为空说明已被ViewPager加载，那么我们要移除改视图，这样就能保证一个视图只有一个父控件，
			if(parent!=null){
				viewpager.removeView(list.get(position%list.size()));
			}
			container.addView(list.get(position%list.size()));
			return list.get(position%list.size());
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			viewpager.removeView(list.get(position%list.size()));
//			container.getChildAt(position)=null;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(final int arg0) {
		// TODO Auto-generated method stub
		Current=arg0;
		System.out.println("Current"+Current);
	}

}