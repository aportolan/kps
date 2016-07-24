package hr.aportolan.kps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.ws.MarshallingWebServiceInboundGateway;
import org.springframework.integration.ws.MarshallingWebServiceOutboundGateway;
import org.springframework.messaging.PollableChannel;
import org.springframework.oxm.Marshaller;

@Configuration
public class IntegrationConfiguration {

	@Autowired
	private Marshaller marshaller;

	@Bean
	public MarshallingWebServiceOutboundGateway wsOutboundGateway() {
		MarshallingWebServiceOutboundGateway marshallingGateway = new MarshallingWebServiceOutboundGateway(
				"http://example.com/client/services", marshaller);
		return marshallingGateway;
	}

	@Bean
	public PollableChannel wsOutboundMessageChannel() {
		QueueChannel queueChannel = new QueueChannel(10000);
		return queueChannel;

	}

	@Bean
	public PollableChannel wsInboundMessageChannel() {
		QueueChannel queueChannel = new QueueChannel(10000);
		return queueChannel;

	}

	@Bean
	public MarshallingWebServiceInboundGateway wsInboundGateway() {
		MarshallingWebServiceInboundGateway marshallingGateway = new MarshallingWebServiceInboundGateway(marshaller);
		marshallingGateway.setReplyChannel(wsOutboundMessageChannel());
		return marshallingGateway;
	}

}
