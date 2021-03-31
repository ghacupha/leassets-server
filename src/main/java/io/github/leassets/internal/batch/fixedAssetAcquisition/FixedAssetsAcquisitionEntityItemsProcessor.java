package io.github.leassets.internal.batch.fixedAssetAcquisition;

import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.batch.framework.EntityItemsProcessor;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

public class FixedAssetsAcquisitionEntityItemsProcessor extends EntityItemsProcessor<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> {

    public FixedAssetsAcquisitionEntityItemsProcessor(Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> mapping,
                                                      final @Value("#{jobParameters['messageToken']}") String jobUploadToken) {
        super(mapping, jobUploadToken);
    }

    @Override
    protected List<FixedAssetAcquisitionDTO> getDtoStream(List<FixedAssetAcquisitionEVM> evms) {
        return evms.stream().map(mapping::toValue2).peek(e -> e.setFileUploadToken(jobUploadToken)).collect(Collectors.toList());
    }
}
