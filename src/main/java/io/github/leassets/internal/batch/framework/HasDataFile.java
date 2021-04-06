package io.github.leassets.internal.batch.framework;

/**
 * Its expected to be a file-upload wrapping an byte-stream object names as "dataFile"
 */
public interface HasDataFile<D> extends HasUploadToken<D> {
    byte[] getDataFile();
}
