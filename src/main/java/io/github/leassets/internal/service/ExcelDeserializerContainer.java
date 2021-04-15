package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.excel.ExcelFileDeserializer;
import io.github.leassets.internal.framework.excel.DefaultExcelFileDeserializer;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.internal.model.sampleDataModel.CurrencyTableEVM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.leassets.internal.framework.excel.PoijiOptionsConfig.getDefaultPoijiOptions;

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
}
