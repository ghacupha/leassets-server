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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.leassets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrencyTableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyTableDTO.class);
        CurrencyTableDTO currencyTableDTO1 = new CurrencyTableDTO();
        currencyTableDTO1.setId(1L);
        CurrencyTableDTO currencyTableDTO2 = new CurrencyTableDTO();
        assertThat(currencyTableDTO1).isNotEqualTo(currencyTableDTO2);
        currencyTableDTO2.setId(currencyTableDTO1.getId());
        assertThat(currencyTableDTO1).isEqualTo(currencyTableDTO2);
        currencyTableDTO2.setId(2L);
        assertThat(currencyTableDTO1).isNotEqualTo(currencyTableDTO2);
        currencyTableDTO1.setId(null);
        assertThat(currencyTableDTO1).isNotEqualTo(currencyTableDTO2);
    }
}
