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

class LeassetsFileUploadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeassetsFileUploadDTO.class);
        LeassetsFileUploadDTO leassetsFileUploadDTO1 = new LeassetsFileUploadDTO();
        leassetsFileUploadDTO1.setId(1L);
        LeassetsFileUploadDTO leassetsFileUploadDTO2 = new LeassetsFileUploadDTO();
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO2.setId(leassetsFileUploadDTO1.getId());
        assertThat(leassetsFileUploadDTO1).isEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO2.setId(2L);
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
        leassetsFileUploadDTO1.setId(null);
        assertThat(leassetsFileUploadDTO1).isNotEqualTo(leassetsFileUploadDTO2);
    }
}
