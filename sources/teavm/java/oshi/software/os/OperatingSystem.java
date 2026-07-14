package oshi.software.os;

public interface OperatingSystem {
    String getManufacturer();
    String getFamily();
    String getVersionInfo();
    boolean isElevated();

    default OSProcess getCurrentProcess() {
        return new OSProcess() {
            @Override public String getName() { return "eaglercraft"; }
            @Override public int getProcessID() { return 0; }
            @Override public long getResidentSetSize() { return 0; }
            @Override public long getVirtualSize() { return 0; }
            @Override public long getStartTime() { return 0; }
            @Override public long getUserTime() { return 0; }
            @Override public long getKernelTime() { return 0; }
            @Override public long getUpTime() { return 0; }
            @Override public String getPath() { return ""; }
            @Override public String getCommandLine() { return ""; }
            @Override public State getState() { return State.RUNNING; }
            @Override public int getThreadCount() { return 1; }
            @Override public int getParentProcessID() { return 0; }
        };
    }
}
