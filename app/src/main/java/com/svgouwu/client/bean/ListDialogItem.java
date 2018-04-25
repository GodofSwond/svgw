package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/5/11.
 */

public class ListDialogItem implements Serializable{

    public String name;
    public int id;

    public ListDialogItem(int id, String name) {
        this.name = name;
        this.id = id;
    }
}
