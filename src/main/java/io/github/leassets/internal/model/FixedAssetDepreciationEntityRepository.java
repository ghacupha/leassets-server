package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetDepreciation;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetDepreciationRepository;
import io.github.leassets.repository.search.FixedAssetDepreciationSearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A transactional batch persistence service for the fixed-asset-depreciation entity
 */
@Service
@Transactional
public class FixedAssetDepreciationEntityRepository implements EntityRepository<FixedAssetDepreciation> {

    private final FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository;

    private final FixedAssetDepreciationRepository fixedAssetDepreciationRepository;

    public FixedAssetDepreciationEntityRepository(
        @Qualifier("fixedAssetDepreciationSearchRepository") FixedAssetDepreciationSearchRepository fixedAssetDepreciationSearchRepository,
        @Qualifier("fixedAssetDepreciationRepository") FixedAssetDepreciationRepository fixedAssetDepreciationRepository
    ) {
        this.fixedAssetDepreciationSearchRepository = fixedAssetDepreciationSearchRepository;
        this.fixedAssetDepreciationRepository = fixedAssetDepreciationRepository;
    }

    @Override
    public void indexAll(List<FixedAssetDepreciation> entities) {
        fixedAssetDepreciationSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetDepreciation> saveAll(List<FixedAssetDepreciation> entities) {
        return fixedAssetDepreciationRepository.saveAll(entities);
    }
}
