package io.github.leassets.internal.excel;

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

import static io.github.leassets.internal.AppConstants.DATETIME_FORMATTER;
import static io.github.leassets.internal.excel.ExcelTestUtil.readFile;
import static io.github.leassets.internal.excel.ExcelTestUtil.toBytes;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import io.github.leassets.internal.model.FixedAssetAcquisitionEVM;
import io.github.leassets.internal.model.sampleDataModel.CurrencyTableEVM;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This test shows how the deserializer works inside the ItemReader interface.
 */
public class ExcelFileUtilsTest {

    private ExcelDeserializerContainer container;

    @BeforeEach
    void setUp() {
        container = new ExcelDeserializerContainer();
    }

    @Test
    public void deserializeCurrenciesFile() throws Exception {
        ExcelFileDeserializer<CurrencyTableEVM> deserializer = container.currencyTableExcelFileDeserializer();

        List<CurrencyTableEVM> currencies = deserializer.deserialize(toBytes(readFile("currencies.xlsx")));

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

    @Test
    public void fixedAssetAcquisitionFile() throws Exception {
        ExcelFileDeserializer<FixedAssetAcquisitionEVM> deserializer = container.fixedAssetAcquisitionExcelFileDeserializer();

        List<FixedAssetAcquisitionEVM> evms = deserializer.deserialize(toBytes(readFile("assetAcquisitionList.xlsx")));

        assertThat(evms.size()).isEqualTo(13);

        for (int i = 0; i < 13; i++) {
            String index = String.valueOf(i + 1);
            assertThat(evms.get(i))
                .isEqualTo(
                    FixedAssetAcquisitionEVM
                        .builder()
                        .rowIndex((long) (i + 1))
                        .assetNumber((long) (i + 1))
                        .serviceOutletCode("SOL_ID " + index)
                        .assetTag("ASSET_TAG " + index)
                        .assetDescription("ASSET_DESCRIPTION " + index)
                        .purchaseDate(DATETIME_FORMATTER.format(of(2021, 1, 1).plusDays(Long.parseLong(index)).minusDays(1l)))
                        .assetCategory("ASSET_CATEGORY " + index)
                        .purchasePrice(1.1 + i)
                        .build()
                );
        }
    }
}
