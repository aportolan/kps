package hr.aportolan.kps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import hr.aportolan.kps.provisioning.enums.HttpOperation;

@Document
public class Routes {
	@Id
	private String id;
	private String route;
	private int local;
	private HttpOperation httpOperation;

	public Routes(String id, String route, int local, HttpOperation httpOperation) {
		super();
		this.id = id;
		this.route = route;
		this.local = local;
		this.httpOperation = httpOperation;
	}

	public Routes() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public HttpOperation getHttpOperation() {
		return httpOperation;
	}

	public void setHttpOperation(HttpOperation httpOperation) {
		this.httpOperation = httpOperation;
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
		Routes other = (Routes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Routes [id=" + id + ", route=" + route + ", local=" + local + ", httpOperation=" + httpOperation + "]";
	}

}
