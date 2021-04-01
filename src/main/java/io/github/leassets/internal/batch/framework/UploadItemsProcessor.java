package io.github.leassets.internal.batch.framework;

import com.google.common.collect.ImmutableList;
import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.model.ExcelViewModel;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

/**
 * This object maps the excel-view-model to the equivalent DTO and add a jobUploadToken to the newly mapped
 * DTO. The inheriting instance is expected to provide the jobUploadToken from the batch system
 *
 * @param <T> Data Type for excel-view-model
 * @param <D> Data Type for the excel-view-models equivalent DTO
 */
public class UploadItemsProcessor<T, D> implements ItemProcessor<List<? extends ExcelViewModel<T>>, List<? extends HasUploadToken<D>>> {

    private final Mapping<T, ? extends HasUploadToken<D>> mapping;
    private final String jobUploadToken;

    public UploadItemsProcessor(
        final Mapping<T, ? extends HasUploadToken<D>> mapping,
        final String jobUploadToken
    ) {
        this.mapping = mapping;
        this.jobUploadToken = jobUploadToken;
    }

    @Override
    public List<? extends HasUploadToken<D>> process(final List<? extends ExcelViewModel<T>> evms) throws Exception {
        return evms.stream().map(ExcelViewModel::getModelData).map(mapping::toValue2).peek(dto -> dto.setFileUploadToken(jobUploadToken)).collect(ImmutableList.toImmutableList());
    }
}
