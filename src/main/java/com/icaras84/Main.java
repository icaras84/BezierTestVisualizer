package com.icaras84;


import com.icaras84.beziertestvisualizer.ui.widgets.simple.LabelWidget;
import com.icaras84.beziertestvisualizer.ui.widgets.simple.TextboxWidget;
import com.icaras84.beziertestvisualizer.utils.proxy.DirectProxy;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.function.Consumer;


public class Main {
    static void main() {
        launchTestFrame(Main::comboBoxCombinedTest);
    }

    public static void launchTestFrame(Consumer<JPanel> contentPanelConsumer){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel();
        frame.getContentPane().add(contentPanel);

        contentPanelConsumer.accept(contentPanel);

        frame.setVisible(true);
    }

    public static void labelTest(JPanel contentPanel){
        String text = "Hello World!";
        LabelWidget labelWidget = LabelWidget.createStatic(text);
        contentPanel.add(labelWidget);
    }

    public static void textboxTest(JPanel contentPanel){
        TextboxWidget textboxWidget =TextboxWidget.createUnformatted(new DirectProxy<>("Hello World!"));
        textboxWidget.proxy().publisher().subscribers().add(System.out::println);
        contentPanel.add(textboxWidget);
    }

    public static void comboBoxCombinedTest(JPanel contentPanel){
        JComboBox<Integer> comboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        JSpinner spinner = new JSpinner();
        comboBox.setEditable(true);

        spinner.addChangeListener(_ -> System.out.println(spinner.getValue()));

        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                System.out.println("From combo: " + comboBox.getSelectedItem());
            }
        });

        spinner.setPreferredSize(new Dimension(100, 20));

        BasicComboBoxEditor basicComboBoxEditor = new BasicComboBoxEditor();

        comboBox.setEditor(new ComboBoxEditor() {
            @Override
            public Component getEditorComponent() {
                return spinner;
            }

            @Override
            public void setItem(Object anObject) {
                spinner.setValue(anObject);
            }

            @Override
            public Object getItem() {
                return spinner.getValue();
            }

            @Override
            public void selectAll() {
                spinner.requestFocus();
            }

            @Override
            public void addActionListener(ActionListener l) {

            }

            @Override
            public void removeActionListener(ActionListener l) {

            }
        });

        contentPanel.add(comboBox);
    }
}
