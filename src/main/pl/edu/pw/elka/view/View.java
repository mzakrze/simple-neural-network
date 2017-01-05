package pl.edu.pw.elka.view;

import lombok.Getter;
import pl.edu.pw.elka.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mariusz on 2016-12-13..
 */
public class View extends JFrame {
    public static final int WIDTH = 1200, HEIGHT = 700;
    public static final int SETPARMS_WIDTH = 500, SETPARMS_HEIGHT = 350;

    private SetParametersPanel setParametersPanel;
    private WorkPanel workPanel;
    @Getter
    private Controller controller;

    public void createAndShowGUI(Controller controller){
        try {
            BufferedImage img = null;
            img = ImageIO.read(new File("icon.png"));
            super.setIconImage(img);
        } catch (IOException e) { }
        setParametersPanel = new SetParametersPanel(this);

        this.controller = controller;

        setVisible(true);
        setSize(SETPARMS_WIDTH,SETPARMS_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(setParametersPanel);
    }

    public void changeToWorkPanel(int boardXSize,int boardYSize){
        workPanel = new WorkPanel(this,boardXSize,boardYSize);
        getContentPane().removeAll();
        getContentPane().add(workPanel);
        validate();
        setVisible(true);
        setSize(WIDTH,HEIGHT);
        setResizable(false);
    }

    public void changeToSetParameters() {
        getContentPane().removeAll();
        getContentPane().add(setParametersPanel);
        validate();
        setVisible(true);
        setSize(SETPARMS_WIDTH,SETPARMS_HEIGHT);
        setResizable(false);
    }
}
