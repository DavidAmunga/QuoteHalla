package com.labs.buttercell.quotehalla;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.joaquimley.faboptions.FabOptions;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    FabOptions fabOptions;
    ImageView img_filter;

    private LovelySaveStateHandler saveStateHandler;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-MediumItalic.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_welcome);

        setContentView(R.layout.activity_main);

        initViews();







        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog(savedInstanceState);
            }
        });


    }
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        if (LovelySaveStateHandler.wasDialogOnScreen(savedState)) {
            //Dialog won't be restarted automatically, so we need to call this method.
            //Each dialog knows how to restore its state
            displayDialog(savedState);
        }
    }

    private void displayDialog(Bundle savedState) {
        String[] items = getResources().getStringArray(R.array.food);
        new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
                .setTopColorRes(R.color.colorPrimaryDark)
                .setTitle("What are your tastes")
                .setIcon(R.drawable.ic_quote_icon)
                .setInstanceStateHandler(1, saveStateHandler)
                .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        Toast.makeText(MainActivity.this, items.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setConfirmButtonText("Ok")
                .setSavedInstanceState(savedState)
                .show();

    }

    private void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fabOptions = findViewById(R.id.fab_options);
        fabOptions.setButtonsMenu(R.menu.menu_main);

        img_filter=findViewById(R.id.filter);
        saveStateHandler = new LovelySaveStateHandler();
    }
}
