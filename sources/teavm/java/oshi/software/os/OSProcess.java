package oshi.software.os;

public interface OSProcess {
    String getName();
    int getProcessID();
    long getResidentSetSize();
    long getVirtualSize();
    long getStartTime();
    long getUserTime();
    long getKernelTime();
    long getUpTime();
    String getPath();
    String getCommandLine();
    State getState();
    int getThreadCount();
    int getParentProcessID();

    enum State { NEW, RUNNING, SLEEPING, WAITING, ZOMBIE, STOPPED, OTHER, SUSPENDED }
}
