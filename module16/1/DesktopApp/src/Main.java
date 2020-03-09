import javax.swing.*;

//todo:
//   Создать приложение с формой, содержащей три поля — “Фамилия”, “Имя” и “Отчество” - и кнопку “Collapse”.
//   При нажатии на кнопку “Collapse” должна происходить проверка того, что поля “Фамилия” и “Имя” заполнены.
//   Если не заполнены - отображать сообщение об ошибке и ничего дальше не делать. Если всё заполнено, скрывать
//   эти три поля и отображать поле “Ф.И.О.” со значениями отдельных полей, объединёнными через пробелы, а также
//   кнопку “Expand” вместо кнопки “Collapse”. При нажатии на кнопку “Expand” аналогичное действие: проверка
//   введённого значения и возврат к исходной форме. Тонкости реализации - на ваше усмотрение.


public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel root = new FormOne().getRootPanel();
        frame.setContentPane(root);
        frame.setVisible(true);
    }

    public static void setFrameContent(JPanel rootPanel) {
        frame.setContentPane(rootPanel);
        frame.setVisible(true);
    }
}
