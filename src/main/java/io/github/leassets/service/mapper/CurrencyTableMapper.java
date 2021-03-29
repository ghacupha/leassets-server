package io.github.leassets.service.mapper;

import io.github.leassets.domain.*;
import io.github.leassets.service.dto.CurrencyTableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrencyTable} and its DTO {@link CurrencyTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyTableMapper extends EntityMapper<CurrencyTableDTO, CurrencyTable> {}
