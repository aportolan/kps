package hr.aportolan.kps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Subscribers {

	public Subscribers(String id, String name, String type, Routes route, Routes parentRoute) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.route = route;
		this.parentRoute = parentRoute;
	}

	public Subscribers() {
	}

	@Id
	private String id;
	private String name;
	private String type;
	private Routes route;
	private Routes parentRoute;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type; // TODO Auto-generated constructor stub

	}

	public Routes getRoute() {
		return route;
	}

	public void setRoute(Routes route) {
		this.route = route;
	}

	public Routes getParentRoute() {
		return parentRoute;
	}

	public void setParentRoute(Routes parentRoute) {
		this.parentRoute = parentRoute;
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
		Subscribers other = (Subscribers) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscribers [id=" + id + ", name=" + name + ", type=" + type + ", route=" + route + ", parentRoute="
				+ parentRoute + "]";
	}

}
