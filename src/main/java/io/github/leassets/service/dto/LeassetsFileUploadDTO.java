package io.github.leassets.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.leassets.domain.LeassetsFileUpload} entity.
 */
public class LeassetsFileUploadDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String fileName;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    @NotNull
    private Long leassetsFileTypeId;

    @Lob
    private byte[] dataFile;

    private String dataFileContentType;
    private Boolean uploadSuccessful;

    private Boolean uploadProcessed;

    private String uploadToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public Long getLeassetsFileTypeId() {
        return leassetsFileTypeId;
    }

    public void setLeassetsFileTypeId(Long leassetsFileTypeId) {
        this.leassetsFileTypeId = leassetsFileTypeId;
    }

    public byte[] getDataFile() {
        return dataFile;
    }

    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFileContentType() {
        return dataFileContentType;
    }

    public void setDataFileContentType(String dataFileContentType) {
        this.dataFileContentType = dataFileContentType;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeassetsFileUploadDTO)) {
            return false;
        }

        return id != null && id.equals(((LeassetsFileUploadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsFileUploadDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", leassetsFileTypeId=" + getLeassetsFileTypeId() +
            ", dataFile='" + getDataFile() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", uploadToken='" + getUploadToken() + "'" +
            "}";
    }
}