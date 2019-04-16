package pw.ollie.psbuildcheck.check;

import java.util.Date;

public final class BuildCheck {
    private final int x;
    private final int y;
    private final int z;
    private final Date submitted;
    private final String submitter;

    public BuildCheck(int x, int y, int z, Date submitted, String submitter) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.submitted = submitted;
        this.submitter = submitter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public String getSubmitter() {
        return submitter;
    }
}
