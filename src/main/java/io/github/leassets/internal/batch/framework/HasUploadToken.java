package io.github.leassets.internal.batch.framework;

/**
 * Represents and entity that contains a string upload-token
 */
public interface HasUploadToken<D> {
    /**
     * Takes a string token and applies the same to the client
     *
     * @param uploadToken
     */
    void setUploadToken(String uploadToken);

    String getUploadToken();
    //    /**
    //     * Simply returns the client's instance
    //     *
    //     * @return
    //     */
    //    D getChild();
}
