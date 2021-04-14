package io.github.leassets.service.mapper;


import io.github.leassets.domain.*;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetDepreciation} and its DTO {@link FixedAssetDepreciationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetDepreciationMapper extends EntityMapper<FixedAssetDepreciationDTO, FixedAssetDepreciation> {



    default FixedAssetDepreciation fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetDepreciation fixedAssetDepreciation = new FixedAssetDepreciation();
        fixedAssetDepreciation.setId(id);
        return fixedAssetDepreciation;
    }
}
