package hr.aportolan.kps.util;

public class KpsUtils {
	public static Throwable getRootException(Throwable t) {
		Throwable result = t;

		if (result.getCause() != null)
			getRootException(result).getCause();
		return result;
	}
}
