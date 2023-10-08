package com.example.carservice;

import java.util.Date;
import java.util.List;

enum ClientType {
    NEW, ATTENDING, VIP
}
public class Client {
    public String name;
    public String surname;
    public Date birthdate;
    public List<Visit> visitList;
    public ClientType clientType;
}
