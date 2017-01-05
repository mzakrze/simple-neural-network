package pl.edu.pw.elka.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import pl.edu.pw.elka.view.enumerate.FieldType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mariusz on 2016-12-13.
 */
public class WorkPanel extends JPanel {
    public final int BOARD_WIDTH = 500, BOARD_HEIGHT = 500;
    private Field[][] inputBoard;
    private Field[][] outputBoard;
    private View view;
    private JPanel rootPanel;
    private JButton backToSetParametersButton;
    private JPanel inputBoardPanel;
    private JPanel outputBoardPanel;
    private JButton endProgramButton;
    private JButton teachbutton;

    private ActionListener buttonActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            view.changeToSetParameters();
        }
    };
    private Color whiteClassColor = Color.WHITE;
    private Color blackClassColor = Color.BLACK;
    private Color noneClassColor = Color.GRAY;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Field{
        int x,y;
        FieldType type;
        JButton button;
    }

    MouseListener mouseListener = new MouseListener() {
        public void mouseClicked(MouseEvent e) {
            for(int i = 0; i < inputBoard.length; ++i){
                for(int j = 0; j < inputBoard[0].length; ++j){
                    Field field = inputBoard[i][j];
                    if(field.getButton() == e.getSource()){
                        if(SwingUtilities.isLeftMouseButton(e)) {
                            field.getButton().setBackground(blackClassColor);
                            field.setType(FieldType.BLACK);
                        }

                        if(SwingUtilities.isRightMouseButton(e)){
                            field.getButton().setBackground(whiteClassColor);
                            field.setType(FieldType.WHITE);
                        }
                        if(SwingUtilities.isMiddleMouseButton(e)) {
                            field.getButton().setBackground(noneClassColor);
                            field.setType(FieldType.NONE);
                        }
                        return;
                    }
                }
            }
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    };

    public WorkPanel(View view, int boardXSize, int boardYSize){
        this.view = view;
        add(rootPanel);
        inputBoardPanel.setSize(BOARD_WIDTH,BOARD_WIDTH);
        outputBoardPanel.setSize(BOARD_WIDTH,BOARD_WIDTH);

        createBoards(boardXSize,boardYSize);

        backToSetParametersButton.addActionListener(buttonActionListener);

        teachbutton.addActionListener((ActionEvent e) -> {
            view.getController().teachNetwork(inputBoard);

            final FieldType[][] fieldTypesInResultBoard = view.getController().calculateResultBoard();

            for (int i = 0; i < outputBoard.length; ++i) {
                for (int j = 0; j < outputBoard[0].length; ++j) {
                    outputBoard[i][j].setType(fieldTypesInResultBoard[i][j]);
                    Color backgroundColor = getColor(fieldTypesInResultBoard[i][j]);
                    outputBoard[i][j].getButton().setBackground(backgroundColor);
                }
            }
        });

        setSize(WIDTH,HEIGHT);
        setVisible(true);
        endProgramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private Color getColor(FieldType fieldType) {
        switch (fieldType) {
            case BLACK:
                return blackClassColor;
            case WHITE:
                return whiteClassColor;
            default:
                return noneClassColor;
        }
    }

    private void createBoards(int boardXSize, int boardYSize){
        inputBoard = new Field[boardXSize][boardYSize];
        outputBoard = new Field[boardXSize][boardYSize];

        GridBagConstraints c = new GridBagConstraints();

        int buttonXSize = BOARD_WIDTH / boardXSize;
        int buttonYSize = BOARD_HEIGHT / boardYSize;

        JButton button;
        for(int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                button = new JButton();
                button.setPreferredSize(new Dimension(buttonXSize, buttonYSize));
                button.setBackground(noneClassColor);
                button.addMouseListener(mouseListener);
                c.gridx = x;
                c.gridy = y;
                inputBoardPanel.add(button, c);
                inputBoard[x][y] = new Field(x, y, FieldType.NONE, button);

                button = new JButton();
                button.setPreferredSize(new Dimension(buttonXSize, buttonYSize));
                button.setBackground(noneClassColor);
                button.addMouseListener(mouseListener);
                button.setEnabled(false);
                c.gridx = x;
                c.gridy = y;
                outputBoardPanel.add(button, c);
                outputBoard[x][y] = new Field(x,y,FieldType.NONE, button);
            }
        }
    }

}
