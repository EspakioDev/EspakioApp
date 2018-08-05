package com.development.espakio.appespakio.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.development.espakio.appespakio.R;


public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnAtras, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnAtras = (Button) findViewById(R.id.btn_skip);
        btnSiguiente = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.slider_1,
                R.layout.slider_2,
                R.layout.slider_3
        };

        addBottomDots(0);

        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.addOnPageChangeListener(this);

        btnAtras.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void fullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        /*int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);*/

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.GrisBajo));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.Blanco));
    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);

        if (position == 0){
            btnAtras.setEnabled(false);
            btnSiguiente.setEnabled(true);
            btnAtras.setVisibility(View.INVISIBLE);

            btnSiguiente.setText("Siguiente");
            btnAtras.setText("");

        } else if(position == layouts.length - 1){
            btnAtras.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnAtras.setVisibility(View.VISIBLE);

            btnSiguiente.setText("Terminar");
            btnAtras.setText("Atras");

        }else {
            btnAtras.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnAtras.setVisibility(View.VISIBLE);

            btnSiguiente.setText("Siguiente");
            btnAtras.setText("Atras");

        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    startActivity(new Intent(WelcomeActivity.this, SplashScreen2.class));
                    finish();
                }
                break;
            case R.id.btn_skip:
                int iCurrent = getItem(-1);
                if (iCurrent < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(iCurrent);
                }
                break;
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}

