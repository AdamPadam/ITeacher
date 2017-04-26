package com.itecher.adampadam.itecher;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.itecher.adampadam.itecher.practice.PracticeActivity;

public class MainActivity extends ActivityGroup {

    public static boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        setTitle("TabHost");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup(this.getLocalActivityManager());

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("profile");

        tabSpec.setContent(new Intent(this, ProfileActivity.class));
        tabSpec.setIndicator("", res.getDrawable(R.mipmap.ic_account_light));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("dict");
        tabSpec.setContent(new Intent(this, DictActivity.class));
        tabSpec.setIndicator("", res.getDrawable(R.mipmap.ic_book_open_variant_light));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("card");
        tabSpec.setContent(new Intent(this, CardActivity.class));
        tabSpec.setIndicator("", res.getDrawable(R.mipmap.ic_cards_light));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("practice");
        tabSpec.setContent(new Intent(this, PracticeActivity.class));
        tabSpec.setIndicator("", res.getDrawable(R.mipmap.ic_google_translate_light));
        tabHost.addTab(tabSpec);

        if (isBack) {

            tabHost.setCurrentTab(3);
            isBack = false;

        } else {

            tabHost.setCurrentTab(0);
            tabHost.setCurrentTab(1);
            tabHost.setCurrentTab(3);
            tabHost.setCurrentTab(2);

        }

    }

}
