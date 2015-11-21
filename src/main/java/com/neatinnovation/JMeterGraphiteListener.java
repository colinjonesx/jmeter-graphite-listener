package com.neatinnovation;

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

public class JMeterGraphiteListener extends AbstractListenerElement implements TestStateListener, SampleListener {

	private static final long serialVersionUID = 1L;
	final MetricRegistry registry = new MetricRegistry();
	final Graphite graphite = new Graphite(new InetSocketAddress("holly.ceejay.local", 2003));
	final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
	                                                  .prefixedWith("jmeter")
	                                                  .convertRatesTo(TimeUnit.SECONDS)
	                                                  .convertDurationsTo(TimeUnit.MILLISECONDS)
	                                                  .filter(MetricFilter.ALL)
	                                                  .build(graphite);
	public JMeterGraphiteListener(){
		reporter.start(10, TimeUnit.SECONDS);
	}
	
	public void testEnded() {
		// TODO Auto-generated method stub
		
	}

	public void testEnded(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void testStarted() {
		// TODO Auto-generated method stub
		
	}

	public void testStarted(String arg0) {
		// TODO Auto-generated method stub
		
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

}
