
package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.CidEstDao;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Fornecedor;

public class FXMLAnchorPaneCadastrosFornecedorDialogController implements Initializable {

    @FXML
    private TextField FornecedorNomeFantasia;
    @FXML
    private TextField FornecedorRazaoSocial;
    @FXML
    private TextField FornecedorEndereco;
    @FXML
    private TextField FornecedorBairro;
    @FXML
    private TextField FornecedorEmail;
    @FXML
    private TextField FornecedorCNPJ;
    @FXML
    private TextField FornecedorISocial;
    @FXML
    private TextField FornecedorNumero;
    @FXML
    private TextField FornecedorComp;
    @FXML
    private TextField FornecedorTelefone;
    @FXML
    private ComboBox FornecedorboxTipoEmpresa;
    @FXML
    private ComboBox FornecedorBoxUF;
    @FXML
    private ComboBox FornecedorBoxCidade;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btmCancelar;
    
    
    private Stage dialogStage;
    private boolean btnConfirmarClicked = false;
    private Fornecedor fornecedor;
    
    //OBJETOS UTILIZADOS
    private String uf;
    private String cidade;
    
    //atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CidEstDao cidestDao = new CidEstDao();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cidestDao.setConnection(connection);
       carregarEstado();
    }    
    
    	//CONVERTE UMA STRING EM FORMATO DATE
    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
    
    //CARREGA OS ESTADOS E OS ADD EM UMA COMBOBOX    
    public void carregarEstado(){
        List<String> listaEstado = cidestDao.listaEstados();
        listaEstado.forEach((nomeEstado) -> {
            FornecedorBoxUF.getItems().add(nomeEstado);
        });
    }
    
    //TORNA OS NOMES COMPLETOS EM SIGLAS
    private String EstadoNomeParaSigla(){
        String estado = null;
        if("Acre".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "AC";
        if("Alagoas".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "AL";
        if("Amazonas".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "AM";
        if("Amapá".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "AP";
        if("Bahia".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "BA";
        if("Ceará".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "CE";
        if("Distrito Federal".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "DF";
        if("Espírito Santo".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "ES";
        if("Goiás".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "GO";
        if("Maranhão".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "MA";
        if("Minas Gerais".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "MG";
        if("Mato Grosso do Sul".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "MS";
        if("Mato Grosso".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "MT";
        if("Pará".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "PA";
        if("Paraíba".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "PB";
        if("Pernambuco".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "PE";
        if("Piauí".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "PI";
        if("Paraná".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "PR";
        if("Rio de Janeiro".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "RJ";
        if("Rio Grande do Norte".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "RN";
        if("Rondônia".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "RO";
        if("Roraima".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "RR";
        if("Rio Grande do Sul".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "RS";
        if("Santa Catarina".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "SC";
        if("Sergipe".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "SE";
        if("São Paulo".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "SP";
        if("Tocantins".equals(FornecedorBoxUF.valueProperty().get()) ) estado = "TO";
        return estado;
    }
	
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }

    public void setBtnConfirmarClicked(boolean btnConfirmarClicked) {
        this.btnConfirmarClicked = btnConfirmarClicked;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        this.FornecedorNomeFantasia.setText(fornecedor.getFantasia());
        this.FornecedorRazaoSocial.setText(fornecedor.getRsocial());
        this.FornecedorEndereco.setText(fornecedor.getEndereco());
        this.FornecedorBairro.setText(fornecedor.getBairro());
        this.FornecedorEmail.setText(fornecedor.getEmail());
        //Tipo de Empresa
        this.FornecedorCNPJ.setText(String.valueOf(fornecedor.getCnpj()));
        this.FornecedorISocial.setText(String.valueOf(fornecedor.getIncsocial()));
        this.FornecedorNumero.setText(String.valueOf(fornecedor.getNumero()));
        this.FornecedorComp.setText(String.valueOf(fornecedor.getComplemento()));
        //uf
        //cidade
        this.FornecedorTelefone.setText(String.valueOf(fornecedor.getTelefone()));
    }
    
    @FXML
    public void handleBtnConfirmar() throws Exception{
        fornecedor.setFantasia(FornecedorNomeFantasia.getText());
        fornecedor.setRsocial(FornecedorRazaoSocial.getText());
        fornecedor.setEndereco(FornecedorEndereco.getText());
        fornecedor.setBairro(FornecedorBairro.getText());
        fornecedor.setEmail(FornecedorEmail.getText());
       //Tipo de Empresa
        fornecedor.setCnpj(Integer.parseInt(FornecedorCNPJ.getText()));
        fornecedor.setIncsocial(Integer.parseInt(FornecedorISocial.getText()));
        fornecedor.setNumero(Integer.parseInt(FornecedorNumero.getText()));
        fornecedor.setComplemento(Integer.parseInt(FornecedorComp.getText()));
        //uf
        //cidade
        fornecedor.setTelefone(FornecedorTelefone.getText());
        btnConfirmarClicked = true;
        dialogStage.close();
    }
    
    @FXML
    public void handleBtnCancelar(){
        dialogStage.close();
    }
    
}
