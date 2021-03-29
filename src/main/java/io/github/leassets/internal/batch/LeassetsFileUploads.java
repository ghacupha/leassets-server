package io.github.leassets.internal.batch;

import io.github.leassets.internal.batch.framework.DataFileUploadService;

/**
 * Extended from internal file-uploads-service inorder to provide the current implementation of file uploads
 * @param <T>
 */
public interface LeassetsFileUploads<T> extends DataFileUploadService<T> {
}
