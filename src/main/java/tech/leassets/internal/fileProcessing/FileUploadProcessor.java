package tech.leassets.internal.fileProcessing;


import tech.leassets.internal.model.framework.FileNotification;

public interface FileUploadProcessor<T> {

    T processFileUpload(T fileUpload, FileNotification fileNotification);
}
