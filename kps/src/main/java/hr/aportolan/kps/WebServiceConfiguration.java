package hr.aportolan.kps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.server.endpoint.mapping.PayloadRootQNameEndpointMapping;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

	@Value("#{inboundGateway}")
	private MessageEndpoint wsInboundGateway;

	private static final String URI = "http://kps-aportolan.rhcloud.com/schema/gen/ProvisioningOperationsSchema";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("hr.aportolan.kps.provisioning.ws");

		return marshaller;
	}

	@Bean
	public ServletRegistrationBean provisioningMessageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/kpsws/*");
	}

	@Bean(name = "provisioning")
	public DefaultWsdl11Definition provisioningWsdl11Definition() {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("kps");
		wsdl11Definition.setLocationUri("/kpsws/services");

		wsdl11Definition.setTargetNamespace(URI);
		wsdl11Definition.setSchema(kpsSchema());
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema kpsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("public/schema/gen/ProvisioningOperationsSchema.xsd"));
	}

	@Bean
	public PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping() {
		String provisionSubscriber = String.format("{%s}%s", URI, "ProvisionSubscriberRequest");
		String provisioningState = String.format("{%s}%s", URI, "ProvisioningStateRequest");
		String cancelProvisioning = String.format("{%s}%s", URI, "CancelProvisioningRequest");

		Map<String, Object> endpoints = new HashMap<String, Object>();
		endpoints.put(provisionSubscriber, wsInboundGateway);
		endpoints.put(provisioningState, wsInboundGateway);
		endpoints.put(cancelProvisioning, wsInboundGateway);

		PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping = new PayloadRootQNameEndpointMapping();
		payloadRootQNameEndpointMapping.setEndpointMap(endpoints);
		payloadRootQNameEndpointMapping.setInterceptors(new EndpointInterceptor[] { new PayloadLoggingInterceptor() });
		return payloadRootQNameEndpointMapping;
	}

	@Bean
	List<UriEndpointMapping> endpointMappings() {
		List<UriEndpointMapping> uriEndpointMappings = new ArrayList<>();
		UriEndpointMapping uriEndpointMapping = new UriEndpointMapping();
		uriEndpointMapping.setDefaultEndpoint(wsInboundGateway);

		String provisionSubscriber = String.format("{%s}%s", URI, "ProvisionSubscriberRequest");
		String provisioningState = String.format("{%s}%s", URI, "ProvisioningStateRequest");
		String cancelProvisioning = String.format("{%s}%s", URI, "CancelProvisioningRequest");
		Properties mappings = new Properties();
		mappings.put(provisionSubscriber, wsInboundGateway);
		mappings.put(provisioningState, wsInboundGateway);
		mappings.put(cancelProvisioning, wsInboundGateway);
		uriEndpointMapping.setMappings(mappings);
		uriEndpointMappings.add(uriEndpointMapping);
		return uriEndpointMappings;

	}

	@Bean
	public EndpointAdapter messageEndpointAdapter() {
		return new MessageEndpointAdapter();
	}

}