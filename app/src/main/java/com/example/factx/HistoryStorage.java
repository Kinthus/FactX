
package com.example.factx;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private static final String PREF_NAME = "FactXHistory";
    private static final String HISTORY_KEY = "history_";
    private static final String TAG = "HistoryStorage";

    // Save a history entry for the logged-in user
    public static void saveHistory(
            Context context,
            String email,
            String type,
            String news,
            String explanation,
            String result,
            String confidence,
            String date) {

        if (email == null || email.trim().isEmpty()) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME, Context.MODE_PRIVATE);

        String key = HISTORY_KEY + email.trim().toLowerCase();

        try {
            JSONArray historyArray = new JSONArray(
                    preferences.getString(key, "[]"));

            JSONObject item = new JSONObject();
            item.put("type", type);
            item.put("news", news);
            item.put("explanation", explanation);
            item.put("result", result);
            item.put("confidence", confidence);
            item.put("date", date);

            historyArray.put(item);

            preferences.edit()
                    .putString(key, historyArray.toString())
                    .apply();

        } catch (Exception e) {
            Log.e(TAG, "Unable to save history", e);
        }
    }

    // Load history entries for the logged-in user
    public static List<HistoryAdapter.HistoryItem> getHistory(
            Context context,
            String email) {

        List<HistoryAdapter.HistoryItem> historyList =
                new ArrayList<>();

        if (email == null || email.trim().isEmpty()) {
            return historyList;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME, Context.MODE_PRIVATE);

        String key = HISTORY_KEY + email.trim().toLowerCase();

        try {
            JSONArray historyArray = new JSONArray(
                    preferences.getString(key, "[]"));

            for (int i = historyArray.length() - 1; i >= 0; i--) {
                JSONObject item = historyArray.getJSONObject(i);

                historyList.add(
                        new HistoryAdapter.HistoryItem(
                                item.optString("type"),
                                item.optString("news"),
                                item.optString("explanation"),
                                item.optString("result"),
                                item.optString("confidence"),
                                item.optString("date")
                        )
                );
            }

        } catch (Exception e) {
            Log.e(TAG, "Unable to load history", e);
        }

        return historyList;
    }

    // Delete selected history entries
    public static void deleteSelectedHistory(
            Context context,
            String email,
            List<HistoryAdapter.HistoryItem> selectedItems) {

        if (email == null || email.trim().isEmpty()
                || selectedItems == null || selectedItems.isEmpty()) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME, Context.MODE_PRIVATE);

        String key = HISTORY_KEY + email.trim().toLowerCase();

        try {
            JSONArray oldArray = new JSONArray(
                    preferences.getString(key, "[]"));

            JSONArray newArray = new JSONArray();

            for (int i = 0; i < oldArray.length(); i++) {
                JSONObject saved = oldArray.getJSONObject(i);
                boolean shouldDelete = false;

                for (HistoryAdapter.HistoryItem selected : selectedItems) {
                    if (saved.optString("type").equals(selected.getType())
                            && saved.optString("news").equals(selected.getNews())
                            && saved.optString("explanation").equals(selected.getExplanation())
                            && saved.optString("result").equals(selected.getResult())
                            && saved.optString("confidence").equals(selected.getConfidence())
                            && saved.optString("date").equals(selected.getDate())) {

                        shouldDelete = true;
                        break;
                    }
                }

                if (!shouldDelete) {
                    newArray.put(saved);
                }
            }

            preferences.edit()
                    .putString(key, newArray.toString())
                    .apply();

        } catch (Exception e) {
            Log.e(TAG, "Unable to delete selected history", e);
        }
    }

    // Clear all history for one user
    public static void clearHistory(Context context, String email) {

        if (email == null || email.trim().isEmpty()) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME, Context.MODE_PRIVATE);

        String key = HISTORY_KEY + email.trim().toLowerCase();

        preferences.edit()
                .remove(key)
                .apply();
    }
}
