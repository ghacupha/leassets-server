package tech.leassets.internal.model.sampleDataModel;

import tech.leassets.internal.Mapping;
import org.mapstruct.Mapper;
import tech.leassets.service.dto.CurrencyTableDTO;
import tech.leassets.domain.enumeration.CurrencyLocality;
import static tech.leassets.domain.enumeration.CurrencyLocality.FOREIGN;
import static tech.leassets.domain.enumeration.CurrencyLocality.LOCAL;

/**
 * This is a sample implementation of how mapping is used to move from an entity's DTO to EVM
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyTableEVMMapping extends Mapping<CurrencyTableEVM, CurrencyTableDTO> {

    @org.mapstruct.Mapping(target = "locality", source = "locality")
    default CurrencyLocality locality(String localityString) {

        if (localityString == null) {
            return null;
        }
        if (localityString.equalsIgnoreCase("LOCAL")) {
            return LOCAL;
        }
        if (localityString.equalsIgnoreCase("FOREIGN")) {
            return FOREIGN;
        }
        return null;
    }

    @org.mapstruct.Mapping(target = "locality", source = "locality")
    default String locality(CurrencyLocality locality) {

        if (locality == null) {
            return null;
        }
        if (locality == LOCAL) {
            return "LOCAL";
        }
        if (locality == FOREIGN) {
            return "FOREIGN";
        }
        return null;
    }
}
