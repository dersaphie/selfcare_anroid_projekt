package com.example.myroutine;

import android.content.Intent;


public class Utils {
    private static int sTheme;
    //defining theme cases, each gets an integer value with which they can be recognized later
    public final static int THEME_BasicTheme = 0;
    public final static int THEME_RoseTheme = 1;
    public final static int THEME_SunnyTheme = 2;
    public final static int THEME_ForestTheme = 3;

    //changes to Theme with associated int value, and gets said value from setupSpinnerItemSelection() in MainActivity
    public static void changeToTheme(MainActivity activity, int theme) {
      //stopping and restarting MainActivity with animation
      sTheme = theme;
      activity.finish();
      activity.startActivity(new Intent(activity, activity.getClass()));
      activity.overridePendingTransition(android.R.anim.fade_in,
        android.R.anim.fade_out);
    }
    //calling setTheme in MainActivity and passing the associated Value 0, 1, 2 or 3
    public static void onActivityCreateSetTheme(MainActivity activity) {
      switch (sTheme) {
        default:
        case THEME_BasicTheme:
          activity.setTheme(R.style.Theme_Basic_Theme);
          break;
        case THEME_RoseTheme:
          activity.setTheme(R.style.Theme_RoseTheme);
          break;
        case THEME_SunnyTheme:
          activity.setTheme(R.style.Theme_SunnyTheme);
          break;
        case THEME_ForestTheme:
          activity.setTheme(R.style.Theme_ForestTheme);
          break;
      }
    }
  }

