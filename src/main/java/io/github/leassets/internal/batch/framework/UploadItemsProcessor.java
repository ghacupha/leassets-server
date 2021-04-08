package io.github.leassets.internal.batch.framework;

/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.google.common.collect.ImmutableList;
import io.github.leassets.internal.Mapping;
import io.github.leassets.internal.model.ExcelViewModel;
import java.util.List;
import org.springframework.batch.item.ItemProcessor;

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

    public UploadItemsProcessor(final Mapping<T, ? extends HasUploadToken<D>> mapping, final String jobUploadToken) {
        this.mapping = mapping;
        this.jobUploadToken = jobUploadToken;
    }

    @Override
    public List<? extends HasUploadToken<D>> process(final List<? extends ExcelViewModel<T>> evms) throws Exception {
        return evms
            .stream()
            .map(ExcelViewModel::getModelData)
            .map(mapping::toValue2)
            .peek(dto -> dto.setUploadToken(jobUploadToken))
            .collect(ImmutableList.toImmutableList());
    }
}
