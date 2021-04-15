package io.github.leassets.internal.framework.service;

import io.github.leassets.service.dto.LeassetsMessageTokenDTO;

/**
 * This is a service that saves and access message-tokens, file-upload tokens and all kinds of
 * hashcode entities from the repository
 */
public interface TokenPersistenceService<DTO, Entity> {

    DTO save(Entity persistentToken);
}
