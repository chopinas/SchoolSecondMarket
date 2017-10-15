package com.pzy.chopin.schoolsecondmarket.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chopin on 2017/9/4.
 */

public class User {

    /**
     * code : 200
     * message : login success
     * data : {"id":"11","username":"pp2","email":"2@qq.com","face":""}
     */

    private int code;
    private String message;
    private UserBean data;

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

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * id : 11
         * username : pp2
         * email : 2@qq.com
         * face :
         */

        @SerializedName("id")
        private int userid;
        private String username;
        private String email;
        private String face;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
