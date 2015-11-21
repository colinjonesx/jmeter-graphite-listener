package com.neatinnovation;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

public class JMeterGraphiteListenerGUI extends AbstractListenerGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6247153860562217100L;

	public JMeterGraphiteListenerGUI() {
		super();
		prep();
	}
	@Override
	public TestElement createTestElement() {
		TestElement te = new JMeterGraphiteListener();
		
		return te;
	}

	@Override
	public String getLabelResource() {
		return "Graphite";
	}

	@Override
	public String getName() {
		
		return "nGraphite";
	}
	@Override
	public String getStaticLabel() {
		return "graphite";
	}
	@Override
	public void modifyTestElement(TestElement te) {
		super.configureTestElement(te);
	}
	
	private void prep() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setWrapStyleWord(true);
        info.setOpaque(false);
        info.setLineWrap(true);
        info.setColumns(30);

        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(info);
        jScrollPane1.setBorder(null);
        
        info.setText("JMeter Graphite Listener");

        add(jScrollPane1, BorderLayout.CENTER);
    }

}
