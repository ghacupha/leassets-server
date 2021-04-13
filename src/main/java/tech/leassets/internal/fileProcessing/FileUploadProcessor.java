package tech.leassets.internal.fileProcessing;

import tech.leassets.internal.model.FileNotification;

public interface FileUploadProcessor<T> {

    T processFileUpload(T fileUpload, FileNotification fileNotification);
}
