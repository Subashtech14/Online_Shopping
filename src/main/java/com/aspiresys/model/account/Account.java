package com.aspiresys.model.account;

public record Account (String Username,String Password,String Role,String Description,String Phone_Number,String Email) {

    @Override
    public String toString() {
        return "Account" +
                " Username = " + Username +
                ", Password = " + Password +
                ", Role = " + Role +
                ", Description = " + Description +
                ", Phone_Number = " + Phone_Number +
                ", Email = " + Email  ;
    }
}
