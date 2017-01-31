package com.d4nglesoft.kiezbuddy;

import android.content.DialogInterface;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import static com.d4nglesoft.kiezbuddy.R.id.voucher_details_deal_description;
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

        TextView voucher_details_deal_description =
                (TextView) findViewById(R.id.voucher_details_deal_description);

        TextView voucher_details_location_name =
                (TextView) findViewById(R.id.voucher_details_location_name);

        int mod = (voucherId -1) % 7;
        switch (mod) {
            case 1:
                voucher_details_image.setImageResource(R.drawable.bar1);
                voucher_details_deal_description.setText("Zwei Bier zum Preis von einem");
                voucher_details_location_name.setText("Prinzenbar");
                break;
            case 2:
                voucher_details_image.setImageResource(R.drawable.bar2);
                voucher_details_deal_description.setText("1 Pizza gratis, beim Kauf von zwei weiteren zum vollen Preis");
                voucher_details_location_name.setText("Kiezgrill");
                break;
            case 3:
                voucher_details_image.setImageResource(R.drawable.bar3);
                voucher_details_deal_description.setText("Besonderer Genuss + 15 Minuten");
                voucher_details_location_name.setText("Pauli-Eck 69");
                break;
            case 4:
                voucher_details_image.setImageResource(R.drawable.bar4);
                voucher_details_deal_description.setText("Große Pommes zum Preis der kleinen - nur Dienstags und Donnerstags");
                voucher_details_location_name.setText("Kiezcurry - der einzig wahre Grill auf deinem Kiez");
                break;
            case 5:
                voucher_details_image.setImageResource(R.drawable.bar5);
                voucher_details_deal_description.setText("Big Mac aus der Tonne");
                voucher_details_location_name.setText("Burger King");
                break;
            case 6:
                voucher_details_image.setImageResource(R.drawable.bar6);
                voucher_details_deal_description.setText("24 Stunden gratis Aufenthalt");
                voucher_details_location_name.setText("Davidwache");
                break;
            case 0:
                voucher_details_image.setImageResource(R.drawable.bar7);
                voucher_details_deal_description.setText("Gratis Rundgang - Ladies Only");
                voucher_details_location_name.setText("Herbertstraße");
                break;
        }


        FloatingActionButton redeemVoucherButton =
                (FloatingActionButton) findViewById(R.id.redeemVoucherButton);

        redeemVoucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(voucherId, voucher_details_image);
//                dbHelper.redeemVoucher(voucherId);
//                disableVoucher(voucher_details_image);
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

        String[]
//                s = {"TV","KICKER","BILLARD","DART","SOFA","BAND","DJ","RAUCHER","ESSEN","HAPPYHOUR","COCKTAILS","SNACKS","LGBT","TIERFREUNDE","BARRIEREFREI"};
                s = {"TV","Kicker","Billard","Dart","Sofa","Band","DJ","Raucher","Essen","HappyHour","Cocktails","Snacks","LGBT","Tierfreunde","Barrierefrei"};
        TagLayout tagLayout = (TagLayout) findViewById(R.id.benefits_container);
        LayoutInflater layoutInflater = getLayoutInflater();
        String tag;
        for (int i = 0; i <= s.length - 1; i++) {
//        for (int i = 0; i <= s.length - 7; i++) {
            tag = s[i];
            View tagView = layoutInflater.inflate(R.layout.tag_layout, null, false);

            TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            tagLayout.addView(tagView);
        }

    }

    public void showDialog(final int voucherId, final ImageView voucher_details_image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gutschein einlösen");
        builder.setMessage("Dieser Gutschein wird verbraucht. Du kannst ihn danach nicht mehr nutzen!");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.redeemVoucher(voucherId);
                        disableVoucher(voucher_details_image);
                        dialog.dismiss();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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
