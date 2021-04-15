package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.Mapping;
import io.github.leassets.internal.framework.model.TokenDTO;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TokenDTOMapper extends Mapping<TokenDTO, LeassetsMessageTokenDTO> {
}
