package com.gmail.takashi316.tminchart.minchart;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KonojiParams {
    public float gapInch;
    public float widthInch;
    public int xGap;
    public int yGap;
    public float xDpi;
    public float yDpi;
    public int orientation;
    public int viewWidth;
    public int viewHeight;

    static public String toJson(KonojiParams konoji_params) {
        ObjectMapper object_mapper = new ObjectMapper();
        try {
            String json_string = object_mapper.writeValueAsString(konoji_params);
            return json_string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
