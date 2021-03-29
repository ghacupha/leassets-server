package io.github.leassets.internal.model;

import io.github.leassets.domain.enumeration.DepreciationRegime;

import static io.github.leassets.internal.AppConstants.DECLINING_BALANCE_BASIS_DEPRECIATION_ID;
import static io.github.leassets.internal.AppConstants.STRAIGHT_LINE_BASIS_DEPRECIATION_ID;

/**
 * Common mapping for the depreciation regime
 */
public class DepreciationRegimeMapUtils {

    public static DepreciationRegime stringToDepreciationRegime(String depreciationRegimeId) {

        if (depreciationRegimeId == null) {
            return DepreciationRegime.STRAIGHT_LINE_BASIS;
        }

        switch (depreciationRegimeId) {
            case STRAIGHT_LINE_BASIS_DEPRECIATION_ID :
                return DepreciationRegime.STRAIGHT_LINE_BASIS;
            case DECLINING_BALANCE_BASIS_DEPRECIATION_ID:
                return DepreciationRegime.DECLINING_BALANCE_BASIS;
            default:
                return DepreciationRegime.STRAIGHT_LINE_BASIS;
        }
    }

    public static String depreciationRegimeToString(DepreciationRegime depreciationRegimeId) {

        if (depreciationRegimeId == null) {

            return STRAIGHT_LINE_BASIS_DEPRECIATION_ID;
        }

        switch (depreciationRegimeId) {
            case DECLINING_BALANCE_BASIS:
                return DECLINING_BALANCE_BASIS_DEPRECIATION_ID;
            case STRAIGHT_LINE_BASIS:
                return STRAIGHT_LINE_BASIS_DEPRECIATION_ID;
            default:
                return STRAIGHT_LINE_BASIS_DEPRECIATION_ID;
        }
    }
}
