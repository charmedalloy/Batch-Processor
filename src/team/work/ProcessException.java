package team.work;

@SuppressWarnings("serial")
public class ProcessException extends Exception
{
	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
