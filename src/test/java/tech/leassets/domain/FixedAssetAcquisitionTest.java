package tech.leassets.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class FixedAssetAcquisitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetAcquisition.class);
        FixedAssetAcquisition fixedAssetAcquisition1 = new FixedAssetAcquisition();
        fixedAssetAcquisition1.setId(1L);
        FixedAssetAcquisition fixedAssetAcquisition2 = new FixedAssetAcquisition();
        fixedAssetAcquisition2.setId(fixedAssetAcquisition1.getId());
        assertThat(fixedAssetAcquisition1).isEqualTo(fixedAssetAcquisition2);
        fixedAssetAcquisition2.setId(2L);
        assertThat(fixedAssetAcquisition1).isNotEqualTo(fixedAssetAcquisition2);
        fixedAssetAcquisition1.setId(null);
        assertThat(fixedAssetAcquisition1).isNotEqualTo(fixedAssetAcquisition2);
    }
}
