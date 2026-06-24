package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ComboBoxWidget<T> extends JPanel implements Widget<T>{

    private final JComboBox<T> comboBox;
    private final VariableProxy<T> proxy;

    public ComboBoxWidget(JComboBox<T> comboBox, VariableProxy<T> proxy) {
        super();

        this.comboBox = comboBox;
        this.proxy = proxy;
        this.comboBox.addItemListener(this::itemChange);

        this.proxy.publisher().add(this);
        this.accept(proxy.get());

        super.setLayout(new BorderLayout());
        super.add(this.comboBox, BorderLayout.CENTER);
    }

    private void itemChange(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            this.proxy.set(this.comboBox.getItemAt(this.comboBox.getSelectedIndex()));
            this.proxy.updateExcept(this);
        }
    }

    public JComboBox<T> getJComboBox() {
        return this.comboBox;
    }

    @Override
    public void accept(T value) {
        this.comboBox.setSelectedItem(value);
    }

    @Override
    public VariableProxy<T> proxy() {
        return this.proxy;
    }

    @Override
    public JComponent getAsComponent() {
        return this;
    }

    public static <T> ComboBoxWidget<T> createArray(T[] options, VariableProxy<T> proxy) {
        return new ComboBoxWidget<>(new JComboBox<>(options), proxy);
    }
}
