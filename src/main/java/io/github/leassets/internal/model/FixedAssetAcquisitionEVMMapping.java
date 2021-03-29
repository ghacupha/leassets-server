package io.github.leassets.internal.model;

import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.util.MapUtils;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetAcquisitionEVMMapping extends Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> {

    @org.mapstruct.Mapping(target = "purchaseDate", source = "purchaseDate")
    default LocalDate dateStringToLocalDate(String dateString) {

        return MapUtils.dateStringToLocalDate(dateString);
    }

    @org.mapstruct.Mapping(target = "purchaseDate", source = "purchaseDate")
    default String localDateToDateString(LocalDate localDateValue) {

        return MapUtils.localDateToDateString(localDateValue);
    }

    @org.mapstruct.Mapping(target = "purchasePrice", source = "purchasePrice")
    default BigDecimal toBigDecimalValue(Double doubleValue) {

        return MapUtils.doubleToBigDecimal(doubleValue);
    }

    @org.mapstruct.Mapping(target = "purchasePrice", source = "purchasePrice")
    default Double toDoubleValue(BigDecimal bigDecimalValue) {

        return MapUtils.bigDecimalToDouble(bigDecimalValue);
    }
}
