package Client;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class employeeCell {
    public static void setTableCellRenderer(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        // Определение минимального и максимального размеров столбцов
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            switch (i) {
                case 4 -> columnModel.getColumn(i).setMinWidth(100);
                case 5, 8 -> columnModel.getColumn(i).setMinWidth(110);
                case 6 -> columnModel.getColumn(i).setMinWidth(150);
                case 7 -> columnModel.getColumn(i).setMinWidth(120);
                default -> columnModel.getColumn(i).setMinWidth(70);
            }
        }
        table.setRowHeight(table.getRowHeight() + 20);

        table.setDefaultRenderer(String.class, new cellRender.CellRender());
    }

    //редактирование ячеек с помощью календаря и combobox
    public static void setTableCellEditor(JTable table) {

        // Редактор даты
        table.setDefaultEditor(String.class, new dateCellEditor.getDateCellEditor());

        DefaultCellEditor posts_editor = new DefaultCellEditor(employeeAddForm.getPostsInput());

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i == 7) {
                table.getColumnModel().getColumn(i).setCellEditor(posts_editor);
            } else if (i != 5 & i != 6 & i != 8) {
                table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
            }
        }
    }
}
