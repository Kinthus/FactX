package com.example.factx;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    private static final String PREF_NAME = "FactXHistory";

    private static final String HISTORY_KEY = "history_";


    // ==========================================
    // SAVE HISTORY
    // ==========================================

    public static void saveHistory(
            Context context,
            String email,
            String type,
            String news,
            String explanation,
            String result,
            String confidence,
            String date
    ) {

        if (email == null || email.isEmpty()) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );

        String key = HISTORY_KEY + email;

        String existingData =
                preferences.getString(key, "[]");

        try {

            JSONArray historyArray =
                    new JSONArray(existingData);

            JSONObject historyObject =
                    new JSONObject();

            historyObject.put(
                    "type",
                    type
            );

            historyObject.put(
                    "news",
                    news
            );

            historyObject.put(
                    "explanation",
                    explanation
            );

            historyObject.put(
                    "result",
                    result
            );

            historyObject.put(
                    "confidence",
                    confidence
            );

            historyObject.put(
                    "date",
                    date
            );

            historyArray.put(historyObject);

            preferences.edit()
                    .putString(
                            key,
                            historyArray.toString()
                    )
                    .apply();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // LOAD USER HISTORY
    // ==========================================

    public static List<HistoryAdapter.HistoryItem> getHistory(
            Context context,
            String email
    ) {

        List<HistoryAdapter.HistoryItem> historyList =
                new ArrayList<>();

        if (email == null || email.isEmpty()) {
            return historyList;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );

        String key =
                HISTORY_KEY + email;

        String existingData =
                preferences.getString(key, "[]");

        try {

            JSONArray historyArray =
                    new JSONArray(existingData);

            for (
                    int i = 0;
                    i < historyArray.length();
                    i++
            ) {

                JSONObject historyObject =
                        historyArray.getJSONObject(i);


                String type =
                        historyObject.optString(
                                "type"
                        );


                String news =
                        historyObject.optString(
                                "news"
                        );


                String explanation =
                        historyObject.optString(
                                "explanation"
                        );


                String result =
                        historyObject.optString(
                                "result"
                        );


                String confidence =
                        historyObject.optString(
                                "confidence"
                        );


                String date =
                        historyObject.optString(
                                "date"
                        );


                historyList.add(
                        new HistoryAdapter.HistoryItem(
                                type,
                                news,
                                explanation,
                                result,
                                confidence,
                                date
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return historyList;
    }


    // ==========================================
    // DELETE SELECTED HISTORY
    // ==========================================

    public static void deleteSelectedHistory(
            Context context,
            String email,
            List<HistoryAdapter.HistoryItem> selectedItems
    ) {

        if (
                email == null
                        || email.isEmpty()
                        || selectedItems == null
                        || selectedItems.isEmpty()
        ) {
            return;
        }


        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );


        String key =
                HISTORY_KEY + email;


        String existingData =
                preferences.getString(key, "[]");


        try {

            JSONArray oldHistoryArray =
                    new JSONArray(existingData);


            JSONArray newHistoryArray =
                    new JSONArray();


            // ==========================================
            // CHECK EACH SAVED HISTORY
            // ==========================================

            for (
                    int i = 0;
                    i < oldHistoryArray.length();
                    i++
            ) {

                JSONObject historyObject =
                        oldHistoryArray.getJSONObject(i);


                String type =
                        historyObject.optString(
                                "type"
                        );


                String news =
                        historyObject.optString(
                                "news"
                        );


                String explanation =
                        historyObject.optString(
                                "explanation"
                        );


                String result =
                        historyObject.optString(
                                "result"
                        );


                String confidence =
                        historyObject.optString(
                                "confidence"
                        );


                String date =
                        historyObject.optString(
                                "date"
                        );


                boolean shouldDelete = false;


                // ==========================================
                // COMPARE WITH SELECTED ITEMS
                // ==========================================

                for (
                        HistoryAdapter.HistoryItem selectedItem
                        : selectedItems
                ) {

                    if (
                            type.equals(
                                    selectedItem.getType()
                            )
                                    && news.equals(
                                    selectedItem.getNews()
                            )
                                    && explanation.equals(
                                    selectedItem.getExplanation()
                            )
                                    && result.equals(
                                    selectedItem.getResult()
                            )
                                    && confidence.equals(
                                    selectedItem.getConfidence()
                            )
                                    && date.equals(
                                    selectedItem.getDate()
                            )
                    ) {

                        shouldDelete = true;

                        break;
                    }
                }


                // ==========================================
                // KEEP NON-SELECTED HISTORY
                // ==========================================

                if (!shouldDelete) {

                    newHistoryArray.put(
                            historyObject
                    );
                }
            }


            // ==========================================
            // SAVE UPDATED HISTORY
            // ==========================================

            preferences.edit()
                    .putString(
                            key,
                            newHistoryArray.toString()
                    )
                    .apply();


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // DELETE ALL HISTORY
    // ==========================================
    // Not used by the current UI.
    // Kept here only if needed in the future.

    public static void clearHistory(
            Context context,
            String email
    ) {

        if (email == null || email.isEmpty()) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );

        String key =
                HISTORY_KEY + email;

        preferences.edit()
                .remove(key)
                .apply();
    }
}