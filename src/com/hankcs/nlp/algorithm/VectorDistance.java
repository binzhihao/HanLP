package com.hankcs.nlp.algorithm;

import com.hankcs.nlp.dictionary.base.synonym.Synonym;
import com.hankcs.nlp.dictionary.common.CommonSynonymDictionary;

import java.util.List;

/**
 * 词向量距离计算
 */
public class VectorDistance {
    public static long compute(long[] arrayA, long[] arrayB) {
        final int m = arrayA.length;
        final int n = arrayB.length;
        if (m == 0 || n == 0) return 1;

        long total = 0;
        for (long va : arrayA) {
            long min_distance = Long.MAX_VALUE;
            for (long vb : arrayB) {
                min_distance = Math.min(min_distance, Math.abs(va - vb));
            }
            total += min_distance;
        }

        return total / m;
    }

    public static double compute(List<CommonSynonymDictionary.SynonymItem> synonymItemListA, List<CommonSynonymDictionary.SynonymItem> synonymItemListB) {
        double total = 0;
        for (CommonSynonymDictionary.SynonymItem itemA : synonymItemListA) {
            long min_distance = Long.MAX_VALUE;
            for (CommonSynonymDictionary.SynonymItem itemB : synonymItemListB) {
                long distance;
                if (itemA.type != Synonym.Type.UNDEFINED && itemB.type != Synonym.Type.UNDEFINED) {
                    distance = Math.abs(itemA.entry.id - itemB.entry.id);
                } else {
                    // 用编辑距离凑合一个
                    distance = EditDistance.ed(itemA.entry.realWord, itemB.entry.realWord) * 1000000;
                }
                min_distance = Math.min(min_distance, distance);
            }
            total += min_distance;
        }

        return total;
    }
}
