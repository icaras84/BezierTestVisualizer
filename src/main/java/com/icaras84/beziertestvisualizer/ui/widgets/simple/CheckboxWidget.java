package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class CheckboxWidget extends JPanel implements Widget<Boolean>, Consumer<Boolean> {

    private final JCheckBox checkbox;
    private final VariableProxy<Boolean> proxy;

    public CheckboxWidget(VariableProxy<Boolean> proxy) {
        super();

        this.checkbox = new JCheckBox();
        this.proxy = proxy;
        this.checkbox.addActionListener(this::onCheck);

        this.proxy.publisher().add(this);
        this.accept(this.proxy.get());

        super.setLayout(new BorderLayout());
        super.add(this.checkbox, BorderLayout.CENTER);
    }

    private void onCheck(ActionEvent evt) {
        this.proxy.set(checkbox.isSelected());
        this.proxy.updateExcept(this);
    }

    @Override
    public void accept(Boolean checked) {
        this.checkbox.setSelected(checked);
    }

    @Override
    public VariableProxy<Boolean> proxy() {
        return null;
    }

    @Override
    public JComponent getAsComponent() {
        return this;
    }
}
