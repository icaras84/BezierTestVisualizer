package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;

public class TextboxWidget extends JPanel implements Widget<String>{

    private final JFormattedTextField formattedTextField;
    private final VariableProxy<String> proxy;

    public TextboxWidget(JFormattedTextField formattedTextField, VariableProxy<String> proxy) {
        super();

        this.formattedTextField = formattedTextField;
        this.proxy = proxy;
        this.formattedTextField.addPropertyChangeListener("value", this::onPropertyChange);

        this.proxy.publisher().add(this);
        this.accept(proxy.get());

        super.setLayout(new BorderLayout());
        super.add(this.formattedTextField, BorderLayout.CENTER);
    }

    private void onPropertyChange(PropertyChangeEvent evt) {
        try {
            this.formattedTextField.commitEdit();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.proxy.set(this.formattedTextField.getValue().toString());
        this.proxy.updateExcept(this);
    }

    public JFormattedTextField getJFormattedTextField() {
        return this.formattedTextField;
    }

    @Override
    public void accept(String text) {
        this.formattedTextField.setValue(text);
    }

    @Override
    public VariableProxy<String> proxy() {
        return this.proxy;
    }

    @Override
    public JComponent getAsComponent() {
        return this;
    }

    public static TextboxWidget createUnformatted(VariableProxy<String> textProxy) {
        return new TextboxWidget(new JFormattedTextField(), textProxy);
    }

    public static TextboxWidget createFormatted(VariableProxy<String> textProxy, MaskFormatter maskFormatter) {
        return new TextboxWidget(new JFormattedTextField(maskFormatter), textProxy);
    }
}
