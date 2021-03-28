package io.github.leassets.internal.fileProcessing;

import io.github.jhipster.service.filter.StringFilter;
import io.github.leassets.service.LeassetsMessageTokenQueryService;
import io.github.leassets.service.dto.LeassetsMessageTokenCriteria;
import io.github.leassets.service.dto.LeassetsMessageTokenDTO;
import org.springframework.stereotype.Service;

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
