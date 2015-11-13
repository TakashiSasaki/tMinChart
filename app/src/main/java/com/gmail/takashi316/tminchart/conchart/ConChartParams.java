package com.gmail.takashi316.tminchart.conchart;

enum CharacterSet {
    KANJI_STROKE_1,
    KANJI_STROKE_2,
    KANJI_STROKE_3,
    KANJI_STROKE_4,
    KANJI_STROKE_5,
    KANJI_STROKE_6,
    KANJI_STROKE_7,
    KANJI_STROKE_8,
    KANJI_STROKE_9,
    KANJI_STROKE_10,
    KANJI_STROKE_11,
    KANJI_STROKE_12,
    KANJI_STROKE_13,
    KANJI_STROKE_14,
    KANJI_STROKE_15,
    KANJI_STROKE_16,
    KANJI_STROKE_17,
    KANJI_STROKE_18,
    KANJI_STROKE_19,
    KANJI_STROKE_20,
    HIRAGANA,
    KATAKANA,
}

public class ConChartParams {
    public CharacterSet characterSet;
    public int rows;
    public int columns;
    public double maxInch;
    public double sizeRatio;
    public double contrastRatio;
    public double fontStyle;
    public double fontFamily;
}
