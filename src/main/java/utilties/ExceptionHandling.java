package utilties;

public class ExceptionHandling {

	public static interface ThrowsException<T> {
		T doMethod() throws Exception;
	}

	public static interface ThrowsExceptionNoReturn {
		void doMethod() throws Exception;
	}

	public static <T> T runTimeExceptionise(ThrowsException<T> method) {
		try {
			return method.doMethod();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void runTimeExceptionise(ThrowsExceptionNoReturn method) {
		try {
			method.doMethod();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
