package io.github.leassets.internal.service;

import io.github.jhipster.service.filter.StringFilter;
import io.github.leassets.internal.framework.service.DeletionUploadService;
import io.github.leassets.service.FixedAssetAcquisitionQueryService;
import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.FixedAssetAcquisitionCriteria;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("leassetsDeletionFileUploadService")
public class LeassetsDeletionFileUploadService implements DeletionUploadService<FixedAssetAcquisitionDTO> {

    private final LeassetsFileUploadService leassetsFileUploadService;
    private final FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService;

    public LeassetsDeletionFileUploadService(LeassetsFileUploadService leassetsFileUploadService, FixedAssetAcquisitionQueryService fixedAssetAcquisitionQueryService) {
        this.leassetsFileUploadService = leassetsFileUploadService;
        this.fixedAssetAcquisitionQueryService = fixedAssetAcquisitionQueryService;
    }

    @Override
    public Optional<LeassetsFileUploadDTO> findOne(long fileId) {

        return leassetsFileUploadService.findOne(fileId);
    }

    @Override
    public Optional<List<FixedAssetAcquisitionDTO>> findAllByUploadToken(String stringToken) {
        FixedAssetAcquisitionCriteria criteria = new FixedAssetAcquisitionCriteria();
        StringFilter uploadToken = new StringFilter();
        uploadToken.setEquals(stringToken);
        criteria.setFileUploadToken(uploadToken);
        return Optional.of(fixedAssetAcquisitionQueryService.findByCriteria(criteria));
    }
}
