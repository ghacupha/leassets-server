package io.github.leassets.internal.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import io.github.leassets.internal.framework.ExcelViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedAssetAcquisitionEVM implements Serializable, ExcelViewModel<FixedAssetAcquisitionEVM> {

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
