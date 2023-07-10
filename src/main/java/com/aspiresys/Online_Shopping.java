/*
Title:Online Shopping System
Author: Subash M
Date: 06/07/2023
Reviewed By:
Reviewed On:
*/
package com.aspiresys;

import com.aspiresys.model.Seller;
import com.aspiresys.authentication.Authentication;

public class Online_Shopping {
    static {
        new Seller().defaultProducts();
        new Authentication().defaultAccount();
    }
    public static void main(String[] args) {

        Shopping.getStarted();
    }




    }
