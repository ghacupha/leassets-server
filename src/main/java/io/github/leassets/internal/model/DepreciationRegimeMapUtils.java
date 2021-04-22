package io.github.leassets.internal.model;

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

import io.github.leassets.domain.enumeration.DepreciationRegime;

public class DepreciationRegimeMapUtils {

    static DepreciationRegime depreciationRegime(String stringDesignation) {

        switch (stringDesignation) {
            case "straight line basis":
                return DepreciationRegime.STRAIGHT_LINE_BASIS;
            case "declining balance basis":
                return DepreciationRegime.DECLINING_BALANCE_BASIS;
            default: throw new IllegalArgumentException("String designation: " + stringDesignation + "does not correspond to known depreciation methods");
        }
    }

    static String depreciationRegime(DepreciationRegime depreciationRegime) {

        switch (depreciationRegime) {
            case STRAIGHT_LINE_BASIS:
                return "straight line basis";
            case DECLINING_BALANCE_BASIS:
                return "declining balance basis";
            default: throw new IllegalArgumentException("You want to review your depreciation regime, just one more time!");
        }
    }
}
