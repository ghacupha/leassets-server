package tech.leassets.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class FixedAssetDepreciationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetDepreciationDTO.class);
        FixedAssetDepreciationDTO fixedAssetDepreciationDTO1 = new FixedAssetDepreciationDTO();
        fixedAssetDepreciationDTO1.setId(1L);
        FixedAssetDepreciationDTO fixedAssetDepreciationDTO2 = new FixedAssetDepreciationDTO();
        assertThat(fixedAssetDepreciationDTO1).isNotEqualTo(fixedAssetDepreciationDTO2);
        fixedAssetDepreciationDTO2.setId(fixedAssetDepreciationDTO1.getId());
        assertThat(fixedAssetDepreciationDTO1).isEqualTo(fixedAssetDepreciationDTO2);
        fixedAssetDepreciationDTO2.setId(2L);
        assertThat(fixedAssetDepreciationDTO1).isNotEqualTo(fixedAssetDepreciationDTO2);
        fixedAssetDepreciationDTO1.setId(null);
        assertThat(fixedAssetDepreciationDTO1).isNotEqualTo(fixedAssetDepreciationDTO2);
    }
}
