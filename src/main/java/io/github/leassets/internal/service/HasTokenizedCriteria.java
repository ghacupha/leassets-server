package io.github.leassets.internal.service;

import tech.jhipster.service.filter.StringFilter;

/**
 * This interface's client has both a criteria implementation and a file upload token
 *
 * @param <T>
 */
public interface HasTokenizedCriteria<T> {
    HasTokenizedCriteria<T> getInstance();

    void setFileUploadToken(StringFilter uploadToken);
}
