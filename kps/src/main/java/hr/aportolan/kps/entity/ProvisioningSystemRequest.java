package hr.aportolan.kps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "provisioning_system_request")
public class ProvisioningSystemRequest {
	@Id
	private String id;
	private ProvisioningSystem provisioningSystem;
	private Requests request;

	public ProvisioningSystemRequest() {

	}

	public ProvisioningSystemRequest(String id, ProvisioningSystem provisioningSystem, Requests request) {
		super();
		this.id = id;
		this.provisioningSystem = provisioningSystem;
		this.request = request;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProvisioningSystem getProvisioningSystem() {
		return provisioningSystem;
	}

	public void setProvisioningSystem(ProvisioningSystem provisioningSystem) {
		this.provisioningSystem = provisioningSystem;
	}

	public Requests getRequest() {
		return request;
	}

	public void setRequest(Requests request) {
		this.request = request;
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
		ProvisioningSystemRequest other = (ProvisioningSystemRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProvisioningSystemRequest [id=" + id + ", provisioningSystem=" + provisioningSystem + ", request="
				+ request + "]";
	}

}
