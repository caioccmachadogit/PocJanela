package com.example.santander.pocporquinho;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Santander on 12/01/17.
 */

public enum EnumSegmento {
    USER_SELECT(1),
    USER_CLASSIC(2);

    EnumSegmento(int value) {
        this.value = value;
    }

    private static Map map = new HashMap<>();

    static {
        for (EnumSegmento seg : EnumSegmento.values()) {
            map.put(seg.value, seg);
        }
    }

    public static EnumSegmento valueOf(int segmento) {
        return (EnumSegmento) map.get(segmento);
    }

    private int value;

    public int getValue(){
        return value;
    }
}
