package com.yeahgenius.hy.gloryboom.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.adapter.FitnessKnowledgeViewPagerAdapter;
import com.yeahgenius.hy.gloryboom.fragment.FitnessTopicFragment;
import com.yeahgenius.hy.gloryboom.fragment.GainMuscleFragment;
import com.yeahgenius.hy.gloryboom.fragment.LoseWeightAndGainMuscleFragment;
import com.yeahgenius.hy.gloryboom.fragment.LoseWeightFragment;
import com.yeahgenius.hy.gloryboom.fragment.RelaxFragment;
import com.yeahgenius.hy.gloryboom.fragment.SelfFitnessFragment;
import com.yeahgenius.hy.gloryboom.fragment.SportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.navgation_view_main) NavigationView navigationView;
    @BindView(R.id.drawer_layout_main) DrawerLayout drawer;
    @BindView(R.id.tabLayout_main) TabLayout tabLayout;
    @BindView(R.id.viewpager_main) ViewPager viewpager;
    private List<Fragment> fragments;
    private String[] titles;
    private BottomBar bottomBar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTabLayout();
        initDrawer();
        initNavHeader();
        initBottomBar(savedInstanceState);
    }

    private void initNavHeader()
    {
        View headerView = navigationView.getHeaderView(0);
        linearLayout = (LinearLayout) headerView.findViewById(R.id.ll_main_activity_nav_head);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                jumpTo(LoginActivity.class, false);
            }
        });
    }

    private void initBottomBar(Bundle savedInstanceState)
    {
        bottomBar = BottomBar.attach(findViewById(R.id.myScrollingContent_main), savedInstanceState);
        bottomBar.useDarkTheme();
        bottomBar.setItems(R.menu.bottombar_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    return;
                }
                else if(menuItemId == R.id.bottomBarItemTwo)
                {
                    jumpTo(PedometerActivity.class, false);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

    }

    private void initTabLayout()
    {
        fragments = new ArrayList<Fragment>();
        fragments.add(new FitnessTopicFragment());
        fragments.add(new LoseWeightAndGainMuscleFragment());
        fragments.add(new GainMuscleFragment());
        fragments.add(new LoseWeightFragment());
        fragments.add(new RelaxFragment());
        fragments.add(new SelfFitnessFragment());
        fragments.add(new SportFragment());
        titles = getResources().getStringArray(R.array.tab_title);
        viewpager.setAdapter(new FitnessKnowledgeViewPagerAdapter(getSupportFragmentManager(), titles, fragments));
        tabLayout.setupWithViewPager(viewpager);
    }

    private void initDrawer()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_signIn) {
            jumpTo(LoginActivity.class, false);
        } else if (id == R.id.nav_favorite) {
            jumpTo(FavPageActivity.class, false);
        } else if (id == R.id.nav_contactUs) {

        } else if (id == R.id.nav_donate) {

        } else if (id == R.id.nav_share) {

        }else if (id == R.id.nav_exit) {
            System.exit(0);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
