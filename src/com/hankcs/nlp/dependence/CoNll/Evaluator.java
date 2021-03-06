package com.hankcs.nlp.dependence.CoNll;

import java.text.NumberFormat;

/**
 * CoNLL是NLP领域著名的评测每年举办NLP领域的赛事。这部分移植用来做一些基准测试。2018.03.01
 *
 * 测试工具
 *
 * @author hankcs
 */
public class Evaluator {
    float U, L, D, A;
    int sentenceCount;
    long start;

    public Evaluator() {
        start = System.currentTimeMillis();
    }

    public void e(CoNLLSentence right, CoNLLSentence test) {
        ++sentenceCount;
        A += right.word.length;
        for (int i = 0; i < test.word.length; ++i) {
            if (test.word[i].HEAD.ID == right.word[i].HEAD.ID) {
                ++U;
                if (right.word[i].DEPREL.equals(test.word[i].DEPREL)) {
                    ++L;
                    if (test.word[i].HEAD.ID != 0) {
                        ++D;
                    }
                }
            }
        }
    }

    public float getUA() {
        return U / A;
    }

    public float getLA() {
        return L / A;
    }

    public float getDA() {
        return D / (A - sentenceCount);
    }

    @Override
    public String toString() {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        StringBuilder sb = new StringBuilder();
        sb.append("UA: ");
        sb.append(percentFormat.format(getUA()));
        sb.append('\t');
        sb.append("LA: ");
        sb.append(percentFormat.format(getLA()));
        sb.append('\t');
        sb.append("DA: ");
        sb.append(percentFormat.format(getDA()));
        sb.append('\t');
        sb.append("sentences: ");
        sb.append(sentenceCount);
        sb.append('\t');
        sb.append("speed: ");
        sb.append(sentenceCount / (float) (System.currentTimeMillis() - start) * 1000);
        sb.append(" sent/s");
        return sb.toString();
    }
}
