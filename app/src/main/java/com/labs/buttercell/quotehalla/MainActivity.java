package com.labs.buttercell.quotehalla;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joaquimley.faboptions.FabOptions;
import com.labs.buttercell.quotehalla.model.Quote;
import com.labs.buttercell.quotehalla.retrofit.ApiClient;
import com.labs.buttercell.quotehalla.retrofit.ApiInterface;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    FabOptions fabOptions;
    ImageView img_filter;
    TextView txtQuote, txtAuthor;

    private List<Integer> list = new ArrayList<Integer>();

    private LovelySaveStateHandler saveStateHandler;

    private static final String TAG = "MainActivity";


    Call<List<Quote>> call;

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

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        call = apiService.getQuotes();

        getQuote();

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog(savedInstanceState);
                call.cancel();
                call.clone().enqueue(new Callback<List<Quote>>() {
                    @Override
                    public void onResponse(final Call<List<Quote>> call, Response<List<Quote>> response) {
                        final List<Quote> quotes = response.body();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                int rand = (int) (Math.random() * 99 + 1);
                                //looping is every 1 secs

                                list.add(rand);

                                txtAuthor.setText(quotes.get(rand).getAuthor());
                                txtQuote.setText(quotes.get(rand).getQuote());

                                Log.d(TAG, "List size " + list.size());
                                if (list.size() == 10) {
                                    list.clear();


                                }
                                handler.postDelayed(this, 1000);
                            }
                        }, 0); //initial delay of 0


                    }

                    @Override
                    public void onFailure(Call<List<Quote>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
            }
        });


    }

    private void getQuote() {

        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(final Call<List<Quote>> call, Response<List<Quote>> response) {
                final List<Quote> quotes = response.body();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        int rand = (int) (Math.random() * 99 + 1);
                        //looping is every 1 secs

                        list.add(rand);

                        txtAuthor.setText(quotes.get(rand).getAuthor());
                        txtQuote.setText(quotes.get(rand).getQuote());

                        Log.d(TAG, "List size " + list.size());
                        if (list.size() == 10) {
                            list.clear();


                        }
                        handler.postDelayed(this, 1000);
                    }
                }, 0); //initial delay of 0


            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
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

        img_filter = findViewById(R.id.filter);
        saveStateHandler = new LovelySaveStateHandler();
        txtAuthor = findViewById(R.id.txt_author);
        txtQuote = findViewById(R.id.txt_quote);
    }
}
