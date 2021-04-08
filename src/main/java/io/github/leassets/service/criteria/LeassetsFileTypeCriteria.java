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

import io.github.leassets.domain.enumeration.LeassetsFileMediumTypes;
import io.github.leassets.domain.enumeration.LeassetsFileModelType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.leassets.domain.LeassetsFileType} entity. This class is used
 * in {@link io.github.leassets.web.rest.LeassetsFileTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leassets-file-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeassetsFileTypeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LeassetsFileMediumTypes
     */
    public static class LeassetsFileMediumTypesFilter extends Filter<LeassetsFileMediumTypes> {

        public LeassetsFileMediumTypesFilter() {}

        public LeassetsFileMediumTypesFilter(LeassetsFileMediumTypesFilter filter) {
            super(filter);
        }

        @Override
        public LeassetsFileMediumTypesFilter copy() {
            return new LeassetsFileMediumTypesFilter(this);
        }
    }

    /**
     * Class for filtering LeassetsFileModelType
     */
    public static class LeassetsFileModelTypeFilter extends Filter<LeassetsFileModelType> {

        public LeassetsFileModelTypeFilter() {}

        public LeassetsFileModelTypeFilter(LeassetsFileModelTypeFilter filter) {
            super(filter);
        }

        @Override
        public LeassetsFileModelTypeFilter copy() {
            return new LeassetsFileModelTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leassetsFileTypeName;

    private LeassetsFileMediumTypesFilter leassetsFileMediumType;

    private StringFilter description;

    private LeassetsFileModelTypeFilter leassetsfileType;

    public LeassetsFileTypeCriteria() {}

    public LeassetsFileTypeCriteria(LeassetsFileTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leassetsFileTypeName = other.leassetsFileTypeName == null ? null : other.leassetsFileTypeName.copy();
        this.leassetsFileMediumType = other.leassetsFileMediumType == null ? null : other.leassetsFileMediumType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.leassetsfileType = other.leassetsfileType == null ? null : other.leassetsfileType.copy();
    }

    @Override
    public LeassetsFileTypeCriteria copy() {
        return new LeassetsFileTypeCriteria(this);
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

    public StringFilter getLeassetsFileTypeName() {
        return leassetsFileTypeName;
    }

    public StringFilter leassetsFileTypeName() {
        if (leassetsFileTypeName == null) {
            leassetsFileTypeName = new StringFilter();
        }
        return leassetsFileTypeName;
    }

    public void setLeassetsFileTypeName(StringFilter leassetsFileTypeName) {
        this.leassetsFileTypeName = leassetsFileTypeName;
    }

    public LeassetsFileMediumTypesFilter getLeassetsFileMediumType() {
        return leassetsFileMediumType;
    }

    public LeassetsFileMediumTypesFilter leassetsFileMediumType() {
        if (leassetsFileMediumType == null) {
            leassetsFileMediumType = new LeassetsFileMediumTypesFilter();
        }
        return leassetsFileMediumType;
    }

    public void setLeassetsFileMediumType(LeassetsFileMediumTypesFilter leassetsFileMediumType) {
        this.leassetsFileMediumType = leassetsFileMediumType;
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

    public LeassetsFileModelTypeFilter getLeassetsfileType() {
        return leassetsfileType;
    }

    public LeassetsFileModelTypeFilter leassetsfileType() {
        if (leassetsfileType == null) {
            leassetsfileType = new LeassetsFileModelTypeFilter();
        }
        return leassetsfileType;
    }

    public void setLeassetsfileType(LeassetsFileModelTypeFilter leassetsfileType) {
        this.leassetsfileType = leassetsfileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeassetsFileTypeCriteria that = (LeassetsFileTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leassetsFileTypeName, that.leassetsFileTypeName) &&
            Objects.equals(leassetsFileMediumType, that.leassetsFileMediumType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(leassetsfileType, that.leassetsfileType)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leassetsFileTypeName, leassetsFileMediumType, description, leassetsfileType);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsFileTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leassetsFileTypeName != null ? "leassetsFileTypeName=" + leassetsFileTypeName + ", " : "") +
            (leassetsFileMediumType != null ? "leassetsFileMediumType=" + leassetsFileMediumType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (leassetsfileType != null ? "leassetsfileType=" + leassetsfileType + ", " : "") +
            "}";
    }
}
