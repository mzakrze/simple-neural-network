
package pl.edu.pw.elka.view;

import pl.edu.pw.elka.model.enumerate.ActivationFunctionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mariusz on 2016-12-13.
 */
public class SetParametersPanel extends JPanel{
    private JButton startButton;
    private JPanel rootPanel;
    private JLabel errorXLabel;
    private JLabel errorYLabel;
    private JLabel sizeXLabel;
    private JLabel sizeYLabel;
    private JPanel xPanel;
    private JPanel yPanel;
    private JPanel buttonPanel;
    private JTextField sizeXTextField;
    private JTextField sizeYTextField;
    private JTextField hiddenLayerSizesTextField;
    private JComboBox activationFunctionTypesComboBox;
    private View view;

    public SetParametersPanel(View view){
        errorXLabel.setForeground(Color.RED);
        errorYLabel.setForeground(Color.RED);
        add(rootPanel);
        this.view = view;
        createUIComponents();

    }

    private ActionListener buttonActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (checkNumberAndSetErrorLabel(sizeXTextField, errorXLabel)
                    && checkNumberAndSetErrorLabel(sizeYTextField, errorYLabel)) {

                int x = Integer.valueOf(sizeXTextField.getText());
                int y = Integer.valueOf(sizeYTextField.getText());

                List<Integer> hiddenLayerSizes = null;
                try {
                    hiddenLayerSizes = Arrays.stream(hiddenLayerSizesTextField.getText().toString().split(" "))
                            .mapToInt(s -> Integer.parseInt(s))
                            .boxed()
                            .collect(Collectors.toList());

                } catch(NumberFormatException nfe){
                    errorYLabel.setText("niepoprawne dane");
                    return;
                }

                ActivationFunctionType selectedFunction = (ActivationFunctionType) activationFunctionTypesComboBox.getSelectedItem();

                view.getController().createModelForGivenParameters( x, y, hiddenLayerSizes, selectedFunction);
                view.changeToWorkPanel(x, y);
            }
        }

        private boolean checkNumberAndSetErrorLabel(JTextField textField, JLabel errorLabel) {
            boolean correct = true;
            try {
                int num = Integer.valueOf(textField.getText());
                if(num < 0){
                    errorLabel.setText("Wpisz liczbę dodatnią");
                    correct = false;
                } else{
                    errorLabel.setText("");
                }

            } catch(NumberFormatException nfe){
                errorYLabel.setText("Wpisz liczbę");
                correct = false;
            }
            return correct;
        }
    };


    private void createUIComponents() {
        startButton.addActionListener(buttonActionListener);
        for(ActivationFunctionType aft : ActivationFunctionType.values()){
            activationFunctionTypesComboBox.addItem(aft);
        }
    }
}
