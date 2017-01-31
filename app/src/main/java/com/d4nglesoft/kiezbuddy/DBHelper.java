package com.d4nglesoft.kiezbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chewbacca on 24.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;

    private static final String DATABASE_NAME = "KiBuVo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String VOUCHER_TABLE_NAME = "vouchers";
    public static final String VOUCHER_COLUMN_ID = "id";
    public static final String VOUCHER_COLUMN_LOCATION_NAME = "location_name";
    public static final String VOUCHER_COLUMN_DEAL_DESCRIPTION = "deal_description";
    public static final String VOUCHER_COLUMN_VOUCHER_VALUE = "voucher_value"; //3.00 € / 25% / 2for1
    public static final String VOUCHER_COLUMN_GEO_LOCATION = "geo_location";
    public static final String VOUCHER_COLUMN_BENEFITS = "benefits";
    public static final String VOUCHER_COLUMN_VALIDITY_DATE = "validity_date";
    public static final String VOUCHER_COLUMN_PACKAGE_ID = "package_id";
    public static final String VOUCHER_COLUMN_FAVORITE = "favorite";
    public static final String VOUCHER_COLUMN_LOCKED = "locked";


    public static final String VOUCHER_COLUMN_REDEEMED = "redeemed";


    private static final String VOUCHER_TABLE_CREATE =
            "CREATE TABLE " + VOUCHER_TABLE_NAME + " (" +
                    VOUCHER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    VOUCHER_COLUMN_LOCATION_NAME + " TEXT, " +
                    VOUCHER_COLUMN_DEAL_DESCRIPTION + " TEXT, " +
                    VOUCHER_COLUMN_VOUCHER_VALUE + " TEXT, " +
                    VOUCHER_COLUMN_GEO_LOCATION + " TEXT, " +
                    VOUCHER_COLUMN_BENEFITS + " TEXT, " +
                    VOUCHER_COLUMN_VALIDITY_DATE + " TEXT, " +
                    VOUCHER_COLUMN_PACKAGE_ID + " TEXT, " +
                    VOUCHER_COLUMN_FAVORITE + " INTEGER, " +
                    VOUCHER_COLUMN_LOCKED + " INTEGER, " +
                    VOUCHER_COLUMN_REDEEMED + " INTEGER" +
                    ");";


    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VOUCHER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VOUCHER_TABLE_NAME);
        onCreate(db);
    }

    public boolean redeemVoucher(int voucherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.VOUCHER_COLUMN_REDEEMED, "1");

        db.update(VOUCHER_TABLE_NAME,
                contentValues,
                VOUCHER_COLUMN_ID + "= ?",
                new String[]{Integer.toString(voucherId)});

        return true;
    }

    public boolean unlockVoucherPackage(int packageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.VOUCHER_COLUMN_LOCKED, "0");

        db.update(VOUCHER_TABLE_NAME,
                contentValues,
                VOUCHER_COLUMN_PACKAGE_ID + "= ?",
                new String[]{Integer.toString(packageId)});
        return true;
    }

    public VoucherDetailsModel getVoucherData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from vouchers where id=" + id + "", null);
        VoucherDetailsModel model;

        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            model = getVoucherModelFromCursor(cursor);
            return model;
        }

        return null;
    }

    public List<VoucherDetailsModel> getAllVouchers() {
        List<VoucherDetailsModel> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from vouchers", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            results.add(getVoucherModelFromCursor(cursor));
        }

        return results;
    }

    @NonNull
    private VoucherDetailsModel getVoucherModelFromCursor(Cursor cursor) {

        VoucherDetailsModel voucherDetailsModel = new VoucherDetailsModel(
                cursor.getInt(cursor.getColumnIndex(VOUCHER_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_LOCATION_NAME)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_DEAL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_VOUCHER_VALUE)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_GEO_LOCATION)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_BENEFITS)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_VALIDITY_DATE)),
                cursor.getString(cursor.getColumnIndex(VOUCHER_COLUMN_PACKAGE_ID)),
                cursor.getInt(cursor.getColumnIndex(VOUCHER_COLUMN_FAVORITE)),
                cursor.getInt(cursor.getColumnIndex(VOUCHER_COLUMN_LOCKED)),
                cursor.getInt(cursor.getColumnIndex(VOUCHER_COLUMN_REDEEMED)));

        return voucherDetailsModel;
    }
}
