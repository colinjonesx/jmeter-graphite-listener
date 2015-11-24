package com.neatinnovation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

public class JMeterGraphiteListenerGUI extends AbstractListenerGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6247153860562217100L;
	private JTextField period;
	private JTextField serverHost;
	private JTextField serverPort;
	private JTextField metricPrefix;

	public JMeterGraphiteListenerGUI() {
		super();
		init();
		initFields();
	}
	@Override
	public TestElement createTestElement() {
		TestElement graphiteListener = new JMeterGraphiteListener();
        modifyTestElement(graphiteListener);
		return graphiteListener;
	}

	@Override
	public String getLabelResource() {
		return "Graphite";
	}

	@Override
	public String getName() {
		return "Graphite";
	}
	
	@Override
	public String getStaticLabel() {
		return "Graphite";
	}

    @Override
    public void modifyTestElement(TestElement te) {
    	super.configureTestElement(te);
        if (te instanceof JMeterGraphiteListener) {
            JMeterGraphiteListener graphiteListener = (JMeterGraphiteListener) te;
            graphiteListener.setPeriod(period.getText());
            graphiteListener.setServerHost(serverHost.getText());
            graphiteListener.setServerPort(Integer.parseInt(serverPort.getText()));
            graphiteListener.setMetricPrefix(metricPrefix.getText());
        }
    }
    
    @Override
    public void configure(TestElement element) {
        super.configure(element);
        JMeterGraphiteListener graphiteListener = (JMeterGraphiteListener) element;
        period.setText(graphiteListener.getPeriod());
        serverHost.setText(graphiteListener.getServerHost());
        serverPort.setText(graphiteListener.getServerPort()+"");
        metricPrefix.setText(graphiteListener.getMetricPrefix());
    }
    
    @Override
    public void clearGui() {
        super.clearGui();
        initFields();
    }
    
    private void initFields(){
    	System.out.println("Initialising Fields");
    	period.setText("10");
        serverHost.setText("graphite.example.org");
        serverPort.setText("2003");
        metricPrefix.setText("jmeter");
    }
	
	private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        GridBagConstraints editConstraints = new GridBagConstraints();
        
        labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        editConstraints.weightx = 1.0;
        editConstraints.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Sample period (seconds): ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, period = new JTextField(3));

        row++;
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Carbon (Graphite) Host: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, serverHost = new JTextField(20));

        row++;
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Carbon (Graphite) Port: ", JLabel.RIGHT));
        addToPanel(mainPanel, editConstraints, 1, row, serverPort = new JTextField(20));

        
        editConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        labelConstraints.insets = new java.awt.Insets(2, 0, 0, 0);

        row++;
        addToPanel(mainPanel, labelConstraints, 0, row, new JLabel("Metric prefix: ", JLabel.RIGHT));
		addToPanel(mainPanel, editConstraints, 1, row, metricPrefix = new JTextField(20));
		
		JPanel container = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }
	
    private void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
        constraints.gridx = col;
        constraints.gridy = row;
        panel.add(component, constraints);
    }

}
