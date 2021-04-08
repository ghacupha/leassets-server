package io.github.leassets.internal.service;

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

import io.github.leassets.service.FixedAssetAcquisitionQueryService;
import io.github.leassets.service.criteria.FixedAssetAcquisitionCriteria;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.StringFilter;

@Service("fixedAssetAcquisitionFileUploadTokenService")
public class FixedAssetAcquisitionFileUploadTokenService implements FileUploadTokenService<FixedAssetAcquisitionDTO> {

    private final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService;

    public FixedAssetAcquisitionFileUploadTokenService(final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService) {
        this.fixedAssetAcquisitionQueryService = fixedAssetAcquisitionQueryService;
    }

    @Override
    public Optional<List<FixedAssetAcquisitionDTO>> findAllByUploadToken(final String messageToken) {
        FixedAssetAcquisitionCriteria criteria = new FixedAssetAcquisitionCriteria();
        StringFilter uploadToken = new StringFilter();
        uploadToken.setEquals(messageToken);
        criteria.setFileUploadToken(uploadToken);
        return Optional.of(fixedAssetAcquisitionQueryService.findByCriteria(criteria));
    }
}
