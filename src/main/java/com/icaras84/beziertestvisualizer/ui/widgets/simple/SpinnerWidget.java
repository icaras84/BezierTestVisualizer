package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.Date;
import java.util.function.Consumer;

public class SpinnerWidget<T extends Comparable<T>> extends JPanel implements Widget<T>, Consumer<T> {

    private final JSpinner spinner;
    private final VariableProxy<T> proxy;

    public SpinnerWidget(JSpinner spinner, VariableProxy<T> proxy) {
        super();

        this.spinner = spinner;
        this.proxy = proxy;
        this.spinner.addChangeListener(this::onChange);

        this.proxy.publisher().add(this);
        this.accept(proxy.get());

        super.setLayout(new BorderLayout());
        super.add(this.spinner, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    private void onChange(ChangeEvent evt) {
        this.proxy.set((T) this.spinner.getModel().getValue());
        this.proxy.updateExcept(this);
    }

    public JSpinner getJSpinner() {
        return this.spinner;
    }

    @Override
    public void accept(T value) {
        this.spinner.getModel().setValue(value);
    }

    @Override
    public VariableProxy<T> proxy() {
        return this.proxy;
    }

    @Override
    public JComponent getAsComponent() {
        return this;
    }

    public static SpinnerWidget<Integer> createInteger(Integer min, Integer max, int stepsize, VariableProxy<Integer> proxy){
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(min);
        model.setMaximum(max);
        model.setValue(proxy.get());
        model.setStepSize(stepsize);
        SpinnerWidget<Integer> integerSpinnerWidget = new SpinnerWidget<>(new JSpinner(model), proxy);
        integerSpinnerWidget.setPreferredSize(new Dimension(75, 25));
        return integerSpinnerWidget;
    }

    public static SpinnerWidget<Double> createDouble(Double min, Double max, double stepsize, VariableProxy<Double> proxy){
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(min);
        model.setMaximum(max);
        model.setValue(proxy.get());
        model.setStepSize(stepsize);
        SpinnerWidget<Double> doubleSpinnerWidget = new SpinnerWidget<>(new JSpinner(model), proxy);
        doubleSpinnerWidget.setPreferredSize(new Dimension(75, 25));
        return doubleSpinnerWidget;
    }

    public static SpinnerWidget<Date> createDate(Comparable<Date> start, Comparable<Date> end, VariableProxy<Date> proxy){
        SpinnerDateModel model = new SpinnerDateModel();
        model.setStart(start);
        model.setEnd(end);
        model.setValue(proxy.get());
        SpinnerWidget<Date> dateSpinnerWidget = new SpinnerWidget<>(new JSpinner(model), proxy);
        dateSpinnerWidget.setPreferredSize(new Dimension(75, 25));
        return dateSpinnerWidget;
    }
}
