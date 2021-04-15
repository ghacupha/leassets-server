package io.github.leassets.internal.framework.service;

import io.github.leassets.internal.framework.batch.HasDataFile;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used in batch deletion job instances to access data about the
 * file-upload entity's instance that is being deleted. It only need to provide the file itself
 * based on the file-id as well as the file based on a string token
 *
 * The second method {@link DeletionUploadService::findAllByUploadToken()} uses the token to query the
 * repository find all instances of the DTO which have the same message-token. This method is specific to
 * the entity which is being configured for file-related batch deletion
 *
 * @param <DTO> This is the DTO containing the file-upload's instance message-token
 */
public interface DeletionUploadService<DTO> {

    /**
     * Returns an instance of the file-upload that contains the same file-id
     * @param fileId
     * @return
     */
    Optional<? extends HasDataFile> findOne(long fileId);

    /**
     * Return a list of persistent DTO entity instance whose file upload token is
     * equal to the stringToken provided in the args
     *
     * @param stringToken
     * @return
     */
    Optional<List<DTO>> findAllByUploadToken(String stringToken);
}
