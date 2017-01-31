package com.d4nglesoft.kiezbuddy;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

import static android.R.attr.duration;
import static com.d4nglesoft.kiezbuddy.R.id.beginning;
import static com.d4nglesoft.kiezbuddy.R.id.redeemVoucherButton;
import static com.d4nglesoft.kiezbuddy.R.id.voucher_details_toolbar;

public class VoucherDetailsView extends AppCompatActivity {

    DBHelper dbHelper;
    VoucherDetailsModel voucher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dbHelper = DBHelper.getInstance(getApplicationContext());
        final int voucherId = getIntent().getIntExtra("VoucherId", -1);
        voucher = dbHelper.getVoucherData(voucherId);

        setContentView(R.layout.activity_voucher_details_under);

        final ImageView voucher_details_image =
                (ImageView) findViewById(R.id.voucher_details_image);
        FloatingActionButton redeemVoucherButton =
                (FloatingActionButton) findViewById(R.id.redeemVoucherButton);

        redeemVoucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.redeemVoucher(voucherId);
                disableVoucher(voucher_details_image);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(voucher_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (voucher.getIsLocked() != 0 || voucher.getIsRedeemed() != 0) {
//            disableVoucher(voucher_details_image);

            voucher_details_image.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }


        setStatusBarTranslucent(true);

    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void disableVoucher(ImageView voucher_details_image) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        voucher_details_image.setColorFilter(filter);

        if (voucher.getIsLocked() != 0) {
            findViewById(R.id.voucher_details_image_locked).setVisibility(View.VISIBLE);
        }
        if (voucher.getIsRedeemed() != 0) {
            findViewById(R.id.voucher_details_image_locked).setVisibility(View.GONE);
            findViewById(R.id.voucher_details_image_used).setVisibility(View.VISIBLE);
        }
        //TODO Fance Animation
        findViewById(R.id.redeemVoucherButton).setVisibility(View.GONE);
        findViewById(R.id.purchaseMoreButton).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voucher_details, menu);
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

    private int getFavoriteDrawable() {
        if (voucher.getIsFavorite() != 0) {
            return R.drawable.ic_menu_favorite;
        } else {
            return R.drawable.ic_menu_no_favorite;
        }
    }

    private String getDisplayDistance(String geoLocation) {
        // TODO
        return geoLocation;
    }

}
