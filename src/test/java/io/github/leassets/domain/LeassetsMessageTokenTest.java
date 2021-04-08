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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.leassets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeassetsMessageTokenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsMessageToken.class);
        LeassetsMessageToken leassetsMessageToken1 = new LeassetsMessageToken();
        leassetsMessageToken1.setId(1L);
        LeassetsMessageToken leassetsMessageToken2 = new LeassetsMessageToken();
        leassetsMessageToken2.setId(leassetsMessageToken1.getId());
        assertThat(leassetsMessageToken1).isEqualTo(leassetsMessageToken2);
        leassetsMessageToken2.setId(2L);
        assertThat(leassetsMessageToken1).isNotEqualTo(leassetsMessageToken2);
        leassetsMessageToken1.setId(null);
        assertThat(leassetsMessageToken1).isNotEqualTo(leassetsMessageToken2);
    }
}
