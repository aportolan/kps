package hr.aportolan.kps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import hr.aportolan.kps.service.ProvisionWSClientService;
import hr.aportolan.kps.service.impl.ProvisionWSClientServiceImpl;

@Configuration
public class WebServiceConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("hr.aportolan.kps.provisioning.notification");
		return marshaller;
	}

	@Bean
	public ProvisionWSClientService provisionWSClient(Jaxb2Marshaller marshaller) {
		ProvisionWSClientServiceImpl client = new ProvisionWSClientServiceImpl();
		client.setDefaultUri("http://example.com/client/services");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

	// @Bean
	// public SoapFaultAnnotationExceptionResolver
	// soapFaultAnnotationExceptionResolver() {
	// SimpleSoapExceptionResolver
	// SoapFaultAnnotationExceptionResolver soapFaultAnnotationExceptionResolver
	// = new SoapFaultAnnotationExceptionResolver();
	// soapFaultAnnotationExceptionResolver.setOrder(1);
	// return soapFaultAnnotationExceptionResolver;
	// }

}