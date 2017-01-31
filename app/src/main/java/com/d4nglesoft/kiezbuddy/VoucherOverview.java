package com.d4nglesoft.kiezbuddy;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.d4nglesoft.kiezbuddy.R.attr.dividerHorizontal;
import static com.d4nglesoft.kiezbuddy.R.attr.layoutManager;
import static com.d4nglesoft.kiezbuddy.R.id.redeemVoucherButton;
import static com.d4nglesoft.kiezbuddy.R.id.voucher_details_image;
import static com.d4nglesoft.kiezbuddy.R.id.voucher_details_toolbar;
import static com.d4nglesoft.kiezbuddy.R.id.voucher_overview_toolbar;

public class VoucherOverview extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_overview);

        Toolbar toolbar = (Toolbar) findViewById(voucher_overview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fabSearch =
                (FloatingActionButton) findViewById(R.id.fabSearch);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Searching...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // to improve performance
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        populateDB();

        registerForContextMenu(mRecyclerView);

        mAdapter = new CardviewDisplayAdapter(getBaseContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void populateDB() {
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long counter = DatabaseUtils.queryNumEntries(db, DBHelper.VOUCHER_TABLE_NAME);
        System.out.println("Currently the DB has " + counter + " entries.");

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.VOUCHER_COLUMN_LOCATION_NAME,counter + "VOUCHER_COLUMN_LOCATION_NAME");
        contentValues.put(DBHelper.VOUCHER_COLUMN_DEAL_DESCRIPTION,counter + "VOUCHER_COLUMN_DEAL_DESCRIPTION");
        contentValues.put(DBHelper.VOUCHER_COLUMN_VOUCHER_VALUE,counter + "VOUCHER_COLUMN_VOUCHER_VALUE");
        contentValues.put(DBHelper.VOUCHER_COLUMN_GEO_LOCATION,counter + "VOUCHER_COLUMN_GEO_LOCATION");
        contentValues.put(DBHelper.VOUCHER_COLUMN_BENEFITS,counter + "VOUCHER_COLUMN_BENEFITS");

        contentValues.put(DBHelper.VOUCHER_COLUMN_VALIDITY_DATE,counter + "VOUCHER_COLUMN_VALIDITY_DATE");
        contentValues.put(DBHelper.VOUCHER_COLUMN_PACKAGE_ID,counter + "VOUCHER_COLUMN_PACKAGE_ID");
        contentValues.put(DBHelper.VOUCHER_COLUMN_FAVORITE,""+ counter % 2);
        contentValues.put(DBHelper.VOUCHER_COLUMN_LOCKED,""+ counter % 2);
        contentValues.put(DBHelper.VOUCHER_COLUMN_REDEEMED,""+ counter % 2);

        db.insert(DBHelper.VOUCHER_TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        Toast toast = Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT);

        switch (id) {
            case android.R.id.home:
                toast.setText("Finishing...");
                finish();
                break;
            case R.id.menu_favorite:
                toast.setText("Favorite...");
                break;
            case R.id.menu_share:
                toast.setText("Sharing...");
                break;
            case R.id.details_menu_action_navigate:
                toast.setText("Navigating...");
                break;
            case R.id.details_menu_action_call:
                toast.setText("Calling...");
                break;
            case R.id.menu_complain:
                toast.setText("Complaining...");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        toast.show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voucher_overview, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                    Log.e("D4NGLE", "onMenuOpened", e);
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Toast toast = Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT);

        switch (id) {
            case R.id.menu_purchase:
                toast.setText("Buying...");
                break;
            case R.id.menu_filter:
                toast.setText("Filtering...");
                break;
            case R.id.menu_settings:
                toast.setText("Setting...");
                break;
            case R.id.menu_complain:
                toast.setText("Complaining...");
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        toast.show();
        return true;
    }

}
