package ir.ali_gharegozlu.gcurrency;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import ir.ali_gharegozlu.gcurrency.model.CurrencyObject;
import ir.ali_gharegozlu.gcurrency.model.CurrencyObject_;

public class MainActivity extends AppCompatActivity {

    List<CurrencyObject> currencyObjectList = new ArrayList<>();
    List<CurrencyObject> coinObjectList = new ArrayList<>();

    String apiKey = getString(R.string.apikey);
    RelativeLayout relativeLayout;

    String TAG = "g-currency";

    TextView lastUpdatedTV;

    CurrencyItemAdapter itemAdapter;
    CoinItemAdapter coinItemAdapter;

    SimpleDateFormat formatDate;
    SimpleDateFormat formatTime;
    String updatedDate;
    public String updatedTime;
    String lastUpdatedDay;
    private String updatedDateTime;
    boolean isAppFirstLaunch = true;
    Box<CurrencyObject> currencyObjectBox;
    SharedPreferences.Editor preferences;
    String PREFS_NAME = "g_currency_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relativeLayout = findViewById(R.id.mainRootLayout);
        lastUpdatedTV = findViewById(R.id.lastUpdatedTV);

        ObjectBox.init(getApplicationContext());
        BoxStore boxStore = ObjectBox.get();
        currencyObjectBox = boxStore.boxFor(CurrencyObject.class);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();


        //try {
        //currencyObjectList.clear();
        currencyObjectList = currencyObjectBox.query().equal(CurrencyObject_.isCoin, false).build().find();
        coinObjectList = currencyObjectBox.query().equal(CurrencyObject_.isCoin, true).build().find();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        lastUpdatedTV.setText(prefs.getString("last_updated_key", ""));
        try {
            lastUpdatedDay = prefs.getString("last_updated_key", "").substring(4, 10);
            if(prefs.contains("last_updated_key"))
                isAppFirstLaunch = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        itemAdapter = new CurrencyItemAdapter(this, currencyObjectList);
        final RecyclerView currencyRecyclerView = findViewById(R.id.currencyRV);
        currencyRecyclerView.setAdapter(itemAdapter);

        coinItemAdapter = new CoinItemAdapter(this, coinObjectList);
        final RecyclerView coinRecyclerView = findViewById(R.id.coinsRV);
        coinRecyclerView.setAdapter(coinItemAdapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        coinRecyclerView.setLayoutManager(layoutManager);


        if (true/*!lastUpdatedDay.equals(setDateTime().substring(4, 10)) || isAppFirstLaunch*/) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get("http://api.apimaster.ir/" + apiKey + "/nerkh/v1/list", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();

                    Log.d(TAG, "onStart called");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    MainActivity.this.onSuccess(response, currencyRecyclerView, coinRecyclerView);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

                    Snackbar.make(relativeLayout, "دریافت اطلاعات با خطا مواجه شد", Snackbar.LENGTH_SHORT);
                    Log.d(TAG, "onFailure status code: " + statusCode);
                    Toast.makeText(MainActivity.this, "ارتباط با سرور با خطا مواجه شد. لطفا دوباره تلاش نمایید.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fillCurrencyList(JSONObject response) {
        String name = "";
        String abbr = "";
        String price = "";
        int size = 15;
        try {
            size = response.getJSONArray("data").length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <= size - 1; i++) {

            try {
                name = response.getJSONArray("data").getJSONObject(i).getString("name");
                abbr = response.getJSONArray("data").getJSONObject(i).getString("slug");
                price = response.getJSONArray("data").getJSONObject(i).getString("price");
            } catch (JSONException e) {
                e.printStackTrace();
                name = "_";
                abbr = "_";
                price = "_";
            }
            CurrencyObject toAddObject = new CurrencyObject(name, abbr, price,true);
            switch (abbr) {
                case "gold_gerami":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_rob":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_sekee_emami":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_nim":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_seke_bahar":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_geram24":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_geram18":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_mesghal":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                case "gold_ons":
                    if (!coinObjectList.contains(toAddObject)) coinObjectList.add(toAddObject);
                default:
                    if (!coinObjectList.contains(toAddObject)) currencyObjectList.add(new CurrencyObject(name,abbr,price,false));
            }
        }
    }

    private String setDateTime(){
        formatDate = new SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault());
        formatTime = new SimpleDateFormat("h:mm a", Locale.getDefault());

        updatedDate = formatDate.format(new Date());
        updatedTime = formatTime.format(new Date());

        return updatedDateTime = updatedDate + "  " + updatedTime;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(id==R.id.action_refresh){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        currencyObjectBox.closeThreadResources();
    }

    void onSuccess(JSONObject response, RecyclerView currencyRecyclerView, RecyclerView coinRecyclerView){
        currencyObjectList.clear();
        coinObjectList.clear();

        Log.d(TAG,response.toString());

        fillCurrencyList(response);

        itemAdapter = new CurrencyItemAdapter(MainActivity.this,currencyObjectList);
        currencyRecyclerView.setAdapter(itemAdapter);
        coinItemAdapter = new CoinItemAdapter(MainActivity.this,coinObjectList);
        coinRecyclerView.setAdapter(coinItemAdapter);

        lastUpdatedTV.setText(setDateTime() + " :آخرین آپدیت");

        Toast.makeText(MainActivity.this, "قیمت ها به روزرسانی شد", Toast.LENGTH_SHORT).show();
        currencyObjectBox.removeAll();
        currencyObjectBox.put(currencyObjectList);
        currencyObjectBox.put(coinObjectList);

        preferences.putString("last_updated_key",lastUpdatedTV.getText().toString());
        preferences.apply();
    }
}
