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

import io.github.leassets.domain.enumeration.CurrencyLocality;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.leassets.domain.CurrencyTable} entity.
 */
public class CurrencyTableDTO implements Serializable {

    private Long id;

    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotNull
    private CurrencyLocality locality;

    private String currencyName;

    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyLocality getLocality() {
        return locality;
    }

    public void setLocality(CurrencyLocality locality) {
        this.locality = locality;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyTableDTO)) {
            return false;
        }

        CurrencyTableDTO currencyTableDTO = (CurrencyTableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currencyTableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTableDTO{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", locality='" + getLocality() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
