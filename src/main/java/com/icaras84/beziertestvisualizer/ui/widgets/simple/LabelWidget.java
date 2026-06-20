package com.icaras84.beziertestvisualizer.ui.widgets.simple;

import com.icaras84.beziertestvisualizer.utils.proxy.DirectProxy;
import com.icaras84.beziertestvisualizer.utils.proxy.VariableProxy;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LabelWidget extends JPanel implements Widget<String>, Consumer<String> {
    public enum TextAlign{
        LEFT(SwingConstants.LEFT),
        CENTER(SwingConstants.CENTER),
        RIGHT(SwingConstants.RIGHT),
        LEADING(SwingConstants.LEADING),
        TRAILING(SwingConstants.TRAILING);

        private final int value;

        TextAlign(int value) {
            this.value = value;
        }

        public int swingValue() {
            return this.value;
        }
    }

    public enum VerticalAlign{
        TOP(SwingConstants.TOP),
        CENTER(SwingConstants.CENTER),
        BOTTOM(SwingConstants.BOTTOM);

        private final int value;

        VerticalAlign(int value) {
            this.value = value;
        }

        public int swingValue() {
            return this.value;
        }
    }

    private final JLabel label;
    private final VariableProxy<String> proxy;
    private TextAlign textAlign;
    private VerticalAlign verticalAlign;

    public LabelWidget(VariableProxy<String> proxy) {
        super(); // initialize JPanel

        // field initialization
        this.proxy = proxy;
        this.label = new JLabel(this.proxy.get());
        this.setTextAlign(TextAlign.CENTER);
        this.setVerticalAlign(VerticalAlign.CENTER);

        // set up proxy stuff
        this.proxy.publisher().add(this);

        // ui
        super.setLayout(new BorderLayout());
        super.add(this.label, BorderLayout.CENTER);
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
        this.label.setHorizontalAlignment(this.textAlign.swingValue());
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        this.label.setVerticalAlignment(this.verticalAlign.swingValue());
    }

    public JLabel getJLabel() {
        return this.label;
    }

    /**
     * Load the input as the text for this label
     * @param text the input argument
     */
    @Override
    public void accept(String text) {
        this.label.setText(text);
    }

    @Override
    public VariableProxy<String> proxy() {
        return this.proxy;
    }

    @Override
    public JComponent getAsComponent() {
        return this;
    }

    public static LabelWidget createStatic(String text){
        return new LabelWidget(new DirectProxy<>(text));
    }

    public static LabelWidget createDynamic(VariableProxy<String> textProxy){
        return new LabelWidget(textProxy);
    }
}
