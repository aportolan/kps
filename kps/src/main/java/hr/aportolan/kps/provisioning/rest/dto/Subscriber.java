package hr.aportolan.kps.provisioning.rest.dto;

public class Subscriber {
	private String subscriber;
	private String type;
	private String route;

	public Subscriber() {

	}

	public Subscriber(String subscriber, String type, String route) {
		super();
		this.subscriber = subscriber;
		this.type = type;
		this.route = route;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscriber other = (Subscriber) obj;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscriber [subscriber=" + subscriber + ", type=" + type + ", route=" + route + "]";
	}

}
