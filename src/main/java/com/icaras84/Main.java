package com.icaras84;

import com.icaras84.beziertestvisualizer.ui.widgets.simple.LabelWidget;
import com.icaras84.beziertestvisualizer.ui.widgets.simple.TextboxWidget;
import com.icaras84.beziertestvisualizer.utils.proxy.DirectProxy;

import javax.swing.*;
import java.util.function.Consumer;


public class Main {
    static void main() {
        launchTestFrame(Main::textboxTest);
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
}
