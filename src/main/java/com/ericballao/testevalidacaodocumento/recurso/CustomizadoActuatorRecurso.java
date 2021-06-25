package com.ericballao.testevalidacaodocumento.recurso;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.Sample;
import org.springframework.stereotype.Component;

@Endpoint(id = "status")
@Component
public class CustomizadoActuatorRecurso {
	
private MetricsEndpoint metricsEndpoint;
	
	@Autowired
    private void setMetricsEndpoint(MetricsEndpoint metricsEndpoint) {
        this.metricsEndpoint = metricsEndpoint;
    }
	
	@ReadOperation
	public Map<String, String> customEndpoint(){
		Map<String, String> map = new HashMap<>();
		
        map.put("Tempo ativo", getTempoAtivo());
        map.put("Numero de Requisições", getNumeroAcessos().toString());
        return map;
		
	}
	
	private List<Sample> getSamples(String Request){
    	
    	return metricsEndpoint.metric(Request, null) != null ?
    	    	metricsEndpoint.metric(Request, null).getMeasurements() : new ArrayList<Sample>();
    }
    
    private Long getNumeroAcessos() {
    	
    	List<Sample> samples = getSamples("http.server.requests");
    	for (Sample sample : samples) {
			if(sample.getStatistic().name().equalsIgnoreCase("COUNT")) {
				return sample.getValue().longValue();
			}
		}
    	
    	
    	return 0L;
    }
    
    private String getTempoAtivo() {
    	String tempoAtivo = "";
    	List<Sample> samplest = getSamples("process.uptime");
    	for (Sample sample : samplest) {
			if(sample.getStatistic().name().equalsIgnoreCase("VALUE")) {
				Duration diff = Duration.ofSeconds(sample.getValue().longValue());
				return String.format("%dH:%02dM:%02dS",
		                                    diff.toHours(), 
		                                    diff.toMinutesPart(), 
		                                    diff.toSecondsPart());
			}
		}
    	return tempoAtivo;
    }

}
