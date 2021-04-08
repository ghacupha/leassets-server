package io.github.leassets.domain;

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
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A LeassetsMessageToken.
 */
@Entity
@Table(name = "leassets_message_token")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "leassetsmessagetoken")
public class LeassetsMessageToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "time_sent", nullable = false)
    private Long timeSent;

    @NotNull
    @Column(name = "token_value", nullable = false)
    private String tokenValue;

    @Column(name = "received")
    private Boolean received;

    @Column(name = "actioned")
    private Boolean actioned;

    @Column(name = "content_fully_enqueued")
    private Boolean contentFullyEnqueued;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeassetsMessageToken id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public LeassetsMessageToken description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimeSent() {
        return this.timeSent;
    }

    public LeassetsMessageToken timeSent(Long timeSent) {
        this.timeSent = timeSent;
        return this;
    }

    public void setTimeSent(Long timeSent) {
        this.timeSent = timeSent;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public LeassetsMessageToken tokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Boolean getReceived() {
        return this.received;
    }

    public LeassetsMessageToken received(Boolean received) {
        this.received = received;
        return this;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public Boolean getActioned() {
        return this.actioned;
    }

    public LeassetsMessageToken actioned(Boolean actioned) {
        this.actioned = actioned;
        return this;
    }

    public void setActioned(Boolean actioned) {
        this.actioned = actioned;
    }

    public Boolean getContentFullyEnqueued() {
        return this.contentFullyEnqueued;
    }

    public LeassetsMessageToken contentFullyEnqueued(Boolean contentFullyEnqueued) {
        this.contentFullyEnqueued = contentFullyEnqueued;
        return this;
    }

    public void setContentFullyEnqueued(Boolean contentFullyEnqueued) {
        this.contentFullyEnqueued = contentFullyEnqueued;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeassetsMessageToken)) {
            return false;
        }
        return id != null && id.equals(((LeassetsMessageToken) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeassetsMessageToken{" +
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
