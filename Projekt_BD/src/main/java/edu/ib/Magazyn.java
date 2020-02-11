package edu.ib;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Magazyn {

    private IntegerProperty id;
    private IntegerProperty bank_id;
    private StringProperty nazwa;
    private StringProperty grupa_krwi;
    private IntegerProperty ilosc;
    private StringProperty stan;

    public Magazyn() {
        id=new SimpleIntegerProperty();
        bank_id=new SimpleIntegerProperty();
        nazwa=new SimpleStringProperty();
        grupa_krwi=new SimpleStringProperty();
        ilosc=new SimpleIntegerProperty();
        stan=new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getBank_id() {
        return bank_id.get();
    }

    public IntegerProperty bank_idProperty() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id.set(bank_id);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getGrupa_krwi() {
        return grupa_krwi.get();
    }

    public StringProperty grupa_krwiProperty() {
        return grupa_krwi;
    }

    public void setGrupa_krwi(String grupa_krwi) {
        this.grupa_krwi.set(grupa_krwi);
    }

    public int getIlosc() {
        return ilosc.get();
    }

    public IntegerProperty iloscProperty() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc.set(ilosc);
    }

    public String getStan() {
        return stan.get();
    }

    public StringProperty stanProperty() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan.set(stan);
    }
}
