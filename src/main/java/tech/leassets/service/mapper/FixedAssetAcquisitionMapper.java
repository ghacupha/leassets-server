package tech.leassets.service.mapper;


import tech.leassets.domain.*;
import tech.leassets.service.dto.FixedAssetAcquisitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetAcquisition} and its DTO {@link FixedAssetAcquisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetAcquisitionMapper extends EntityMapper<FixedAssetAcquisitionDTO, FixedAssetAcquisition> {



    default FixedAssetAcquisition fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetAcquisition fixedAssetAcquisition = new FixedAssetAcquisition();
        fixedAssetAcquisition.setId(id);
        return fixedAssetAcquisition;
    }
}
