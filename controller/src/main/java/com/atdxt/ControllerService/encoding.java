package com.atdxt.ControllerService;

import java.util.Base64;

public class encoding {
    public static void main(String[] args){
        String tex= "kaviya";
        String encodetext = Base64.getEncoder().encodeToString(tex.getBytes());
        System.out.println("encoded text:"+encodetext);
    }
}
