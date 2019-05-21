package com.jaydenxiao.voicemanager.vest.utils;

import java.io.Serializable;

/**
 * Created by kekex on 2019/5/21.
 */

public class TypeEntity implements Serializable{


    /**
     * code : 0
     * message : success
     * data : {"status":0,"pid":"70","product_name":"Cashcash Pro"}
     */

    private int code;
    private String message;
    private DataBean data;

    @Override
    public String toString() {
        return "TypeEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", pid='" + pid + '\'' +
                    ", product_name='" + product_name + '\'' +
                    '}';
        }

        /**
         * status : 0
         * pid : 70
         * product_name : Cashcash Pro
         */


        private int status;
        private String pid;
        private String product_name;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }
}
