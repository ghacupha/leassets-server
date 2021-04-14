package tech.leassets.internal.batch.framework;

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

import tech.leassets.internal.service.BatchService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Performs persistence of the newly created entities through the batch-service
 *
 * @param <DTO>
 */
public class EntityListItemsWriter<DTO> implements ItemWriter<List<DTO>> {

    private final BatchService<DTO> batchService;

    public EntityListItemsWriter(BatchService<DTO> batchService) {
        this.batchService = batchService;
    }

    @Override
    public void write(List<? extends List<DTO>> items) throws Exception {
        items.stream().peek(batchService::save).forEach(batchService::index);
    }
}
