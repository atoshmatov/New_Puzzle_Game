package uz.gita.newPuzzle.mobdev.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultShared {
    private SharedPreferences pref;
    private static ResultShared resultShared;

    private ResultShared(Context context) {
        pref = context.getSharedPreferences("RESULT", Context.MODE_PRIVATE);
    }

    public static ResultShared getInstance(Context context) {
        if (resultShared == null) {
            resultShared = new ResultShared(context);
        }
        return resultShared;
    }

    public void saveResult(int count) {
        List<Integer> list = new ArrayList<>();
        list.add(count);
        list.add(getFirst());
        list.add(getSecond());
        list.add(getThird());
        Collections.sort(list);
        saveFirst(list.get(0));
        saveSecond(list.get(1));
        saveThird(list.get(2));
    }

    public int getFirst() {
        return pref.getInt("FIRST", Integer.MAX_VALUE);
    }

    public boolean getBol() {
        return pref.getBoolean("dsds", true);
    }

    public int getSecond() {
        return pref.getInt("SECOND", Integer.MAX_VALUE);
    }

    public int getThird() {
        return pref.getInt("THIRD", Integer.MAX_VALUE);
    }

    private void saveFirst(int count) {
        pref.edit().putInt("FIRST", count).apply();
    }

    private void saveSecond(int count) {
        pref.edit().putInt("SECOND", count).apply();
    }

    private void saveThird(int count) {
        pref.edit().putInt("THIRD", count).apply();
    }
}
