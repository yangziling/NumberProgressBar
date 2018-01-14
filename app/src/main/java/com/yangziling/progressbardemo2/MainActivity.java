package com.yangziling.progressbardemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yangziling.progressbardemo2.numberprogressbar.OnProgressBarListener;
import com.yangziling.progressbardemo2.numberprogressbar.NumberProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnProgressBarListener, View.OnClickListener{

    private ImageView mCancle_image,mImage_background, mRefresh_image, mBack_image;
    private NumberProgressBar mNumberProgressBar;
    private Timer mTimer;

    /*private ProgressBar mPb_button;
    private TextView mPb_textView;
    private ImageView mPb_image;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mPb_image.setBackground(getResources().getDrawable(R.drawable.girl));
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化控件

        //注释的代码放开，并将xml布局中对应的代码放开
        // 实现普通的百分数进度条
        /*mPb_button = (ProgressBar) findViewById(R.id.pb_button);
        mPb_textView = (TextView) findViewById(R.id.tvpb);
        mPb_image = (ImageView) findViewById(R.id.pb_image);

        new Thread(){
            @Override
            public void run() {
                int i =0;
                while (i<=100){
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j =i;
                    mPb_button.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPb_textView.setText(j+"%");
                        }
                    });
                }
                if (i>100) {
                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();*/
    }

    private void initView() {
        mCancle_image = (ImageView) findViewById(R.id.cancle_image);
        mImage_background = (ImageView) findViewById(R.id.image_background);
        mRefresh_image = (ImageView) findViewById(R.id.refresh_image);
        mBack_image = (ImageView) findViewById(R.id.back_image);

        mCancle_image.setOnClickListener(this);
        mRefresh_image.setOnClickListener(this);
        mBack_image.setOnClickListener(this);

        mNumberProgressBar = (NumberProgressBar)findViewById(R.id.numberbar);
        mNumberProgressBar.setOnProgressBarListener(this);
        mTimer = new Timer();//计时器初始化
        startTimer();//执行定时任务
    }

    //定时任务开始执行
    private void startTimer() {
        /**
         * 执行延迟2秒钟，每0.1秒执行一次
         */
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNumberProgressBar.incrementProgressBy(1);
                    }
                });
            }
        },2000,100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle_image:   //点击返回按钮
                mTimer.cancel();   //取消计时
                mNumberProgressBar.setProgress(0);
                mNumberProgressBar.setVisibility(View.INVISIBLE);
                mImage_background.setVisibility(View.INVISIBLE);

                mCancle_image.setVisibility(View.INVISIBLE);
                mRefresh_image.setVisibility(View.VISIBLE);
                break;
            case R.id.refresh_image:    //点击刷新按钮
                mCancle_image.setVisibility(View.VISIBLE);
                mRefresh_image.setVisibility(View.INVISIBLE);
                mTimer = new Timer();

                mNumberProgressBar.setProgress(0);
                mNumberProgressBar.setVisibility(View.VISIBLE);
                mImage_background.setVisibility(View.INVISIBLE);
                startTimer();
                break;
            case R.id.back_image:   //点击结束按钮
                finish();
                break;
        }
    }

        /**
         * 进度条的监听
         * @param current
         * @param max
         */
        @Override
        public void onProgressChange ( int current, int max){
            //如果进度条加载到100%
            if (current == max) {
                mTimer.cancel();//任务取消
                mNumberProgressBar.setProgress(0);
                mNumberProgressBar.setVisibility(View.INVISIBLE);

                mImage_background.setVisibility(View.VISIBLE);
                mCancle_image.setVisibility(View.INVISIBLE);
                mRefresh_image.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            mTimer.cancel();//结束任务
        }
}
