package sportify.bean;

public class LoginBean {

    public boolean userCheck(String username){
        return !username.equals("");
    }

    public boolean passCheck(String password){
        return !password.isEmpty();
    }
}
