package io.github.leassets.internal.model;

import java.io.Serializable;

/**
 * Marker interface for models containing data that has been deserialized from an excel file
 */
public interface ExcelViewModel<M> extends Serializable {

    Long getRowIndex();

    String getFileUploadToken();

    M getModelData();
}
