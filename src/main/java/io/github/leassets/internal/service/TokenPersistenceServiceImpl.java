package io.github.leassets.internal.service;

import io.github.leassets.domain.LeassetsMessageToken;
import io.github.leassets.internal.framework.service.TokenPersistenceService;
import io.github.leassets.service.LeassetsMessageTokenService;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.leassets.service.mapper.LeassetsMessageTokenMapper;
import org.springframework.stereotype.Service;

@Service("tokenPersistenceServiceImpl")
public class TokenPersistenceServiceImpl implements TokenPersistenceService<LeassetsMessageTokenDTO, LeassetsMessageToken>{

    private final LeassetsMessageTokenService leassetsMessageTokenService;
    private final LeassetsMessageTokenMapper messageTokenMapper;

    public TokenPersistenceServiceImpl(LeassetsMessageTokenService leassetsMessageTokenService, LeassetsMessageTokenMapper messageTokenMapper) {
        this.leassetsMessageTokenService = leassetsMessageTokenService;
        this.messageTokenMapper = messageTokenMapper;
    }

    @Override
    public LeassetsMessageTokenDTO save(LeassetsMessageToken persistentToken) {
        return leassetsMessageTokenService.save(messageTokenMapper.toDto(persistentToken));
    }
}
