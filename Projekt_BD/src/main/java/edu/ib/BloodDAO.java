package edu.ib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodDAO {

    protected DBUtil dbUtil;
    protected TextArea consoleTextArea;

    protected BloodDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        this.dbUtil = dbUtil;
        this.consoleTextArea = consoleTextArea;
    }

    protected ObservableList<Magazyn> showOnlyOne(String grupa) throws SQLException, ClassNotFoundException {

        String kom = "select * from magazyn where grupa_krwi='" + grupa + "';";

            ResultSet result = dbUtil.dbExecuteQuery(kom);
            ObservableList<Magazyn> spis = getMagazynList(result);
            consoleTextArea.appendText("Stan grupy krwi " + grupa + " w magazynie" + "\n");
            return spis;

    }

    protected ObservableList<Magazyn> showAllMagazyns() throws SQLException, ClassNotFoundException {

        String kom = "SELECT * FROM Magazyn;";

            ResultSet result = dbUtil.dbExecuteQuery(kom);
            ObservableList<Magazyn> spis = getMagazynList(result);
            consoleTextArea.appendText("Stan magazynow krwi wszystkich bankow" + "\n");
            return spis;

    }

    protected ObservableList<Magazyn> getMagazynList(ResultSet rs) throws SQLException {

        ObservableList<Magazyn> magazynList = FXCollections.observableArrayList();

        while (rs.next()) {
            Magazyn w = new Magazyn();
            w.setId(rs.getInt("id"));
            w.setBank_id(rs.getInt("bank_id"));
            w.setNazwa(rs.getString("nazwa"));
            w.setGrupa_krwi(rs.getString("grupa_krwi"));
            w.setIlosc(rs.getInt("ilosc"));
            w.setStan(rs.getString("stan"));
            magazynList.add(w);
        }

        return magazynList;
    }

    protected void Utilization(){
        String info;
        try {
            info = dbUtil.dbExecuteUtilization("{call utylizacja(?)}");
            consoleTextArea.appendText("Kontrola stanu krwi" + "\n");
            consoleTextArea.appendText(info + "\n");
        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("Podczas utylizacji nastapil blad"+ "\n");
        }
    }

}