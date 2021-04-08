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

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.leassets.domain.LeassetsMessageToken} entity.
 */
public class LeassetsMessageTokenDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private Long timeSent;

    @NotNull
    private String tokenValue;

    private Boolean received;

    private Boolean actioned;

    private Boolean contentFullyEnqueued;

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

    public Long getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Long timeSent) {
        this.timeSent = timeSent;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public Boolean getActioned() {
        return actioned;
    }

    public void setActioned(Boolean actioned) {
        this.actioned = actioned;
    }

    public Boolean getContentFullyEnqueued() {
        return contentFullyEnqueued;
    }

    public void setContentFullyEnqueued(Boolean contentFullyEnqueued) {
        this.contentFullyEnqueued = contentFullyEnqueued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeassetsMessageTokenDTO)) {
            return false;
        }

        LeassetsMessageTokenDTO leassetsMessageTokenDTO = (LeassetsMessageTokenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leassetsMessageTokenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsMessageTokenDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", timeSent=" + getTimeSent() +
            ", tokenValue='" + getTokenValue() + "'" +
            ", received='" + getReceived() + "'" +
            ", actioned='" + getActioned() + "'" +
            ", contentFullyEnqueued='" + getContentFullyEnqueued() + "'" +
            "}";
    }
}
