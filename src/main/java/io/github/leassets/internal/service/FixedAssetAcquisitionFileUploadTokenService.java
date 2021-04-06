package io.github.leassets.internal.service;

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
