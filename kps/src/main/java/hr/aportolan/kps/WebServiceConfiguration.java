package hr.aportolan.kps;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("hr.aportolan.kps.provisioning.ws");
		return marshaller;
	}

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/client/*");
	}

	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("client");
		wsdl11Definition.setLocationUri("/client/services");
		wsdl11Definition.setTargetNamespace("http://aportolan.hr/kps/provisioning/ws");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schema/ProvisioningNotificationSchema.xsd"));
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