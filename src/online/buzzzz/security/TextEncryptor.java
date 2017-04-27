/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online.buzzzz.security;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.geometry.Insets;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author GIB
 */
public class TextEncryptor extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 600;
    private static final int JFXPANEL_HEIGHT_INT = 500;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            }
            
            JFrame frame = new JFrame("Text Encryptor!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            JApplet applet = new TextEncryptor();
            applet.init();
            
            frame.setContentPane(applet.getContentPane());
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            applet.start();
        });
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(() -> {
            createScene();
        });
    }
    
    private void createScene() {
        Insets defInset = new Insets(10);
        VBox root = new VBox();
        root.setPadding(defInset);
        root.setSpacing(10);
        TextArea source = new TextArea();
        //source.setPrefColumnCount(400);
        source.setPrefRowCount(70);
        source.setWrapText(true);
        TextField pass;
        pass = new TextField();
        pass.setPrefColumnCount(200);
        pass.textProperty().addListener(
                (observable,oldValue,newValue)->{
                    if (newValue.length() > 16) ((StringProperty)observable).setValue(oldValue);
                }
        );
        
        HBox buttonArea = new HBox();
        //buttonArea.setPadding(defInset);
        buttonArea.setSpacing(10);
        Button encBtn = new Button();
        encBtn.setText("Encrypt");
        encBtn.setOnAction((ActionEvent event) -> {
            String encrypted;
            encrypted = AESCrypto.encrypt(pass.getText(), source.getText());
            source.setText(encrypted);
            //System.out.println("Hello World!");
        });
        Button decBtn = new Button();
        decBtn.setText("Decrypt");
        decBtn.setOnAction((ActionEvent event) -> {
            String plainText;
            plainText = AESCrypto.decrypt(pass.getText(), source.getText());
            source.setText(plainText);
            //System.out.println("Hello World!");
        });
        buttonArea.getChildren().addAll(encBtn, decBtn);
        root.getChildren().addAll(source,pass,buttonArea);
        fxContainer.setScene(new Scene(root));
    }
    
}
