/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buzzSecurity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.geometry.Insets;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
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
            }
        });
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                createScene();
            }
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
        TextField pass = new TextField();
        pass.setPrefColumnCount(200);
        
        
        HBox buttonArea = new HBox();
        //buttonArea.setPadding(defInset);
        buttonArea.setSpacing(10);
        Button encBtn = new Button();
        encBtn.setText("Encrypt");
        encBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                source.setText("Encrypting.....");
                //System.out.println("Hello World!");
            }
        });
        Button decBtn = new Button();
        decBtn.setText("Decrypt");
        decBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                source.setText("Decrypting....");
                //System.out.println("Hello World!");
            }
        });
        buttonArea.getChildren().addAll(encBtn, decBtn);
        root.getChildren().addAll(source,pass,buttonArea);
        fxContainer.setScene(new Scene(root));
    }
    
}
