package tech.leassets.internal.excel;

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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.leassets.LeassetsServerApp;
import tech.leassets.internal.model.sampleDataModel.CurrencyTableEVM;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.leassets.internal.excel.ExcelTestUtil.readFile;
import static tech.leassets.internal.excel.ExcelTestUtil.toBytes;

/**
 * If nothing is added in value for this test it confirms that the excel deserializer beans
 * are correctly configured. We are using the sample currency-table model from the internal
 * package but and if the user wishes additional tests can be added for custom data models.
 * The deserializer should work after being successfully injected into this test from a spring container.
 */
@SpringBootTest(classes = LeassetsServerApp.class)
public class ExcelFileUtilsIT {

    @Autowired
    private ExcelFileDeserializer<CurrencyTableEVM> currencyTableEVMExcelFileDeserializer;

    @Test
    public void deserializeCurrencyTableFile() throws Exception {
        // @formatter:off
        List<CurrencyTableEVM> currencies =
            currencyTableEVMExcelFileDeserializer.deserialize(toBytes(readFile("currencies.xlsx")));
        // @formatter:on

        assertThat(currencies.size()).isEqualTo(13);
        assertThat(currencies.get(0))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(1)
                    .country("USA")
                    .currencyCode("USD")
                    .currencyName("US DOLLAR")
                    .locality("FOREIGN")
                    .build()
            );
        assertThat(currencies.get(1))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(2)
                    .country("UNITED KINGDOM")
                    .currencyCode("GBP")
                    .currencyName("STERLING POUND")
                    .locality("FOREIGN")
                    .build()
            );
        assertThat(currencies.get(2))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(3)
                    .country("EURO-ZONE")
                    .currencyCode("EUR")
                    .currencyName("EURO")
                    .locality("FOREIGN")
                    .build()
            );
        assertThat(currencies.get(3))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(4)
                    .country("KENYA")
                    .currencyCode("KES")
                    .currencyName("KENYA SHILLING")
                    .locality("LOCAL")
                    .build()
            );
        assertThat(currencies.get(4))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(5)
                    .country("SWITZERLAND")
                    .currencyCode("CHF")
                    .currencyName("SWISS FRANC")
                    .locality("FOREIGN")
                    .build()
            );
        assertThat(currencies.get(5))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(6)
                    .country("SOUTH AFRICA")
                    .currencyCode("ZAR")
                    .currencyName("SOUTH AFRICAN RAND")
                    .locality("FOREIGN")
                    .build()
            );
        assertThat(currencies.get(12))
            .isEqualTo(
                CurrencyTableEVM
                    .builder()
                    .rowIndex(13)
                    .country("CHINA")
                    .currencyCode("CNY")
                    .currencyName("CHINESE YUAN")
                    .locality("FOREIGN")
                    .build()
            );
    }
}
