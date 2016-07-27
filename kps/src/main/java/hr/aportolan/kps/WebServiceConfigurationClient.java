package hr.aportolan.kps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfigurationClient extends WsConfigurerAdapter {

	private static final String URI = "http://kps-aportolan.rhcloud.com/schema/ProvisioningNotificationSchema";

	@Bean(name = "client")
	public DefaultWsdl11Definition clientWsdl11Definition() {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("client");
		wsdl11Definition.setLocationUri("/kpsws/client/services");
		wsdl11Definition.setTargetNamespace(URI);
		wsdl11Definition.setSchema(clientSchema());
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema clientSchema() {
		return new SimpleXsdSchema(new ClassPathResource("public/schema/ProvisioningNotificationSchema.xsd"));
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