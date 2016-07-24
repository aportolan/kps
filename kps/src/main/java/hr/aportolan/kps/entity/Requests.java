package hr.aportolan.kps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import hr.aportolan.kps.provisioning.ws.ProvisioningState;

@Document
public class Requests {
	@Id
	private String id;
	private ProvisioningState provisioningState;
	private Subscribers subscribers;

	public Requests(String id, ProvisioningState provisioningState, Subscribers subscribers) {
		super();
		this.id = id;
		this.provisioningState = provisioningState;
		this.subscribers = subscribers;
	}

	public Requests() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Subscribers getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Subscribers subscribers) {
		this.subscribers = subscribers;
	}

	public ProvisioningState getProvisioningState() {
		return provisioningState;
	}

	public void setProvisioningState(ProvisioningState provisioningState) {
		this.provisioningState = provisioningState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Requests other = (Requests) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Requests [id=" + id + ", provisioningState=" + provisioningState + ", subscribers=" + subscribers + "]";
	}

}
