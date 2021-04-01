package io.github.leassets.internal.service;

import tech.jhipster.service.filter.StringFilter;

public interface HasTokenizedCriteria<T> {

    HasTokenizedCriteria<T> getInstance();

    void setFileUploadToken(StringFilter uploadToken);
}
