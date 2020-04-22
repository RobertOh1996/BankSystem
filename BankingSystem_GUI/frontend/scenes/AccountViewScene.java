package scenes;

import java.io.FileNotFoundException;
import java.util.List;

import accounts.Account;
import accounts.table.AccountTableDAO;
import accounts.table.AccountTableDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import transactions.TransactionDAO;
import transactions.TransactionDAOImpl;
import user.User;

public class AccountViewScene {

	public static Scene getScene(User user) {

		TableView tableView = new TableView();
		VBox vbox = new VBox(tableView);
		String driversLicense = user.getDriversLicense();
		TransactionDAO transactionDAO = new TransactionDAOImpl();
		AccountTableDAO act = new AccountTableDAOImpl(transactionDAO);
		List<Account> accounts = null;
		try {
			accounts = act.getAccounts(driversLicense);
		} catch (FileNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		TableColumn<String, Account> column1 = new TableColumn<>("Account Type");
		column1.setCellValueFactory(new PropertyValueFactory<>("AccountType"));
		TableColumn<String, Account> column2 = new TableColumn<>("Balance");
		column2.setCellValueFactory(new PropertyValueFactory<>("Balance"));
		
		column1.setPrefWidth(220);
		column2.setPrefWidth(70);
		
		tableView.getColumns().addAll(column1, column2);
		for(Account account: accounts) {
			tableView.getItems().add(account);
		}
		
		tableView.setOnMouseClicked((MouseEvent event) -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Account ac = (Account) tableView.getSelectionModel().getSelectedItem();
				SceneController.changeScene(TransactionScene.getScene(ac));
			}
		});
		
		Button btn = new Button("Add New Account");
		vbox.getChildren().add(btn);
		vbox.setAlignment(Pos.CENTER);
		btn.setAlignment(Pos.BOTTOM_CENTER);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SceneController.changeScene(AddAccountScene.getScene(user));
			}
		});

		Scene scene = new Scene(vbox, 500, 400);

		return scene;

	}

}
