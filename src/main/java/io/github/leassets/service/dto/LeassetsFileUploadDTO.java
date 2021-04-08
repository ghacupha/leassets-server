package io.github.leassets.service.dto;

/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import io.github.leassets.internal.batch.framework.HasDataFile;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.leassets.domain.LeassetsFileUpload} entity.
 */
public class LeassetsFileUploadDTO implements Serializable, HasDataFile<LeassetsFileUploadDTO> {

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

    //    /**
    //     * Simply returns the client's instance
    //     *
    //     * @return
    //     */
    //    @Override
    //    public LeassetsFileUploadDTO getChild() {
    //        return this;
    //    }

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

    public Boolean getUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean getUploadProcessed() {
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

        LeassetsFileUploadDTO leassetsFileUploadDTO = (LeassetsFileUploadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leassetsFileUploadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
            ", uploadSuccessful='" + getUploadSuccessful() + "'" +
            ", uploadProcessed='" + getUploadProcessed() + "'" +
            ", uploadToken='" + getUploadToken() + "'" +
            "}";
    }
}
