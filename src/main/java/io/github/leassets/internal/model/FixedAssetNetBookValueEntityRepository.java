package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetNetBookValue;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetNetBookValueRepository;
import io.github.leassets.repository.search.FixedAssetNetBookValueSearchRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A transactional batch persistence service for the fixed-asset-net-book-value entity
 */
@Transactional
@Service
public class FixedAssetNetBookValueEntityRepository implements EntityRepository<FixedAssetNetBookValue> {

    private final FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository;

    private final FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository;

    public FixedAssetNetBookValueEntityRepository(
        FixedAssetNetBookValueSearchRepository fixedAssetNetBookValueSearchRepository,
        FixedAssetNetBookValueRepository fixedAssetNetBookValueRepository
    ) {
        this.fixedAssetNetBookValueSearchRepository = fixedAssetNetBookValueSearchRepository;
        this.fixedAssetNetBookValueRepository = fixedAssetNetBookValueRepository;
    }

    @Override
    public void indexAll(List<FixedAssetNetBookValue> entities) {
        fixedAssetNetBookValueSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetNetBookValue> saveAll(List<FixedAssetNetBookValue> entities) {
        return fixedAssetNetBookValueRepository.saveAll(entities);
    }
}
