package tech.leassets.internal.model;

/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Excel view model for the fixed-asset-net-book-value entity
 */
public class FixedAssetNetBookValueEVM implements Serializable {

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
    private String netBookValueDate;

    @ExcelCell(5)
    private String assetCategory;

    @ExcelCell(6)
    private double netBookValue;

    @ExcelCell(7)
    private String depreciationRegime;

    private String fileUploadToken;

    private String compilationToken;

    public FixedAssetNetBookValueEVM() {}

    public FixedAssetNetBookValueEVM(
        Long rowIndex,
        Long assetNumber,
        String serviceOutletCode,
        String assetTag,
        String assetDescription,
        String netBookValueDate,
        String assetCategory,
        double netBookValue,
        String depreciationRegime,
        String fileUploadToken,
        String compilationToken
    ) {
        this.rowIndex = rowIndex;
        this.assetNumber = assetNumber;
        this.serviceOutletCode = serviceOutletCode;
        this.assetTag = assetTag;
        this.assetDescription = assetDescription;
        this.netBookValueDate = netBookValueDate;
        this.assetCategory = assetCategory;
        this.netBookValue = netBookValue;
        this.depreciationRegime = depreciationRegime;
        this.fileUploadToken = fileUploadToken;
        this.compilationToken = compilationToken;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FixedAssetNetBookValueEVM.class.getSimpleName() + "[", "]")
            .add("rowIndex=" + rowIndex)
            .add("assetNumber=" + assetNumber)
            .add("serviceOutletCode='" + serviceOutletCode + "'")
            .add("assetTag='" + assetTag + "'")
            .add("assetDescription='" + assetDescription + "'")
            .add("netBookValueDate='" + netBookValueDate + "'")
            .add("assetCategory='" + assetCategory + "'")
            .add("netBookValue=" + netBookValue)
            .add("depreciationRegime='" + depreciationRegime + "'")
            .add("fileUploadToken='" + fileUploadToken + "'")
            .add("compilationToken='" + compilationToken + "'")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedAssetNetBookValueEVM)) return false;
        FixedAssetNetBookValueEVM that = (FixedAssetNetBookValueEVM) o;
        return (
            Double.compare(that.getNetBookValue(), getNetBookValue()) == 0 &&
            Objects.equals(getRowIndex(), that.getRowIndex()) &&
            Objects.equals(getAssetNumber(), that.getAssetNumber()) &&
            Objects.equals(getServiceOutletCode(), that.getServiceOutletCode()) &&
            Objects.equals(getAssetTag(), that.getAssetTag()) &&
            Objects.equals(getAssetDescription(), that.getAssetDescription()) &&
            Objects.equals(getNetBookValueDate(), that.getNetBookValueDate()) &&
            Objects.equals(getAssetCategory(), that.getAssetCategory()) &&
            Objects.equals(getDepreciationRegime(), that.getDepreciationRegime()) &&
            Objects.equals(getFileUploadToken(), that.getFileUploadToken()) &&
            Objects.equals(getCompilationToken(), that.getCompilationToken())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getRowIndex(),
            getAssetNumber(),
            getServiceOutletCode(),
            getAssetTag(),
            getAssetDescription(),
            getNetBookValueDate(),
            getAssetCategory(),
            getNetBookValue(),
            getDepreciationRegime(),
            getFileUploadToken(),
            getCompilationToken()
        );
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public FixedAssetNetBookValueEVM setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public Long getAssetNumber() {
        return assetNumber;
    }

    public FixedAssetNetBookValueEVM setAssetNumber(Long assetNumber) {
        this.assetNumber = assetNumber;
        return this;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public FixedAssetNetBookValueEVM setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public FixedAssetNetBookValueEVM setAssetTag(String assetTag) {
        this.assetTag = assetTag;
        return this;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public FixedAssetNetBookValueEVM setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public String getNetBookValueDate() {
        return netBookValueDate;
    }

    public FixedAssetNetBookValueEVM setNetBookValueDate(String netBookValueDate) {
        this.netBookValueDate = netBookValueDate;
        return this;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public FixedAssetNetBookValueEVM setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
        return this;
    }

    public double getNetBookValue() {
        return netBookValue;
    }

    public FixedAssetNetBookValueEVM setNetBookValue(double netBookValue) {
        this.netBookValue = netBookValue;
        return this;
    }

    public String getDepreciationRegime() {
        return depreciationRegime;
    }

    public FixedAssetNetBookValueEVM setDepreciationRegime(String depreciationRegime) {
        this.depreciationRegime = depreciationRegime;
        return this;
    }

    public String getFileUploadToken() {
        return fileUploadToken;
    }

    public FixedAssetNetBookValueEVM setFileUploadToken(String fileUploadToken) {
        this.fileUploadToken = fileUploadToken;
        return this;
    }

    public String getCompilationToken() {
        return compilationToken;
    }

    public FixedAssetNetBookValueEVM setCompilationToken(String compilationToken) {
        this.compilationToken = compilationToken;
        return this;
    }
}
