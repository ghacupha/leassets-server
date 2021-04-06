package io.github.leassets.internal.model;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.model.framework.EntityRepository;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import io.github.leassets.repository.search.FixedAssetAcquisitionSearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transactional batch persistence serviece for fixed-asset-acquisition
 */
@Service
@Transactional
public class FixedAssetAcquisitionEntityRepository implements EntityRepository<FixedAssetAcquisition> {

    private final FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository;

    private final FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository;

    public FixedAssetAcquisitionEntityRepository(
        @Qualifier("fixedAssetAcquisitionRepository") FixedAssetAcquisitionRepository fixedAssetAcquisitionRepository,
        @Qualifier("fixedAssetAcquisitionSearchRepository") FixedAssetAcquisitionSearchRepository fixedAssetAcquisitionSearchRepository
    ) {
        this.fixedAssetAcquisitionRepository = fixedAssetAcquisitionRepository;
        this.fixedAssetAcquisitionSearchRepository = fixedAssetAcquisitionSearchRepository;
    }

    @Override
    public void indexAll(List<FixedAssetAcquisition> entities) {
        fixedAssetAcquisitionSearchRepository.saveAll(entities);
    }

    @Override
    public List<FixedAssetAcquisition> saveAll(List<FixedAssetAcquisition> entities) {
        return fixedAssetAcquisitionRepository.saveAll(entities);
    }
}
