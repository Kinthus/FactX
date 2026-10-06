package com.example.factx;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class activity_History extends AppCompatActivity {

    // ==========================================
    // VIEWS
    // ==========================================

    RecyclerView recyclerHistory;

    Button btnHistoryMenu;

    Button btnDeleteSelected;


    // ==========================================
    // HISTORY
    // ==========================================

    HistoryAdapter historyAdapter;

    List<HistoryAdapter.HistoryItem> historyList;


    // ==========================================
    // USER EMAIL
    // ==========================================

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);


        // ==========================================
        // GET LOGGED-IN USER EMAIL
        // ==========================================

        email =
                getIntent().getStringExtra("email");


        // ==========================================
        // FIND VIEWS
        // ==========================================

        recyclerHistory =
                findViewById(R.id.recyclerHistory);

        btnHistoryMenu =
                findViewById(R.id.btnHistoryMenu);

        btnDeleteSelected =
                findViewById(R.id.btnDeleteSelected);


        // ==========================================
        // SET RECYCLER VIEW
        // ==========================================

        recyclerHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );


        // ==========================================
        // LOAD HISTORY
        // ==========================================

        loadHistory();


        // ==========================================
        // THREE DOT MENU
        // ==========================================

        btnHistoryMenu.setOnClickListener(v -> {

            showHistoryMenu();

        });


        // ==========================================
        // DELETE SELECTED
        // ==========================================

        btnDeleteSelected.setOnClickListener(v -> {

            deleteSelectedHistory();

        });
    }


    // ==========================================
    // LOAD HISTORY
    // ==========================================

    private void loadHistory() {

        historyList =
                HistoryStorage.getHistory(
                        this,
                        email
                );


        historyAdapter =
                new HistoryAdapter(
                        historyList
                );


        recyclerHistory.setAdapter(
                historyAdapter
        );
    }


    // ==========================================
    // SHOW THREE DOT MENU
    // ==========================================

    private void showHistoryMenu() {

        PopupMenu popupMenu =
                new PopupMenu(
                        activity_History.this,
                        btnHistoryMenu
                );


        popupMenu.getMenu().add(
                Menu.NONE,
                1,
                Menu.NONE,
                "Delete"
        );


        popupMenu.setOnMenuItemClickListener(
                item -> {

                    if (item.getItemId() == 1) {

                        enableDeleteMode();

                        return true;
                    }

                    return false;
                }
        );


        popupMenu.show();
    }


    // ==========================================
    // ENABLE DELETE MODE
    // ==========================================

    private void enableDeleteMode() {

        // Show Delete Selected button

        btnDeleteSelected.setVisibility(
                View.VISIBLE
        );


        // Show checkboxes

        historyAdapter.setDeleteMode(
                true
        );


        Toast.makeText(
                this,
                "Select the news you want to delete",
                Toast.LENGTH_SHORT
        ).show();
    }


    // ==========================================
    // DELETE SELECTED HISTORY
    // ==========================================

    private void deleteSelectedHistory() {

        List<HistoryAdapter.HistoryItem> selectedItems =
                new ArrayList<>();


        // ==========================================
        // FIND SELECTED ITEMS
        // ==========================================

        for (
                HistoryAdapter.HistoryItem item
                : historyList
        ) {

            if (item.isSelected()) {

                selectedItems.add(item);
            }
        }


        // ==========================================
        // NOTHING SELECTED
        // ==========================================

        if (selectedItems.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please select at least one news",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // ==========================================
        // DELETE SELECTED ITEMS
        // ==========================================

        HistoryStorage.deleteSelectedHistory(
                this,
                email,
                selectedItems
        );


        // ==========================================
        // RELOAD HISTORY
        // ==========================================

        loadHistory();


        // ==========================================
        // HIDE DELETE BUTTON
        // ==========================================

        btnDeleteSelected.setVisibility(
                View.GONE
        );


        // ==========================================
        // SUCCESS MESSAGE
        // ==========================================

        Toast.makeText(
                this,
                "Selected news deleted",
                Toast.LENGTH_SHORT
        ).show();
    }


    // ==========================================
    // BACK BUTTON
    // ==========================================

    @Override
    public void onBackPressed() {

        if (
                historyAdapter != null
                        && historyAdapter.isDeleteMode()
        ) {

            historyAdapter.setDeleteMode(
                    false
            );

            btnDeleteSelected.setVisibility(
                    View.GONE
            );

            Toast.makeText(
                    this,
                    "Delete mode cancelled",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            super.onBackPressed();
        }
    }
}