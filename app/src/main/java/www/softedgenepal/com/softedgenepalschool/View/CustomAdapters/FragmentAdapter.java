package www.softedgenepal.com.softedgenepalschool.View.CustomAdapters;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;


public class FragmentAdapter {
    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager supportFragmentManager;

    private Fragment[] fragments;
    private String[] titles;
    private int[] drawableImage;

    private FragmentPagerAdapter fragmentPagerAdapter;

    public FragmentAdapter(Activity activity, TabLayout tabLayout, ViewPager viewPager, FragmentManager supportFragmentManager,
                           Fragment[] fragments, String[] titles, int[] drawableImage) {
        this.activity=activity;
        this.tabLayout=tabLayout;
        this.viewPager=viewPager;
        this.supportFragmentManager=supportFragmentManager;

        this.fragments=fragments;
        this.titles=titles;
        this.drawableImage=drawableImage;
    }

    public void setTablayout(final boolean toShowText) {
        fragmentPagerAdapter = new FragmentPagerAdapter(supportFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if(toShowText){
                    return titles[position];
                }else {
                    return null;
                }

            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setIcons(){
        //todo to display icons in tab layout
        for(int i=0; i<tabLayout.getTabCount();i++){
            tabLayout.getTabAt(i).setIcon(drawableImage[i]);
        }
    }

    public void disableSwipe(){
        //todo disable viewpager swipe
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //use this to swipe manually
        //viewPager.setCurrentItem(int index);
    }
}
