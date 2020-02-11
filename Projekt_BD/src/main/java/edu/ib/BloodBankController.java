package edu.ib;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BloodBankController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private SplitPane akcja;

    @FXML
    private GridPane pobierz;

    @FXML
    private MenuButton menuWG2;

    @FXML
    private MenuItem mitmAp2;

    @FXML
    private MenuItem mitmAm2;

    @FXML
    private MenuItem mitmBp2;

    @FXML
    private MenuItem mitmBm2;

    @FXML
    private MenuItem mitmABp2;

    @FXML
    private MenuItem mitmABm2;

    @FXML
    private MenuItem mitm0p2;

    @FXML
    private MenuItem mitm0m2;

    @FXML
    private Button downloadBloodButton;

    @FXML
    private TextField txtIlosc;

    @FXML
    private GridPane oddaj;

    @FXML
    private TextField txtImie;

    @FXML
    private TextField txtNazwisko;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TextField txtTelefon;

    @FXML
    private MenuButton menuWG1;

    @FXML
    private MenuItem mitmAp1;

    @FXML
    private MenuItem mitmAm1;

    @FXML
    private MenuItem mitmBp1;

    @FXML
    private MenuItem mitmBm1;

    @FXML
    private MenuItem mitmABp1;

    @FXML
    private MenuItem mitmABm1;

    @FXML
    private MenuItem mitm0p1;

    @FXML
    private MenuItem mitm0m1;

    @FXML
    private TextField txtWaga;

    @FXML
    private Button addBloodButton;

    @FXML
    private TextField txtBank;

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private Button disconnectButton;

    @FXML
    private Button showBloodButton;

    @FXML
    private MenuButton menuWG3;

    @FXML
    private MenuItem mitmAll3;

    @FXML
    private MenuItem mitmAp3;

    @FXML
    private MenuItem mitmAm3;

    @FXML
    private MenuItem mitmBp3;

    @FXML
    private MenuItem mitmBm3;

    @FXML
    private MenuItem mitmABp3;

    @FXML
    private MenuItem mitmABm3;

    @FXML
    private MenuItem mitm0p3;

    @FXML
    private MenuItem mitm0m3;

    @FXML
    private VBox logowanie;

    @FXML
    private ImageView image;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField txtUwaga;

    @FXML
    private Button connectButton;

    @FXML
    private TableView<Magazyn> BloodTable;

    @FXML
    private TableColumn<Magazyn, String> idCol;

    @FXML
    private TableColumn<Magazyn, String> nameCol;

    @FXML
    private TableColumn<Magazyn, String> groupCol;

    @FXML
    private TableColumn<Magazyn, String> numberCol;

    @FXML
    private TableColumn<Magazyn, String> conditionCol;



    private DBUtil dbUtil;
    private BankDAO bankDAO;
    private CentrumDAO centrumDAO;
    private BloodDAO bloodDAO;

    @FXML
    void connectButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        dbUtil = new DBUtil(userTextField.getText(), passwordTextField.getText());
        if(userTextField.getText().equals("centrum")){
            centrumDAO = new CentrumDAO(dbUtil, consoleTextArea);
            bloodDAO = new BloodDAO(dbUtil,consoleTextArea);
        }
       else  if(userTextField.getText().contains("bank")){
            bankDAO = new BankDAO(dbUtil, consoleTextArea);
            bloodDAO = new BloodDAO(dbUtil,consoleTextArea);
        }

        try {
            dbUtil.dbConnect();
            consoleTextArea.appendText("Przyznano dostep uzytkownikowi \"" + userTextField.getText() + "\"." + "\n");
            bloodDAO.Utilization();
            logowanie.setVisible(false);
            akcja.setVisible(true);
            if (centrumDAO != null) {
                oddaj.setVisible(true);
            } else {
                pobierz.setVisible(true);
            }
        }catch (SQLException | ClassNotFoundException e){
            txtUwaga.setVisible(true);
            txtUwaga.setText("Brak dostepu!");
        }
    }

    @FXML
    void disconnectButtonPressed(ActionEvent event) {
        TextField textField[]=new TextField[]{userTextField,passwordTextField,txtIlosc,txtWaga,txtTelefon,txtNazwisko,txtImie,txtBank,txtUwaga};
        centrumDAO=null;
        bankDAO=null;
        logowanie.setVisible(true);
        akcja.setVisible(false);
        pobierz.setVisible(false);
        oddaj.setVisible(false);
        txtUwaga.setVisible(false);
        consoleTextArea.clear();
        BloodTable.getItems().clear();
        for (int i = 0; i < textField.length; i++) {
            textField[i].clear();
        }
        menuWG1.setText("Wybierz grupę");
        menuWG2.setText("Wybierz grupę");
        menuWG3.setText("Wszystkie grupy krwi");
    }

    @FXML
    void addBloodButtonPressed(ActionEvent event) {
        String im = txtImie.getText();
        String naz = txtNazwisko.getText();
        String grupa = menuWG1.getText();
        String data= pickDate.getValue().toString();
        String tel = txtTelefon.getText();

        if(grupa.equals("Wybierz grupę")) {
            consoleTextArea.setText("Nie wybrano grupy krwi. \n");
        }else if(im.equals("") || naz.equals("")) {
            consoleTextArea.setText("Nie wpisano imienia i/lub nazwiska. \n");
        }else if(tel.length()!=9) {
            consoleTextArea.setText("Niepoprawny numer telefonu. \n");
        }else {
                try {
                    int numer_tel = Integer.valueOf(tel);
                    double waga = Double.valueOf(txtWaga.getText());
                    int id = Integer.valueOf(txtBank.getText());
                    centrumDAO.AddBlood(im, naz, data, tel, waga, grupa, id);
                } catch (NumberFormatException e) {
                    consoleTextArea.setText("Zly format danych. Numer telefonu, waga i id banku musza być liczbami. \n");
                } catch (NullPointerException e) {
                    consoleTextArea.setText("Nie wybrano daty urodzenia. \n");
                }
            }
        }


    @FXML
    void downloadBloodButtonPressed(ActionEvent event) {
        String grupa = menuWG2.getText();
        try {
        int ilosc = Integer.valueOf(txtIlosc.getText());
        bankDAO.DownloadBlood(grupa,ilosc);
        } catch (NumberFormatException e) {
            consoleTextArea.setText("Zly format danych. Ilosc jednostek musi być liczba. \n");
        } catch (NullPointerException e) {
            consoleTextArea.setText("Nie wpisano ilosci jednostek. \n");
        }
    }

    private void populateBlood(ObservableList<Magazyn> bloodData) {
        BloodTable.setItems(bloodData);
    }

    @FXML
    void showBloodButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            BloodTable.getItems().clear();
        if(menuWG3.getText().equals("Wszystkie grupy krwi")){
            ObservableList<Magazyn> bloodData = bloodDAO.showAllMagazyns();
            populateBlood(bloodData);
        }
        else{
            ObservableList<Magazyn> bloodData = bloodDAO.showOnlyOne(menuWG3.getText());
            populateBlood(bloodData);
        }

        } catch (SQLException e) {
            consoleTextArea.appendText("Problem przy przegladaniu magazynu.\n");
            throw e;
        }
    }

    void Wybierz(MenuButton mb){
        ObservableList<MenuItem> list = mb.getItems();
            for (int i = 0; i < list.size(); i++) {
                MenuItem itm = list.get(i);
                String blood = itm.getText();
                itm.setOnAction(e -> {
                    mb.setText(blood);
                });
            }
        }

    @FXML
    void initialize() {
        assert akcja != null : "fx:id=\"akcja\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert pobierz != null : "fx:id=\"pobierz\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert menuWG2 != null : "fx:id=\"menuWG2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAp2 != null : "fx:id=\"mitmAp2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAm2 != null : "fx:id=\"mitmAm2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBp2 != null : "fx:id=\"mitmBp2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBm2 != null : "fx:id=\"mitmBm2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABp2 != null : "fx:id=\"mitmABp2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABm2 != null : "fx:id=\"mitmABm2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0p2 != null : "fx:id=\"mitm0p2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0m2 != null : "fx:id=\"mitm0m2\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert downloadBloodButton != null : "fx:id=\"downloadBloodButton\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtIlosc != null : "fx:id=\"txtIlosc\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert oddaj != null : "fx:id=\"oddaj\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtImie != null : "fx:id=\"txtImie\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtNazwisko != null : "fx:id=\"txtNazwisko\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert pickDate != null : "fx:id=\"pickDate\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtTelefon != null : "fx:id=\"txtTelefon\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert menuWG1 != null : "fx:id=\"menuWG1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAp1 != null : "fx:id=\"mitmAp1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAm1 != null : "fx:id=\"mitmAm1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBp1 != null : "fx:id=\"mitmBp1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBm1 != null : "fx:id=\"mitmBm1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABp1 != null : "fx:id=\"mitmABp1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABm1 != null : "fx:id=\"mitmABm1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0p1 != null : "fx:id=\"mitm0p1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0m1 != null : "fx:id=\"mitm0m1\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtWaga != null : "fx:id=\"txtWaga\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert addBloodButton != null : "fx:id=\"addBloodButton\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtBank != null : "fx:id=\"txtBank\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert consoleTextArea != null : "fx:id=\"consoleTextArea\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert BloodTable != null : "fx:id=\"BloodTable\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert groupCol != null : "fx:id=\"groupCol\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert numberCol != null : "fx:id=\"numberCol\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert conditionCol != null : "fx:id=\"conditionCol\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert showBloodButton != null : "fx:id=\"showBloodButton\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert menuWG3 != null : "fx:id=\"menuWG3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAll3 != null : "fx:id=\"mitmAll3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAp3 != null : "fx:id=\"mitmAp3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmAm3 != null : "fx:id=\"mitmAm3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBp3 != null : "fx:id=\"mitmBp3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmBm3 != null : "fx:id=\"mitmBm3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABp3 != null : "fx:id=\"mitmABp3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitmABm3 != null : "fx:id=\"mitmABm3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0p3 != null : "fx:id=\"mitm0p3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert mitm0m3 != null : "fx:id=\"mitm0m3\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert logowanie != null : "fx:id=\"logowanie\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert userTextField != null : "fx:id=\"userTextField\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert txtUwaga != null : "fx:id=\"txtUwaga\" was not injected: check your FXML file 'dbFX.fxml'.";

        Wybierz(menuWG1);
        Wybierz(menuWG2);
        Wybierz(menuWG3);

    }
}
