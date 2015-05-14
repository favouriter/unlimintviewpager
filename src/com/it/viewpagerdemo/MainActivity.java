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
	//��������
	private ViewPager viewpager;
	//�����б�
	private List<TextView> list;
	//����һ���̣߳���ʱ��ҳ
	private Thread thread;
	//handler
	private Handler mhandler;
	//��ʼλ��
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
		//�Զ���ҳ�̣߳�
		thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//������ʱ��
				Timer t=new Timer();
				//��ʱ���Ķ�ʱ����
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//handler�ķ��͵�MSG.what����ViewPager��λ��
						mhandler.sendEmptyMessage(Current+1);
					}
					//��ʱ�����ʱ��
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
			v.setText("ҳ��" + (i + 1));
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
			//���ó���Ϊ�������ֵ
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
			//��ȡ��Ҫ������ͼ�ĸ��ؼ�
			ViewParent parent = list.get(position%list.size()).getParent();
			//�жϸ��ؼ��Ƿ�Ϊ�գ���Ϊ��˵���ѱ�ViewPager���أ���ô����Ҫ�Ƴ�����ͼ���������ܱ�֤һ����ͼֻ��һ�����ؼ���
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