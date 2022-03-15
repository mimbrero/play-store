package fp.util.test;

public class TestResults {

    private int successful;
    private int exceptions;

    public void incrementSuccessful() {
        this.successful += 1;
    }

    public void incrementExceptions() {
        this.exceptions += 1;
    }

    public int getSuccessful() {
        return successful;
    }

    public int getExceptions() {
        return exceptions;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public void setExceptions(int exceptions) {
        this.exceptions = exceptions;
    }
}
