package fp.util.test;

class TestResults {

    private Integer successful = 0;
    private Integer exceptions = 0;

    public void incrementSuccessful() {
        this.successful += 1;
    }

    public void incrementExceptions() {
        this.exceptions += 1;
    }

    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    public Integer getExceptions() {
        return exceptions;
    }

    public void setExceptions(Integer exceptions) {
        this.exceptions = exceptions;
    }
}
