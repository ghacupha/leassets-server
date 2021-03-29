package io.github.leassets.service.mapper;

import io.github.leassets.domain.*;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeassetsFileUpload} and its DTO {@link LeassetsFileUploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeassetsFileUploadMapper extends EntityMapper<LeassetsFileUploadDTO, LeassetsFileUpload> {}
