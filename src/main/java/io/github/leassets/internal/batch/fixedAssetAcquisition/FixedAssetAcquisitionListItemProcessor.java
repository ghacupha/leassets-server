package io.github.leassets.internal.batch.fixedAssetAcquisition;

import com.google.common.collect.ImmutableList;
import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.service.dto.FixedAssetAcquisitionDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class FixedAssetAcquisitionListItemProcessor implements ItemProcessor<List<FixedAssetAcquisitionEVM>, List<FixedAssetAcquisitionDTO>> {

    private final Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> mapping;
    private final String jobUploadToken;

    public FixedAssetAcquisitionListItemProcessor(
        final Mapping<FixedAssetAcquisitionEVM, FixedAssetAcquisitionDTO> mapping,
        final @Value("#{jobParameters['messageToken']}") String jobUploadToken
    ) {
        this.mapping = mapping;
        this.jobUploadToken = jobUploadToken;
    }

    @Override
    public List<FixedAssetAcquisitionDTO> process(final List<FixedAssetAcquisitionEVM> evms) throws Exception {
        return evms.stream().map(mapping::toValue2).peek(d -> d.setFileUploadToken(jobUploadToken)).collect(ImmutableList.toImmutableList());
    }
}
