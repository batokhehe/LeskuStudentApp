package com.lescepat.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.lescepat.R;
import com.lescepat.manager.ConfigManager;
import com.lescepat.manager.HawkManager;
import com.lescepat.views.activities.auth.LoginActivity;
import com.lescepat.views.activities.others.AboutUsActivity;
import com.lescepat.views.activities.others.AccountActivity;
import com.lescepat.views.activities.others.PrivacyPolicyActivity;
import com.lescepat.views.fragments.history.HistoryFragment;
import com.lescepat.views.fragments.main.AccountFragment;
import com.lescepat.views.fragments.main.HomeFragment;
import com.lescepat.views.fragments.order.OrderFragment;
import com.lescepat.views.fragments.schedule.ScheduleFragment;
import com.lescepat.views.fragments.transaction.OrderClassFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OrderClassFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private String userName, userEmail, userImg, userBalance;
    private ImageView ivUserImg, ivHeaderImg;
    private TextView tvUserName, tvUserEmail, tvUserBalance;
    private byte[] decodedString;
    HawkManager hawkManager;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = ConfigManager.BASE_URL_IMAGE + "/sample_background.jpg";

    // index to identify current nav menu item
    public static int navItemIndex = 1;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_ORDER = "order";
    private static final String TAG_SCHEDULE = "schedule";
    private static final String TAG_HISTORY = "history";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    public static Context contextOfApplication;
    private Bundle savedInstanceState;
    private Button btnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState; 

        contextOfApplication = getApplicationContext();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnAccount = (Button) toolbar.findViewById(R.id.btn_login_home);

        setSupportActionBar(toolbar);

        mHandler = new Handler();
        hawkManager = new HawkManager();

        if(hawkManager.getAppUserToken() != null){
            btnAccount.setVisibility(View.GONE);

            userName = hawkManager.getAppUserName();
            userEmail = hawkManager.getAppUserEmail();
            userBalance = hawkManager.getAppUserBalance();
            userImg = hawkManager.getAppUserImg();

            decodedString = Base64.decode(userImg, Base64.DEFAULT);

            //Navigation view Header
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationView = (NavigationView) findViewById(R.id.nav_view);

            View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            tvUserName = (TextView) hView.findViewById(R.id.tv_user_name);
            tvUserEmail = (TextView) hView.findViewById(R.id.tv_user_email);
            tvUserBalance = (TextView) hView.findViewById(R.id.tv_user_balance);
            ivUserImg = (ImageView) hView.findViewById(R.id.iv_profile_image);
            ivHeaderImg = (ImageView) hView.findViewById(R.id.img_header_bg);

            // load toolbar titles from string resources
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //Load nav menu header data
            loadNavHeader();

            //initializing navigation menu
            setUpNavigationView();

            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                Toast.makeText(this, "Go To Order : " + extras.getInt("gotoOrder", 0), Toast.LENGTH_SHORT).show();
                int gotoOrder = extras.getInt("gotoOrder", 0);
                if(gotoOrder == 1){
                    navItemIndex = 2;
                    CURRENT_TAG = TAG_ORDER;
                    loadHomeFragment();
                }
            }
            if (savedInstanceState == null && extras == null) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
            }
        } else {
            btnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            Runnable mPendingRunnable = new Runnable() {
                @Override
                public void run() {
                    Fragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            };

            // If mPendingRunnable is not null, then add to the message queue
            if (mPendingRunnable != null) {
                mHandler.post(mPendingRunnable);
            }
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);
        tvUserBalance.setText(userBalance);

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivHeaderImg);

        // Loading profile image
        Glide.with(this)
                .asBitmap()
                .load(decodedString)
                .apply(new RequestOptions().circleCrop())
                .thumbnail(0.5f)
                .into(ivUserImg);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_account:
                        startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_home:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_order:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_ORDER;
                        break;
                    case R.id.nav_schedule:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SCHEDULE;
                        break;
                    case R.id.nav_history:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_HISTORY;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_logout:
                        hawkManager.destroyAppData();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        drawer.closeDrawers();
                        finish();
                        return true;
                    default:
                        navItemIndex = 1;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        navigationView.removeHeaderView(navigationView.getHeaderView(0));


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // photos
                AccountFragment accountFragment = new AccountFragment();
                return accountFragment;
            case 1:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 2:
                // order fragment
                OrderFragment orderFragment = new OrderFragment();
                return orderFragment;
            case 3:
                // schedule fragment
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                return scheduleFragment;
            case 4:
                // history fragment
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            default:
                HomeFragment homeFragmentDefault = new HomeFragment();
                return homeFragmentDefault;
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 1) {
                navItemIndex = 1;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
//        if (navItemIndex == 1) {
//            getMenuInflater().inflate(R.menu.main, menu);
//        }

        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // Handle the camera action
        }

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_schedule) {

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_privacy_policy) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // show or hide the fab
    private void toggleFab() {
//        if (navItemIndex == 1)
//            addAssembly.show();
//        else
//            addAssembly.hide();
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(contextOfApplication, "Activity Resumed", Toast.LENGTH_SHORT).show();
        if(hawkManager.getAppUserToken() != null){
            // Loading profile image
            Glide.with(this)
                    .asBitmap()
                    .load(decodedString)
                    .apply(new RequestOptions().circleCrop())
                    .thumbnail(0.5f)
                    .into(ivUserImg);
        }
    }
}
