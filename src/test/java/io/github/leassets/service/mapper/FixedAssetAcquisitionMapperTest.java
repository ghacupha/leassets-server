package io.github.leassets.service.mapper;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FixedAssetAcquisitionMapperTest {

    private FixedAssetAcquisitionMapper fixedAssetAcquisitionMapper;

    @BeforeEach
    public void setUp() {
        fixedAssetAcquisitionMapper = new FixedAssetAcquisitionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fixedAssetAcquisitionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fixedAssetAcquisitionMapper.fromId(null)).isNull();
    }
}
