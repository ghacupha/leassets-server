package io.github.leassets.internal.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import io.github.leassets.domain.enumeration.DepreciationRegime;
import io.github.leassets.internal.framework.ExcelViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedAssetDepreciationEVM implements Serializable, ExcelViewModel<FixedAssetDepreciationEVM> {

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
    private LocalDate depreciationDate;

    @ExcelCell(5)
    private String assetCategory;

    @ExcelCell(6)
    private BigDecimal depreciationAmount;

    @ExcelCell(7)
    private DepreciationRegime depreciationRegime;

    private String fileUploadToken;

    private String compilationToken;

    @Override
    public FixedAssetDepreciationEVM getModelData() {
        return SerializationUtils.clone(this);
    }
}
