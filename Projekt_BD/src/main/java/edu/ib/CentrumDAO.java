package edu.ib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CentrumDAO extends BloodDAO {

    public CentrumDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        super(dbUtil, consoleTextArea);
    }


    public void AddBlood(String imie, String nazwisko, String data, String telefon, double waga, String grupa, int id){
        String info;
        try {
            info = dbUtil.dbExecuteAdd("{call dodaj_dawce(?,?,?,?,?,?,?,?)}",imie,nazwisko,data,telefon,waga,grupa,id);
            consoleTextArea.appendText("Procedura dodawania" + "\n");
            consoleTextArea.appendText(info + "\n");
        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("Podczas dodawania krwi nastapil blad"+ "\n");
        }
    }

}
