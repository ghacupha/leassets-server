package tech.leassets.internal.excel;

import tech.leassets.internal.excel.deserializer.DefaultExcelFileDeserializer;
import tech.leassets.internal.model.sampleDataModel.CurrencyTableEVM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static tech.leassets.internal.excel.PoijiOptionsConfig.getDefaultPoijiOptions;

/**
 * This container has configurations for our excel file deserializers and a sample is provided for currency-table
 */
@Configuration
public class ExcelDeserializerContainer {

    @Bean("currencyTableExcelFileDeserializer")
    public ExcelFileDeserializer<CurrencyTableEVM> currencyTableExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(CurrencyTableEVM.class, getDefaultPoijiOptions()).deserialize(excelFile);
    }

    // todo create bean for every data model
}
