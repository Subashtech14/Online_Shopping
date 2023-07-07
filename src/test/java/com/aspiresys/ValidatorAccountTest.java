package com.aspiresys;


import com.aspiresys.authentication.ValidatorAccount;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorAccountTest {
    @Test
    public void runTestCase(){
        ValidatorAccount v=new ValidatorAccount("Subash","Subash27@","Buyer","Hi there i am a buyer","1234567899","Subashtech14@gmail.com");
        Assert.assertTrue(v.validate());
    }
    @Test
    public void ProductChecker(){


    }

}