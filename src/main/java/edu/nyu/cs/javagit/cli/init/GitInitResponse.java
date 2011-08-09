package edu.nyu.cs.javagit.cli.init;

import edu.nyu.cs.javagit.cli.ICommandResponse;

public class GitInitResponse implements ICommandResponse {

    public boolean initialized   = false;
    public boolean reinitialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isReinitialized() {
        return reinitialized;
    }

    public void setReinitialized(boolean reinitialized) {
        this.reinitialized = reinitialized;
    }

    public boolean containsError() {
        // TODO Auto-generated method stub
        return false;
    }
}
