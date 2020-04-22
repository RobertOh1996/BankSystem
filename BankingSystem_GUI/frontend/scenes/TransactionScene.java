package scenes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import accounts.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import user.AddressDAO;
import user.AddressDAOImpl;
import user.User;
import user.UserController;
import user.UserDAO;
import user.UserDAOImpl;

public class TransactionScene {

	/**
	 * Generates the Default scene for the Banking System, which is the home page.
	 * 
	 * @return The constructed default scene for the Banking System.
	 */
	public static Scene getScene(Account account) {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(400);
		grid.setMaxHeight(400);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Image img = new Image("logo.png");
		grid.add(new ImageView(img), 0, 0);
		
		Text ab = new Text("Account Balance");
		Button deposit = new Button("Deposit");
		grid.add(ab, 0, 1);
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		hbox.getChildren().add(deposit);
		grid.add(hbox, 1, 1);
		
		String balance = account.getBalance().toString();
		Text showBalance = new Text("$" + balance);
		Button withdraw = new Button("Withdraw");
		grid.add(showBalance, 0, 2);
		hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(withdraw, 1, 2);
		
		Text enter = new Text("Enter an amount:");
		Button cancel = new Button("Cancel");
		grid.add(enter, 0, 3);
		hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		grid.add(cancel, 1, 3);
		
		TextField amount = new TextField();
		grid.add(amount, 0, 4);
		
		deposit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					account.setBalance(account.deposit(amount.getText()));
					showBalance.setText(account.getBalance().toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		withdraw.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					account.setBalance(account.withdraw(amount.getText()));
					showBalance.setText(account.getBalance().toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SceneController.changeScene(DefaultScene.getScene());
			}
		});

		Text notFederallyInsuredText = new Text("Not Federally insured by NCUA.");
		grid.add(notFederallyInsuredText, 0, 5);

		BorderPane bp = new BorderPane();
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(notFederallyInsuredText);
		bp.setCenter(grid);
		bp.setBottom(hb);
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

		Scene scene = new Scene(bp, 475, 550);
		scene.getStylesheets().add(LoginScene.class.getResource("Login.css").toExternalForm());

		return scene;
	}

}
