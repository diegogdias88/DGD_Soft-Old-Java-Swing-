package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.FornecedorDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Fornecedor;

public class FXMLAnchorPaneCadastrosFornecedorController implements Initializable {
    @FXML
    private Button btnInserir;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<Fornecedor> tableViewFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> tableColumnFornecedorNome;
    @FXML
    private TableColumn<Fornecedor, String> tableColumnFornecedorCNPJ;
    @FXML
    private Label FornecedorCodigo;
    @FXML
    private Label FornecedorNome;
    @FXML
    private Label FornecedorCNPJ;
    @FXML
    private Label FornecedorTipoEmpresa;
    @FXML
    private Label FornecedorRSocial;
    @FXML
    private Label FornecedorIncSocial;
    @FXML
    private Label FornecedorEnd;
    @FXML
    private Label FornecedorNumero;
    @FXML
    private Label FornecedorComplemento;
    @FXML
    private Label FornecedorBairro;
    @FXML
    private Label FornecedorUF;
    @FXML
    private Label FornecedorCidade;
    @FXML
    private Label FornecedorEmail;
    @FXML
    private Label FornecedorTelefone;

    
    @FXML
    private List<Fornecedor> listFornecedor;
    private ObservableList<Fornecedor> observableListFornecedor;
    
    //atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final FornecedorDAO fornecedorDao = new FornecedorDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fornecedorDao.setConnection(connection);
        carregarTableVielFornecedor();
        
        //listem acionado diante de qualquer alterações na seleção de itens da Tableview
        tableViewFornecedor.getSelectionModel().selectedItemProperty().addListener(
        (observable,oldValue,newValue) -> selecionarItemTableViewFornecedor(newValue));
    }    
    
    public void carregarTableVielFornecedor(){
        //configura as colunas para exibir o nome e cpf na tabela (TableView)
        tableColumnFornecedorNome.setCellValueFactory(new PropertyValueFactory<>("fantasia"));
        tableColumnFornecedorCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        //chama o metodo listar
        listFornecedor = fornecedorDao.listar();
        //converte um observableList para um arraylist
        observableListFornecedor = FXCollections.observableArrayList(listFornecedor);
        //passa o observablelist para a tableview
        tableViewFornecedor.setItems(observableListFornecedor);
    }
    
    public void selecionarItemTableViewFornecedor(Fornecedor fornecedor){
        if(fornecedor != null){
            FornecedorCodigo.setText(String.valueOf(fornecedor.getId()));
            FornecedorNome.setText(fornecedor.getFantasia());
            FornecedorCNPJ.setText(String.valueOf(fornecedor.getCnpj()));
            FornecedorTipoEmpresa.setText(String.valueOf(fornecedor.getTipoempresa()));
            FornecedorRSocial.setText(fornecedor.getRsocial());
            FornecedorIncSocial.setText(String.valueOf(fornecedor.getIncsocial()));
            FornecedorEnd.setText(fornecedor.getEndereco());
            FornecedorNumero.setText(String.valueOf(fornecedor.getNumero()));
            FornecedorComplemento.setText(String.valueOf(fornecedor.getComplemento()));
            FornecedorBairro.setText(fornecedor.getBairro());
            FornecedorUF.setText(fornecedor.getUf());
            FornecedorCidade.setText(fornecedor.getCidade());
            FornecedorEmail.setText(fornecedor.getEmail());
            FornecedorTelefone.setText(fornecedor.getTelefone());
        }else{
            FornecedorCodigo.setText("");
            FornecedorNome.setText("");
            FornecedorCNPJ.setText("");
            FornecedorTipoEmpresa.setText("");
            FornecedorRSocial.setText("");
            FornecedorIncSocial.setText("");
            FornecedorEnd.setText("");
            FornecedorNumero.setText("");
            FornecedorComplemento.setText("");
            FornecedorBairro.setText("");
            FornecedorUF.setText("");
            FornecedorCidade.setText("");
            FornecedorEmail.setText("");
            FornecedorTelefone.setText("");
        }
    }
    
    @FXML
    public void handleBtnInserir() throws IOException{
        Fornecedor fornecedor = new Fornecedor();
        boolean btnConfirmarClicked = showFXMLAnchorPaneCadastrosFornecedorDialog(fornecedor);
        
        if(btnConfirmarClicked){
            fornecedorDao.inserir(fornecedor);
            carregarTableVielFornecedor();
        }
    }
    
    @FXML
    public void handleBtnAlterar() throws IOException{
        Fornecedor fornecedor = tableViewFornecedor.getSelectionModel().getSelectedItem();
        if(fornecedor != null){
            boolean btnConfirmarClicked = showFXMLAnchorPaneCadastrosFornecedorDialog(fornecedor);
            if(btnConfirmarClicked){
                fornecedorDao.alterar(fornecedor);
                carregarTableVielFornecedor();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Fornecedor na Tabela");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtnExcluir()throws IOException{
        Fornecedor fornecedor = tableViewFornecedor.getSelectionModel().getSelectedItem();
        if(fornecedor != null){
            fornecedorDao.remover(fornecedor);
            carregarTableVielFornecedor();
        }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Por favor, escolha um Fornecedor na Tabela");
           alert.show(); 
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosFornecedorDialog(Fornecedor fornecedor) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(FXMLAnchorPaneCadastrosFornecedorDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosFornecedorDialog.fxml"));
        loader.setLocation(FXMLAnchorPaneCadastrosFornecedorDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosFornecedorDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //cria um estagio de dialogo(Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastros de Fornecedor");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //setando o cliente no controller
        FXMLAnchorPaneCadastrosFornecedorDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFornecedor(fornecedor);
        
        //mostra o dialog  e espera ate que o usuario o feche
        dialogStage.showAndWait();
        
        return controller.isBtnConfirmarClicked();
    }
}
