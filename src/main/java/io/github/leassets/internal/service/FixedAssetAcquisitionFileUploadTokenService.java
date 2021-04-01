package io.github.leassets.internal.service;

import io.github.leassets.service.EntityQueryService;
import io.github.leassets.service.criteria.FixedAssetAcquisitionCriteria;
import tech.jhipster.service.filter.StringFilter;

import java.util.List;
import java.util.Optional;

public class FixedAssetAcquisitionFileUploadTokenService<DTO, Criteria, Entity> implements FileUploadTokenService<DTO>  {

    private final EntityQueryService<DTO, Criteria, Entity> bankGuaranteeQueryService;
    private final HasTokenizedCriteria hasCriteriaInstance;

    public FixedAssetAcquisitionFileUploadTokenService(final EntityQueryService<DTO, Criteria, Entity> bankGuaranteeQueryService, HasTokenizedCriteria hasCriteriaInstance) {
        this.bankGuaranteeQueryService = bankGuaranteeQueryService;
        this.hasCriteriaInstance = hasCriteriaInstance;
    }

    @Override
    public Optional<List<DTO>> findAllByUploadToken(final String messageToken) {
        FixedAssetAcquisitionCriteria criteria = new FixedAssetAcquisitionCriteria();
        StringFilter uploadToken = new StringFilter();
        uploadToken.setEquals(messageToken);
        this.hasCriteriaInstance.setFileUploadToken(uploadToken);
        return Optional.of(bankGuaranteeQueryService.findByCriteria(hasCriteriaInstance));
    }
}
