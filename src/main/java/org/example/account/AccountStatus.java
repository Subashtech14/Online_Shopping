package org.example.account;

public class AccountStatus {
    private static String username=null;
    private static int status=0;
    public AccountStatus(String username, int status) {
        AccountStatus.username = username;
        AccountStatus.status = status;
    }
    public AccountStatus(){

    }

    public static class AccountStatusNote {
        AccountStatusNote(){

        }
        public static boolean getStatus(){
            return status != 0;
        }

    }


}
