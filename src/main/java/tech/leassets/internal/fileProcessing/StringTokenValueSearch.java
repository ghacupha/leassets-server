package tech.leassets.internal.fileProcessing;

import tech.leassets.service.LeassetsMessageTokenQueryService;
import tech.leassets.service.dto.LeassetsMessageTokenCriteria;
import tech.leassets.service.dto.LeassetsMessageTokenDTO;
import io.github.jhipster.service.filter.StringFilter;
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
