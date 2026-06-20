package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;

public interface Widget<T> {
    VariableProxy<T> proxy();
    JComponent getAsComponent();
}
