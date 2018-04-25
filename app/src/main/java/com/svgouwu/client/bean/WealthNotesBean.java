package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class WealthNotesBean implements Serializable {

    public List<NotesItemBean> list;

    public static class NotesItemBean {
        public String number;        //201711271403566050
        public String amount;        //50.00
        public String addtime;       //2017-11-27
        public String status;        //待审核
    }
}
