package io.github.leassets.internal.model;

import io.github.leassets.internal.framework.MapUtils;
import io.github.leassets.internal.framework.Mapping;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetDepreciationEVMMapping extends Mapping<FixedAssetDepreciationEVM, FixedAssetDepreciationDTO> {

    @org.mapstruct.Mapping(target = "depreciationDate", source = "depreciationDate")
    default LocalDate dateStringToLocalDate(String dateString) {
        return MapUtils.dateStringToLocalDate(dateString);
    }

    @org.mapstruct.Mapping(target = "depreciationDate", source = "depreciationDate")
    default String localDateToDateString(LocalDate localDateValue) {
        return MapUtils.localDateToDateString(localDateValue);
    }

    @org.mapstruct.Mapping(target = "depreciationAmount", source = "depreciationAmount")
    default BigDecimal toBigDecimalValue(Double doubleValue) {
        return MapUtils.doubleToBigDecimal(doubleValue);
    }

    @org.mapstruct.Mapping(target = "depreciationAmount", source = "depreciationAmount")
    default Double toDoubleValue(BigDecimal bigDecimalValue) {
        return MapUtils.bigDecimalToDouble(bigDecimalValue);
    }
}
