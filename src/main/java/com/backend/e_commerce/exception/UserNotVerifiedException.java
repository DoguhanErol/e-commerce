package com.backend.e_commerce.exception;

public class UserNotVerifiedException extends Exception{
    private boolean newEmailSent;

    public UserNotVerifiedException(boolean newEmailSent){
        this.newEmailSent = newEmailSent;
    }
    public boolean isNewEmailSent(){
        return newEmailSent;
    }
}
