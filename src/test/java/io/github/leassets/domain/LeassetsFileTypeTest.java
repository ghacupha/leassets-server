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

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.leassets.web.rest.TestUtil;

public class LeassetsFileTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileType.class);
        LeassetsFileType leassetsFileType1 = new LeassetsFileType();
        leassetsFileType1.setId(1L);
        LeassetsFileType leassetsFileType2 = new LeassetsFileType();
        leassetsFileType2.setId(leassetsFileType1.getId());
        assertThat(leassetsFileType1).isEqualTo(leassetsFileType2);
        leassetsFileType2.setId(2L);
        assertThat(leassetsFileType1).isNotEqualTo(leassetsFileType2);
        leassetsFileType1.setId(null);
        assertThat(leassetsFileType1).isNotEqualTo(leassetsFileType2);
    }
}
