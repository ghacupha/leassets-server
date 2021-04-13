package tech.leassets.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.leassets.web.rest.TestUtil;

public class FixedAssetNetBookValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetNetBookValue.class);
        FixedAssetNetBookValue fixedAssetNetBookValue1 = new FixedAssetNetBookValue();
        fixedAssetNetBookValue1.setId(1L);
        FixedAssetNetBookValue fixedAssetNetBookValue2 = new FixedAssetNetBookValue();
        fixedAssetNetBookValue2.setId(fixedAssetNetBookValue1.getId());
        assertThat(fixedAssetNetBookValue1).isEqualTo(fixedAssetNetBookValue2);
        fixedAssetNetBookValue2.setId(2L);
        assertThat(fixedAssetNetBookValue1).isNotEqualTo(fixedAssetNetBookValue2);
        fixedAssetNetBookValue1.setId(null);
        assertThat(fixedAssetNetBookValue1).isNotEqualTo(fixedAssetNetBookValue2);
    }
}
