import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormOne {
    private JPanel rootPanel;
    private JButton buttonCollapse;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public FormOne(String F, String I, String O) {
        this();
        textField1.setText(F);
        textField2.setText(I);
        textField3.setText(O);
    }

    public FormOne() {
        buttonCollapse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textField1.getText().trim().length() > 0
                        && textField2.getText().trim().length() > 0) {
                    String F = textField1.getText().trim();
                    String I = textField2.getText().trim();
                    String O = textField3.getText().trim();

                    String FIO = F + " " + I + " " + O;

                    Main.setFrameContent(new FormTwo(FIO).getRootPanel());
                } else {
                    JOptionPane.showMessageDialog(
                            rootPanel,
                            "Поля имя и фамилия должны быть заполнены.",
                            "Сообщение",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }


}
