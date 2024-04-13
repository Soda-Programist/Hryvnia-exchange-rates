package org.example.lab.model;

import jakarta.persistence.*;

@Entity
@Table(name="exchangeRates")
public class HryvniaExchangeItem {

    public HryvniaExchangeItem() {

    }

    public HryvniaExchangeItem(int id, String digitalCode, String letterCode, String name, String officialCourse){
        ID=id;
        DigitalCode=digitalCode;
        LetterCode=letterCode;
        Name=name;
        OfficialCourse=officialCourse;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int ID;
    @Column(name = "digitalCode")
    public String DigitalCode;
    @Column(name = "letterCode")
    public String LetterCode;
    @Column(name = "name")
    public String Name;
    @Column(name = "officialCourse")
    public String OfficialCourse;

    public void SetID(int id){
        ID=id;
    }
}
