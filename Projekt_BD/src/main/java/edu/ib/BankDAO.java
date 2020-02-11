package edu.ib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankDAO extends BloodDAO{

    private int id;

    public BankDAO(DBUtil dbUtil, TextArea consoleTextArea) {
        super(dbUtil, consoleTextArea);
        TakeID();
    }

    private void TakeID(){
        int end=dbUtil.getUserName().lastIndexOf('k')+1;
        int length = dbUtil.getUserName().length();
        String bank_id = dbUtil.getUserName().substring(end,length);
        id=Integer.parseInt(bank_id);
    }

    public void DownloadBlood(String grupa, int ilosc){
        String info;
        try {
            info = dbUtil.dbExecuteDownload("{call pobierz(?,?,?,?)}",grupa,id,ilosc);
            consoleTextArea.appendText("Procedura pobierania" + "\n");
            consoleTextArea.appendText(info + "\n");
        } catch (SQLException | ClassNotFoundException e) {
            consoleTextArea.appendText("Podczas pobierania krwi nastapil blad"+"\n");
        }
    }

}
