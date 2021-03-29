package io.github.leassets.service.mapper;

import io.github.leassets.domain.*;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetAcquisition} and its DTO {@link FixedAssetAcquisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetAcquisitionMapper extends EntityMapper<FixedAssetAcquisitionDTO, FixedAssetAcquisition> {}
