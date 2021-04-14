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
 * Excel view model for the fixed-asset-depreciation entity
 */
public class FixedAssetDepreciationEVM implements Serializable {

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
    private String depreciationDate;

    @ExcelCell(5)
    private String assetCategory;

    @ExcelCell(6)
    private double depreciationAmount;

    @ExcelCell(7)
    private String depreciationRegime;

    private String fileUploadToken;

    private String compilationToken;

    public FixedAssetDepreciationEVM() {}

    public FixedAssetDepreciationEVM(
        Long rowIndex,
        Long assetNumber,
        String serviceOutletCode,
        String assetTag,
        String assetDescription,
        String depreciationDate,
        String assetCategory,
        double depreciationAmount,
        String depreciationRegime,
        String fileUploadToken,
        String compilationToken
    ) {
        this.rowIndex = rowIndex;
        this.assetNumber = assetNumber;
        this.serviceOutletCode = serviceOutletCode;
        this.assetTag = assetTag;
        this.assetDescription = assetDescription;
        this.depreciationDate = depreciationDate;
        this.assetCategory = assetCategory;
        this.depreciationAmount = depreciationAmount;
        this.depreciationRegime = depreciationRegime;
        this.fileUploadToken = fileUploadToken;
        this.compilationToken = compilationToken;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FixedAssetDepreciationEVM.class.getSimpleName() + "[", "]")
            .add("rowIndex=" + rowIndex)
            .add("assetNumber=" + assetNumber)
            .add("serviceOutletCode='" + serviceOutletCode + "'")
            .add("assetTag='" + assetTag + "'")
            .add("assetDescription='" + assetDescription + "'")
            .add("depreciationDate='" + depreciationDate + "'")
            .add("assetCategory='" + assetCategory + "'")
            .add("depreciationAmount=" + depreciationAmount)
            .add("depreciationRegime='" + depreciationRegime + "'")
            .add("fileUploadToken='" + fileUploadToken + "'")
            .add("compilationToken='" + compilationToken + "'")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedAssetDepreciationEVM)) return false;
        FixedAssetDepreciationEVM that = (FixedAssetDepreciationEVM) o;
        return (
            Double.compare(that.getDepreciationAmount(), getDepreciationAmount()) == 0 &&
            Objects.equals(getRowIndex(), that.getRowIndex()) &&
            Objects.equals(getAssetNumber(), that.getAssetNumber()) &&
            Objects.equals(getServiceOutletCode(), that.getServiceOutletCode()) &&
            Objects.equals(getAssetTag(), that.getAssetTag()) &&
            Objects.equals(getAssetDescription(), that.getAssetDescription()) &&
            Objects.equals(getDepreciationDate(), that.getDepreciationDate()) &&
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
            getDepreciationDate(),
            getAssetCategory(),
            getDepreciationAmount(),
            getDepreciationRegime(),
            getFileUploadToken(),
            getCompilationToken()
        );
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public FixedAssetDepreciationEVM setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public Long getAssetNumber() {
        return assetNumber;
    }

    public FixedAssetDepreciationEVM setAssetNumber(Long assetNumber) {
        this.assetNumber = assetNumber;
        return this;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public FixedAssetDepreciationEVM setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public FixedAssetDepreciationEVM setAssetTag(String assetTag) {
        this.assetTag = assetTag;
        return this;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public FixedAssetDepreciationEVM setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    public String getDepreciationDate() {
        return depreciationDate;
    }

    public FixedAssetDepreciationEVM setDepreciationDate(String depreciationDate) {
        this.depreciationDate = depreciationDate;
        return this;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public FixedAssetDepreciationEVM setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
        return this;
    }

    public double getDepreciationAmount() {
        return depreciationAmount;
    }

    public FixedAssetDepreciationEVM setDepreciationAmount(double depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
        return this;
    }

    public String getDepreciationRegime() {
        return depreciationRegime;
    }

    public FixedAssetDepreciationEVM setDepreciationRegime(String depreciationRegime) {
        this.depreciationRegime = depreciationRegime;
        return this;
    }

    public String getFileUploadToken() {
        return fileUploadToken;
    }

    public FixedAssetDepreciationEVM setFileUploadToken(String fileUploadToken) {
        this.fileUploadToken = fileUploadToken;
        return this;
    }

    public String getCompilationToken() {
        return compilationToken;
    }

    public FixedAssetDepreciationEVM setCompilationToken(String compilationToken) {
        this.compilationToken = compilationToken;
        return this;
    }
}
