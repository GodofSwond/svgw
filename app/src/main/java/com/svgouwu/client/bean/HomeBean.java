package com.svgouwu.client.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by whq on 2017/12/26.
 */

public class HomeBean implements Serializable {

    public static class DataBean {
        /**
         * 1 : {"a":"er","a1":"er","a2":"er"}
         * 2 : {"a":"er","a1":"er","a2":"er"}
         * 3 : {"a":"er","a1":"er","a2":"er"}
         */

        @SerializedName("1")
        private _$1Bean _$1;
        @SerializedName("2")
        private _$2Bean _$2;
        @SerializedName("3")
        private _$3Bean _$3;

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public _$2Bean get_$2() {
            return _$2;
        }

        public void set_$2(_$2Bean _$2) {
            this._$2 = _$2;
        }

        public _$3Bean get_$3() {
            return _$3;
        }

        public void set_$3(_$3Bean _$3) {
            this._$3 = _$3;
        }

        public static class _$1Bean {
            /**
             * a : er
             * a1 : er
             * a2 : er
             */

            private String a;
            private String a1;
            private String a2;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getA1() {
                return a1;
            }

            public void setA1(String a1) {
                this.a1 = a1;
            }

            public String getA2() {
                return a2;
            }

            public void setA2(String a2) {
                this.a2 = a2;
            }
        }

        public static class _$2Bean {
            /**
             * a : er
             * a1 : er
             * a2 : er
             */

            private String a;
            private String a1;
            private String a2;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getA1() {
                return a1;
            }

            public void setA1(String a1) {
                this.a1 = a1;
            }

            public String getA2() {
                return a2;
            }

            public void setA2(String a2) {
                this.a2 = a2;
            }
        }

        public static class _$3Bean {
            /**
             * a : er
             * a1 : er
             * a2 : er
             */

            private String a;
            private String a1;
            private String a2;

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getA1() {
                return a1;
            }

            public void setA1(String a1) {
                this.a1 = a1;
            }

            public String getA2() {
                return a2;
            }

            public void setA2(String a2) {
                this.a2 = a2;
            }
        }
    }
}
