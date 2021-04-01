package io.github.leassets.internal.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Excel view model for the fixed-asset-acquisition entity
 */
public class FixedAssetAcquisitionEVM implements ExcelViewModel {

    @ExcelRow
    private Long rowIndex;

    @ExcelCell(0)
    private Long assetNumber;

    @ExcelCell(1)
    private String serviceOutletCode;

    @ExcelCell(2)
    private String assetTag;

    @ExcelCell(3)
    private String assetDescription;

    @ExcelCell(4)
    private String purchaseDate;

    @ExcelCell(5)
    private String assetCategory;

    @ExcelCell(6)
    private double purchasePrice;

    private String fileUploadToken;

    public FixedAssetAcquisitionEVM() {
    }

    public FixedAssetAcquisitionEVM(Long rowIndex, Long assetNumber, String serviceOutletCode, String assetTag, String assetDescription, String purchaseDate, String assetCategory, double purchasePrice, String fileUploadToken) {
        this.rowIndex = rowIndex;
        this.assetNumber = assetNumber;
        this.serviceOutletCode = serviceOutletCode;
        this.assetTag = assetTag;
        this.assetDescription = assetDescription;
        this.purchaseDate = purchaseDate;
        this.assetCategory = assetCategory;
        this.purchasePrice = purchasePrice;
        this.fileUploadToken = fileUploadToken;
    }

    @Override
    public Object getModelData() {
        return this;
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public FixedAssetAcquisitionEVM setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public Long getAssetNumber() {
        return assetNumber;
    }

    public FixedAssetAcquisitionEVM setAssetNumber(Long assetNumber) {
        this.assetNumber = assetNumber;
        return this;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public FixedAssetAcquisitionEVM setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public FixedAssetAcquisitionEVM setAssetTag(String assetTag) {
        this.assetTag = assetTag;
        return this;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public FixedAssetAcquisitionEVM setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public FixedAssetAcquisitionEVM setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public FixedAssetAcquisitionEVM setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
        return this;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public FixedAssetAcquisitionEVM setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public String getFileUploadToken() {
        return fileUploadToken;
    }

    public FixedAssetAcquisitionEVM setFileUploadToken(String fileUploadToken) {
        this.fileUploadToken = fileUploadToken;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FixedAssetAcquisitionEVM.class.getSimpleName() + "[", "]")
            .add("rowIndex=" + rowIndex)
            .add("assetNumber=" + assetNumber)
            .add("serviceOutletCode='" + serviceOutletCode + "'")
            .add("assetTag='" + assetTag + "'")
            .add("assetDescription='" + assetDescription + "'")
            .add("purchaseDate='" + purchaseDate + "'")
            .add("assetCategory='" + assetCategory + "'")
            .add("purchasePrice=" + purchasePrice)
            .add("fileUploadToken='" + fileUploadToken + "'")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedAssetAcquisitionEVM)) return false;
        FixedAssetAcquisitionEVM that = (FixedAssetAcquisitionEVM) o;
        return Double.compare(that.getPurchasePrice(), getPurchasePrice()) == 0 && Objects.equals(getRowIndex(), that.getRowIndex()) && Objects.equals(getAssetNumber(), that.getAssetNumber()) && Objects.equals(getServiceOutletCode(), that.getServiceOutletCode()) && Objects.equals(getAssetTag(), that.getAssetTag()) && Objects.equals(getAssetDescription(), that.getAssetDescription()) && Objects.equals(getPurchaseDate(), that.getPurchaseDate()) && Objects.equals(getAssetCategory(), that.getAssetCategory()) && Objects.equals(getFileUploadToken(), that.getFileUploadToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowIndex(), getAssetNumber(), getServiceOutletCode(), getAssetTag(), getAssetDescription(), getPurchaseDate(), getAssetCategory(), getPurchasePrice(), getFileUploadToken());
    }
}
