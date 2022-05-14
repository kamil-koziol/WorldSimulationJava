package com.company;

import com.company.organizmy.Organizm;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Main extends JFrame {

    static Dimension dimensions = new Dimension(800, 800);
    int SCALE;
    static int WORLD_MARGIN = 20;

    // ui
    JButton newRoundButton;
    Swiat swiat;
    JComboBox<String> organismSelector;
    JButton saveWorldButton;
    JButton loadWorldButton;
    JFileChooser fileChooser;

    public Main() {
        setSize(dimensions);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension worldDimensions = new Dimension(dimensions.width - 200, dimensions.height - 200);
        SCALE = worldDimensions.width/20;
        Dimension worldTiles = new Dimension(worldDimensions.width / SCALE, worldDimensions.height / SCALE);
        swiat = new Swiat(worldTiles, SCALE);
        swiat.setSize(worldDimensions);
        swiat.setBounds(100, 50, worldDimensions.width, worldDimensions.height);
        swiat.generateRandomWorld();
        swiat.setFocusable(true);
        swiat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                swiat.handleMousePress(e);
            }
        });

        add(swiat);

        newRoundButton = new JButton("Next round");
        newRoundButton.setBounds(WORLD_MARGIN, dimensions.height - 100, 200, 50);
        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.handleNewRoundButtonPress(e);
                newRoundButton.setFocusable(false);
            }
        });
        add(newRoundButton);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                swiat.handleKeyPress(e);
            }
        });

        organismSelector = new JComboBox<>();
        organismSelector.setBounds(WORLD_MARGIN + newRoundButton.getX() + 180, newRoundButton.getY(), 200, 50);
        for(String organismName: swiat.getOrganizmRegister().getRegisteredNames()) {
            organismSelector.addItem(organismName);
        }

        organismSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.handleOrganismSelectorChange(e);
                setFocusable(true);
                organismSelector.setFocusable(false);
            }
        });
        add(organismSelector);
        organismSelector.setSelectedIndex(0);

        loadWorldButton = new JButton();
        loadWorldButton.setText("Load");
        loadWorldButton.setBounds(organismSelector.getX() + 200, organismSelector.getY(), 100, 50);
        loadWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("./saves/"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Saves", "save");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    swiat.loadFromFile(fileChooser.getSelectedFile().toString());
                }

                loadWorldButton.setFocusable(false);
            }
        });
        loadWorldButton.setFocusable(false);
        add(loadWorldButton);

        saveWorldButton = new JButton();
        saveWorldButton.setText("Save");
        saveWorldButton.setBounds(loadWorldButton.getX() + 100, loadWorldButton.getY(), 100, 50);
        saveWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter file name");
                swiat.makeSave(fileName);
                saveWorldButton.setFocusable(false);
            }
        });
        saveWorldButton.setFocusable(false);
        add(saveWorldButton);


        setFocusable(true);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
