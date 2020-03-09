import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTwo {
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton buttonExpand;

    public FormTwo(String FIO) {
        textField1.setText(FIO);
        buttonExpand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //в отдельный метод
                String FIO = textField1.getText();
                String[] fragments = FIO.trim().split("\\s+", 3);
                String F = "";
                String I = "";
                String O = "";

                switch (fragments.length) {
                    case (3):
                        O = fragments[2];
                    case (2):
                        I = fragments[1];
                    case (1):
                        F = fragments[0];
                }

                if (F.trim().length() > 0 && I.trim().length() > 0) {
                    Main.setFrameContent(new FormOne(F, I, O).getRootPanel());
                } else {
                    JOptionPane.showMessageDialog(
                            rootPanel,
                            "Поле должно быть заполнено минимум 2 словами.",
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
