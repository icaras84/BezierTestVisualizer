package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import java.util.function.Consumer;

public interface Widget<T> extends Consumer<T> {
    VariableProxy<T> proxy();
    JComponent getAsComponent();
}
