package io.github.leassets.internal.excel;

import static io.github.leassets.internal.excel.PoijiOptionsConfig.getDefaultPoijiOptions;

import io.github.leassets.domain.FixedAssetDepreciation;
import io.github.leassets.internal.excel.deserializer.DefaultExcelFileDeserializer;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.internal.model.FixedAssetDepreciationEVM;
import io.github.leassets.internal.model.FixedAssetNetBookValueEVM;
import io.github.leassets.internal.model.sampleDataModel.CurrencyTableEVM;
import liquibase.pro.packaged.D;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This container has configurations for our excel file deserializers and a sample is provided for currency-table
 */
@Configuration
public class ExcelDeserializerContainer {

    @Bean("currencyTableExcelFileDeserializer")
    public ExcelFileDeserializer<CurrencyTableEVM> currencyTableExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(CurrencyTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    @Bean("fixedAssetAcquisitionExcelFileDeserializer")
    public ExcelFileDeserializer<FixedAssetAcquisitionEVM> fixedAssetAcquisitionExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(FixedAssetAcquisitionEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    @Bean("fixedAssetDepreciationExcelFileDeserializer")
    public ExcelFileDeserializer<FixedAssetDepreciationEVM> fixedAssetDepreciationExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(FixedAssetDepreciationEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    @Bean("fixedAssetNetBookValueExcelFileDeserializer")
    public ExcelFileDeserializer<FixedAssetNetBookValueEVM> fixedAssetNetBookValueExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(FixedAssetNetBookValueEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }
}
