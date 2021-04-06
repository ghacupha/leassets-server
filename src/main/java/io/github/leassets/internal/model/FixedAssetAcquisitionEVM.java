package io.github.leassets.internal.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Excel view model for the fixed-asset-acquisition entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixedAssetAcquisitionEVM implements ExcelViewModel<FixedAssetAcquisitionEVM> {

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

    @Override
    public FixedAssetAcquisitionEVM getModelData() {
        return this;
    }
}
