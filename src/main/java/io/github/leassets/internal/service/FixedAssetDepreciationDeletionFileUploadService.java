package io.github.leassets.internal.service;

import io.github.jhipster.service.filter.StringFilter;
import io.github.leassets.internal.framework.service.DeletionUploadService;
import io.github.leassets.service.FixedAssetDepreciationQueryService;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.FixedAssetDepreciationCriteria;
import io.github.leassets.service.dto.FixedAssetDepreciationDTO;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("fixedAssetDepreciationDeletionFileUploadService")
public class FixedAssetDepreciationDeletionFileUploadService implements DeletionUploadService<FixedAssetDepreciationDTO> {

    private final LeassetsFileUploadService leassetsFileUploadService;
    private final FixedAssetDepreciationQueryService fixedAssetDepreciationQueryService;

    public FixedAssetDepreciationDeletionFileUploadService(LeassetsFileUploadService leassetsFileUploadService, FixedAssetDepreciationQueryService fixedAssetDepreciationQueryService) {
        this.leassetsFileUploadService = leassetsFileUploadService;
        this.fixedAssetDepreciationQueryService = fixedAssetDepreciationQueryService;
    }

    @Override
    public Optional<LeassetsFileUploadDTO> findOne(long fileId) {

        return leassetsFileUploadService.findOne(fileId);
    }

    @Override
    public Optional<List<FixedAssetDepreciationDTO>> findAllByUploadToken(String stringToken) {
        FixedAssetDepreciationCriteria criteria = new FixedAssetDepreciationCriteria();
        StringFilter uploadToken = new StringFilter();
        uploadToken.setEquals(stringToken);
        criteria.setFileUploadToken(uploadToken);
        return Optional.of(fixedAssetDepreciationQueryService.findByCriteria(criteria));
    }
}
