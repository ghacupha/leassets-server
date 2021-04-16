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

public class LeassetsFileUploadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileUpload.class);
        LeassetsFileUpload leassetsFileUpload1 = new LeassetsFileUpload();
        leassetsFileUpload1.setId(1L);
        LeassetsFileUpload leassetsFileUpload2 = new LeassetsFileUpload();
        leassetsFileUpload2.setId(leassetsFileUpload1.getId());
        assertThat(leassetsFileUpload1).isEqualTo(leassetsFileUpload2);
        leassetsFileUpload2.setId(2L);
        assertThat(leassetsFileUpload1).isNotEqualTo(leassetsFileUpload2);
        leassetsFileUpload1.setId(null);
        assertThat(leassetsFileUpload1).isNotEqualTo(leassetsFileUpload2);
    }
}
