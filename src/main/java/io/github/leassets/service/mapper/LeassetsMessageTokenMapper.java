package io.github.leassets.service.mapper;


import io.github.leassets.domain.*;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeassetsMessageToken} and its DTO {@link LeassetsMessageTokenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeassetsMessageTokenMapper extends EntityMapper<LeassetsMessageTokenDTO, LeassetsMessageToken> {



    default LeassetsMessageToken fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeassetsMessageToken leassetsMessageToken = new LeassetsMessageToken();
        leassetsMessageToken.setId(id);
        return leassetsMessageToken;
    }
}