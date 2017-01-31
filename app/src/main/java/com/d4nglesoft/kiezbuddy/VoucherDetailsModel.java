package com.d4nglesoft.kiezbuddy;

/**
 * Created by Chewbacca on 24.01.2017.
 */
public class VoucherDetailsModel {
    private int id;
    private String locationName;
    private String dealDescription;
    private String voucherValue; //TODO Paketpreis muss höher sein als der Wert eines einzelnen Vouchers
    private String geoLocation;
    private String benefits;

    private String validityDate;
    private String packageId;
    private int isFavorite; //0 false, 1 true
    private int isLocked; //0 false, 1 true
    private int isRedeemed; //0 false, 1 true

    public VoucherDetailsModel(int id, String locationName, String dealDescription,
                               String voucherValue, String geoLocation, String benefits,
                               String validityDate, String packageId,
                               int isFavorite, int isLocked, int isRedeemed) {
        this.id = id;
        this.locationName = locationName;
        this.dealDescription = dealDescription;
        this.voucherValue = voucherValue;
        this.geoLocation = geoLocation;
        this.benefits = benefits;
        this.validityDate = validityDate;
        this.packageId = packageId;
        this.isFavorite = isFavorite;
        this.isLocked = isLocked;
        this.isRedeemed = isRedeemed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public String getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(String voucherValue) {
        this.voucherValue = voucherValue;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public int getIsRedeemed() {
        return isRedeemed;
    }

    public void setIsRedeemed(int isRedeemed) {
        this.isRedeemed = isRedeemed;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    @Override
    public String toString() {
        return
                "\n this.id = " + id +
                        "\n this.locationName = " + locationName +
                        "\n this.dealDescription = " + dealDescription +
                        "\n this.voucherValue = " + voucherValue +
                        "\n this.geoLocation = " + geoLocation +
                        "\n this.benefits = " + benefits +
                        "\n this.validityDate = " + validityDate +
                        "\n this.packageId = " + packageId +
                        "\n this.isLocked = " + isLocked +
                        "\n this.isRedeemed = " + isRedeemed +
                        "\n --- --- --- ---";
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

}
