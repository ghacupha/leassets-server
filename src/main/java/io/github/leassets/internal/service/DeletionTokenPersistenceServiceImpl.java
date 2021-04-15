package io.github.leassets.internal.service;

import io.github.leassets.internal.framework.batch.DeletionTokenPersistenceService;
import io.github.leassets.internal.framework.model.TokenDTO;
import io.github.leassets.service.LeassetsMessageTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DeletionTokenPersistenceServiceImpl implements DeletionTokenPersistenceService {

    private final LeassetsMessageTokenService messageTokenService;
    private final TokenDTOMapper tokenDTOMapper;

    public DeletionTokenPersistenceServiceImpl(LeassetsMessageTokenService messageTokenService, TokenDTOMapper tokenDTOMapper) {
        this.messageTokenService = messageTokenService;
        this.tokenDTOMapper = tokenDTOMapper;
    }

    @Override
    public TokenDTO save(TokenDTO persistentToken) {
        return tokenDTOMapper.toValue1(messageTokenService.save(tokenDTOMapper.toValue2(persistentToken)));
    }

    @Override
    public Optional<List<TokenDTO>> findAllByUploadToken(String stringToken) {
        return Optional.empty();
    }
}
