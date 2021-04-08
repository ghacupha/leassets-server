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
package io.github.leassets.internal.fileProcessing;

import io.github.leassets.service.LeassetsMessageTokenQueryService;
import io.github.leassets.service.criteria.LeassetsMessageTokenCriteria;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.StringFilter;

/**
 * Implementation of token-search where the token value itself is of the value string
 */
@Service("stringTokenValueSearch")
public class StringTokenValueSearch implements TokenValueSearch<String> {

    private final LeassetsMessageTokenQueryService messageTokenQueryService;

    public StringTokenValueSearch(final LeassetsMessageTokenQueryService messageTokenQueryService) {
        this.messageTokenQueryService = messageTokenQueryService;
    }

    public LeassetsMessageTokenDTO getMessageToken(final String tokenValue) {
        StringFilter tokenFilter = new StringFilter();
        tokenFilter.setEquals(tokenValue);
        LeassetsMessageTokenCriteria tokenValueCriteria = new LeassetsMessageTokenCriteria();
        tokenValueCriteria.setTokenValue(tokenFilter);
        return messageTokenQueryService.findByCriteria(tokenValueCriteria).get(0);
    }
}
