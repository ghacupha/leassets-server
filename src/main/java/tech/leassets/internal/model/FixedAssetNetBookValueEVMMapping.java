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

import tech.leassets.domain.enumeration.DepreciationRegime;
import tech.leassets.internal.Mapping;
import tech.leassets.internal.util.MapUtils;
import tech.leassets.service.dto.FixedAssetNetBookValueDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetNetBookValueEVMMapping extends Mapping<tech.leassets.internal.model.FixedAssetNetBookValueEVM, FixedAssetNetBookValueDTO> {
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
        return tech.leassets.internal.model.DepreciationRegimeMapUtils.stringToDepreciationRegime(depreciationRegimeId);
    }

    @org.mapstruct.Mapping(target = "depreciationRegime", source = "depreciationRegime")
    default String stringToDepreciationRegime(DepreciationRegime depreciationRegimeId) {
        return tech.leassets.internal.model.DepreciationRegimeMapUtils.depreciationRegimeToString(depreciationRegimeId);
    }
}
