package tech.leassets.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class FixedAssetNetBookValueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetNetBookValueDTO.class);
        FixedAssetNetBookValueDTO fixedAssetNetBookValueDTO1 = new FixedAssetNetBookValueDTO();
        fixedAssetNetBookValueDTO1.setId(1L);
        FixedAssetNetBookValueDTO fixedAssetNetBookValueDTO2 = new FixedAssetNetBookValueDTO();
        assertThat(fixedAssetNetBookValueDTO1).isNotEqualTo(fixedAssetNetBookValueDTO2);
        fixedAssetNetBookValueDTO2.setId(fixedAssetNetBookValueDTO1.getId());
        assertThat(fixedAssetNetBookValueDTO1).isEqualTo(fixedAssetNetBookValueDTO2);
        fixedAssetNetBookValueDTO2.setId(2L);
        assertThat(fixedAssetNetBookValueDTO1).isNotEqualTo(fixedAssetNetBookValueDTO2);
        fixedAssetNetBookValueDTO1.setId(null);
        assertThat(fixedAssetNetBookValueDTO1).isNotEqualTo(fixedAssetNetBookValueDTO2);
    }
}
