package exception;

public class ItensInsuficientesException extends RuntimeException {

	private static final long serialVersionUID = -5746623830208457615L;

	public ItensInsuficientesException() {
		super();
	}

	public ItensInsuficientesException(String message) {
		super(message);
	}

}
