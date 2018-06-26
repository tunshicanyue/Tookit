package com.xb.canyue;


import com.xb.toolkit.bean.XBean;

public class RefreshTokenBean extends XBean {

    /**
     * message : 成功
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC96aGgtYXBpLnRlc3RcL2xvZ2luQnlDb2RlIiwiaWF0IjoxNTI2MzQ4Mzk0LCJleHAiOjE1MjYzNTE5OTQsIm5iZiI6MTUyNjM0ODM5NCwianRpIjoiWm4zTjFab1RPWjFqRGY5NCIsInN1YiI6MTAxMjE2LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.vAOWRUhsIoMU4NmcVS9yTdOmHmmlfHcSlkg0146X5hc","type":"Bearer","expires_in":"3600"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC96aGgtYXBpLnRlc3RcL2xvZ2luQnlDb2RlIiwiaWF0IjoxNTI2MzQ4Mzk0LCJleHAiOjE1MjYzNTE5OTQsIm5iZiI6MTUyNjM0ODM5NCwianRpIjoiWm4zTjFab1RPWjFqRGY5NCIsInN1YiI6MTAxMjE2LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.vAOWRUhsIoMU4NmcVS9yTdOmHmmlfHcSlkg0146X5hc
         * type : Bearer
         * expires_in : 3600
         */

        private String token;
        private String type;
        private String expires_in;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "token='" + token + '\'' +
                    ", type='" + type + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RefreshTokenBean{" +
                "data=" + data +
                '}';
    }
}
