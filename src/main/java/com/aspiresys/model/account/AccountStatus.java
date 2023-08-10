package com.aspiresys.model.account;

import com.aspiresys.controller.Admin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AccountStatus {
    private static final Logger logger = Logger.getLogger(AccountStatus.class.getName());
    private static final String authentication;
    private static String userName;
    private static int status=0;
    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);
        } catch (IOException exception) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
        authentication = properties.getProperty("authentication");
        try {
            logger.setUseParentHandlers(false);
            File file = new File(authentication);
            boolean append = file.exists();
            FileHandler fileHandler = new FileHandler(authentication, append);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException exception) {
            logger.info("Error in Authentication "+exception);
        }

    }
    public AccountStatus(String userName, int status) {
        AccountStatus.userName = userName;
        AccountStatus.status = status;
        logger.info("Account Logged in userName " + userName);
    }
    public AccountStatus(){
        logger.info("Account Logged out userName " + userName);
        AccountStatus.userName=null;
        AccountStatus.status=0;

    }


    public static class AccountStatusNote {
        AccountStatusNote(){

        }
        public static boolean getStatus(){

            return status != 0;
        }
        public static String getUserName(){
            return userName;
        }

    }


}
