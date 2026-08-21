package multibaygaragesimulator.model;

public class ServiceReport {
    private final String bayName;
    private final int jobId;
    private final double durationMs;
    private final boolean status;

    public ServiceReport(int jobId, String bayName, double durationMs, boolean status) {
        this.jobId = jobId;
        this.bayName = bayName;
        this.durationMs = durationMs;
        this.status = status;
    }
    public String getBayName() {return bayName;}
    public int getJobId() {return jobId;}
    public double getDurationMs() {return durationMs;}
    public boolean isStatus() {return status;}

    @Override
    public String toString() {
        return String.format("[Job ID: %d, Task: %s, Duration: %.1f milliseconds -- Status: %b]",  jobId, bayName, durationMs, status);
    }
}
