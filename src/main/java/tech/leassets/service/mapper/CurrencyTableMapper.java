package tech.leassets.service.mapper;


import tech.leassets.domain.*;
import tech.leassets.service.dto.CurrencyTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrencyTable} and its DTO {@link CurrencyTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyTableMapper extends EntityMapper<CurrencyTableDTO, CurrencyTable> {



    default CurrencyTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurrencyTable currencyTable = new CurrencyTable();
        currencyTable.setId(id);
        return currencyTable;
    }
}
