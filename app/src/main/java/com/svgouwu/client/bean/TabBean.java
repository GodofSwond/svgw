package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/7/26.
 */

public class TabBean implements Serializable {
    public String id;
    public String title;

    public TabBean(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
