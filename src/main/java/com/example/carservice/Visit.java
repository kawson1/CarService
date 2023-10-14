package com.example.carservice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Visit implements Serializable {
    public Date date;
    public String VIN;
    public Garage garage;
    public Client client;
    public List<Fault> faults;
}
