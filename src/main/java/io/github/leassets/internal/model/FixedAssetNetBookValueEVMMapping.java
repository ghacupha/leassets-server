package io.github.leassets.internal.model;

import io.github.leassets.domain.enumeration.DepreciationRegime;
import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.util.MapUtils;
import io.github.leassets.service.dto.FixedAssetNetBookValueDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetNetBookValueEVMMapping extends Mapping<FixedAssetNetBookValueEVM, FixedAssetNetBookValueDTO> {

    @org.mapstruct.Mapping(target = "netBookValueDate", source = "netBookValueDate")
    default LocalDate dateStringToLocalDate(String dateString) {

        return MapUtils.dateStringToLocalDate(dateString);
    }

    @org.mapstruct.Mapping(target = "netBookValueDate", source = "netBookValueDate")
    default String localDateToDateString(LocalDate localDateValue) {

        return MapUtils.localDateToDateString(localDateValue);
    }

    @org.mapstruct.Mapping(target = "netBookValue", source = "netBookValue")
    default BigDecimal toBigDecimalValue(Double doubleValue) {

        return MapUtils.doubleToBigDecimal(doubleValue);
    }

    @org.mapstruct.Mapping(target = "netBookValue", source = "netBookValue")
    default Double toDoubleValue(BigDecimal bigDecimalValue) {

        return MapUtils.bigDecimalToDouble(bigDecimalValue);
    }

    @org.mapstruct.Mapping(target = "depreciationRegime", source = "depreciationRegime")
    default DepreciationRegime stringToDepreciationRegime(String depreciationRegimeId) {

        return DepreciationRegimeMapUtils.stringToDepreciationRegime(depreciationRegimeId);
    }

    @org.mapstruct.Mapping(target = "depreciationRegime", source = "depreciationRegime")
    default String stringToDepreciationRegime(DepreciationRegime depreciationRegimeId) {

        return DepreciationRegimeMapUtils.depreciationRegimeToString(depreciationRegimeId);
    }

}
