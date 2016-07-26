package hr.aportolan.kps.provisioning.enums;

public enum KpsConstants {
	PROVISIONING_SYSTEM_ENDPOINT_URL_HEADER("ENDPOINT_URL"), PROVISIONING_REQUEST_ID(
			"REQUEST_ID"), CORRELATION_IDENTIFIER("CORRELATION_ID"), REQUEST_METHOD("REQUEST_METHOD");

	private final String value;

	private KpsConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
