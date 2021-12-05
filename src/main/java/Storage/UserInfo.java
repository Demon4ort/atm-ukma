package Storage;


import example.entity.Model;

public class UserInfo {

    private static Model.User user;


    public static synchronized Model.User getUser() {
        return user;
    }


    public static synchronized void setUser(Model.User user) {
        UserInfo.user = user;
    }
}
