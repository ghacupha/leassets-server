package io.github.leassets.internal.framework.fileProcessing;

import io.github.leassets.internal.model.FileNotification;

public interface FileUploadProcessor<T> {

    T processFileUpload(T fileUpload, FileNotification fileNotification);
}
