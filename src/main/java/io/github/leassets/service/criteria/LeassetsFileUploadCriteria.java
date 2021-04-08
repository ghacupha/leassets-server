package io.github.leassets.service.criteria;

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

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.leassets.domain.LeassetsFileUpload} entity. This class is used
 * in {@link io.github.leassets.web.rest.LeassetsFileUploadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leassets-file-uploads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeassetsFileUploadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter fileName;

    private LocalDateFilter periodFrom;

    private LocalDateFilter periodTo;

    private LongFilter leassetsFileTypeId;

    private BooleanFilter uploadSuccessful;

    private BooleanFilter uploadProcessed;

    private StringFilter uploadToken;

    public LeassetsFileUploadCriteria() {}

    public LeassetsFileUploadCriteria(LeassetsFileUploadCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.periodFrom = other.periodFrom == null ? null : other.periodFrom.copy();
        this.periodTo = other.periodTo == null ? null : other.periodTo.copy();
        this.leassetsFileTypeId = other.leassetsFileTypeId == null ? null : other.leassetsFileTypeId.copy();
        this.uploadSuccessful = other.uploadSuccessful == null ? null : other.uploadSuccessful.copy();
        this.uploadProcessed = other.uploadProcessed == null ? null : other.uploadProcessed.copy();
        this.uploadToken = other.uploadToken == null ? null : other.uploadToken.copy();
    }

    @Override
    public LeassetsFileUploadCriteria copy() {
        return new LeassetsFileUploadCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public LocalDateFilter getPeriodFrom() {
        return periodFrom;
    }

    public LocalDateFilter periodFrom() {
        if (periodFrom == null) {
            periodFrom = new LocalDateFilter();
        }
        return periodFrom;
    }

    public void setPeriodFrom(LocalDateFilter periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDateFilter getPeriodTo() {
        return periodTo;
    }

    public LocalDateFilter periodTo() {
        if (periodTo == null) {
            periodTo = new LocalDateFilter();
        }
        return periodTo;
    }

    public void setPeriodTo(LocalDateFilter periodTo) {
        this.periodTo = periodTo;
    }

    public LongFilter getLeassetsFileTypeId() {
        return leassetsFileTypeId;
    }

    public LongFilter leassetsFileTypeId() {
        if (leassetsFileTypeId == null) {
            leassetsFileTypeId = new LongFilter();
        }
        return leassetsFileTypeId;
    }

    public void setLeassetsFileTypeId(LongFilter leassetsFileTypeId) {
        this.leassetsFileTypeId = leassetsFileTypeId;
    }

    public BooleanFilter getUploadSuccessful() {
        return uploadSuccessful;
    }

    public BooleanFilter uploadSuccessful() {
        if (uploadSuccessful == null) {
            uploadSuccessful = new BooleanFilter();
        }
        return uploadSuccessful;
    }

    public void setUploadSuccessful(BooleanFilter uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public BooleanFilter getUploadProcessed() {
        return uploadProcessed;
    }

    public BooleanFilter uploadProcessed() {
        if (uploadProcessed == null) {
            uploadProcessed = new BooleanFilter();
        }
        return uploadProcessed;
    }

    public void setUploadProcessed(BooleanFilter uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public StringFilter getUploadToken() {
        return uploadToken;
    }

    public StringFilter uploadToken() {
        if (uploadToken == null) {
            uploadToken = new StringFilter();
        }
        return uploadToken;
    }

    public void setUploadToken(StringFilter uploadToken) {
        this.uploadToken = uploadToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeassetsFileUploadCriteria that = (LeassetsFileUploadCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(periodFrom, that.periodFrom) &&
            Objects.equals(periodTo, that.periodTo) &&
            Objects.equals(leassetsFileTypeId, that.leassetsFileTypeId) &&
            Objects.equals(uploadSuccessful, that.uploadSuccessful) &&
            Objects.equals(uploadProcessed, that.uploadProcessed) &&
            Objects.equals(uploadToken, that.uploadToken)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            description,
            fileName,
            periodFrom,
            periodTo,
            leassetsFileTypeId,
            uploadSuccessful,
            uploadProcessed,
            uploadToken
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsFileUploadCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (periodFrom != null ? "periodFrom=" + periodFrom + ", " : "") +
            (periodTo != null ? "periodTo=" + periodTo + ", " : "") +
            (leassetsFileTypeId != null ? "leassetsFileTypeId=" + leassetsFileTypeId + ", " : "") +
            (uploadSuccessful != null ? "uploadSuccessful=" + uploadSuccessful + ", " : "") +
            (uploadProcessed != null ? "uploadProcessed=" + uploadProcessed + ", " : "") +
            (uploadToken != null ? "uploadToken=" + uploadToken + ", " : "") +
            "}";
    }
}
