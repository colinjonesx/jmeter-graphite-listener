package com.neatinnovation;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

public class JMeterGraphiteListener 
		extends AbstractListenerElement 
		implements TestStateListener, SampleListener, Serializable {

	private static final long serialVersionUID = 1L;
	final MetricRegistry registry = new MetricRegistry();
	Graphite graphite;
	GraphiteReporter reporter;
	private int period = 10;
	private String serverHost;
	private int serverPort;
	private String metricPrefix;
	
	public JMeterGraphiteListener(){
		super();
	}
	
	public void testStarted(String arg0) {
		this.testStarted();
	}

	public void testStarted() {
		graphite = new Graphite(new InetSocketAddress(serverHost, serverPort));
		reporter = GraphiteReporter.forRegistry(registry)
                .prefixedWith(this.metricPrefix)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
		reporter.start(this.period, TimeUnit.SECONDS);
	}
	
	public void testEnded() {
		reporter.stop();
	}

	public void testEnded(String arg0) {
		testEnded();
	}


	public void testIterationStart(LoopIterationEvent arg0) {
		registry.counter(arg0.getSource().getThreadName()).inc();
		
	}
	
	public void sampleOccurred(SampleEvent event) {
		SampleResult result = event.getResult();
		if(result.isSuccessful()){
			registry.counter("txn.ok").inc();
		}else{
			registry.counter("txn.ng").inc();
		}
		registry.timer("timers."+result.getSampleLabel()).update(result.getTime(), TimeUnit.MILLISECONDS);
	}

	public void sampleStarted(SampleEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sampleStopped(SampleEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setPeriod(String text) {
		this.period = Integer.parseInt(text);
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setMetricPrefix(String text) {
		this.metricPrefix = text;
	}

	public String getPeriod() {
		return this.period+"";
	}
	
	public String getServerHost(){
		return this.serverHost;
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
	
	public String getMetricPrefix(){
		return this.metricPrefix;
	}
}
