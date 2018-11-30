package mn.foreman.chisel.service.smi;

import java.util.LinkedList;
import java.util.List;

/** A builder for creating nvidia-smi commands. */
public class SmiCommandBuilder {

    /** Whether or not to CSV the output. */
    private boolean csv = false;

    /** Whether or not to display the header. */
    private boolean noheader = false;

    /** Whether or not to display units. */
    private boolean nounits = false;

    /** Whether or not to query for the core clock. */
    private boolean queryCoreClock = false;

    /** Whether or not to query for the fan speed. */
    private boolean queryFanSpeed = false;

    /** Whether or not to query for the GPU temperature. */
    private boolean queryGpuTemperature = false;

    /** Whether or not to query for the memory clock. */
    private boolean queryMemoryClock = false;

    /** Whether or not to query for the name. */
    private boolean queryName = false;

    /** Whether or not to query for the PCI bus. */
    private boolean queryPciBus = false;

    /**
     * Sets whether or not to display as CSV.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder csv() {
        this.csv = true;
        return this;
    }

    /**
     * Sets whether or not to display the header.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder noheader() {
        this.noheader = true;
        return this;
    }

    /**
     * Sets whether or not to display units.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder nounits() {
        this.nounits = true;
        return this;
    }

    /**
     * Sets whether or not to query for the core clock.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryCoreClock() {
        this.queryCoreClock = true;
        return this;
    }

    /**
     * Sets whether or not to query for the fan speed.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryFanSpeed() {
        this.queryFanSpeed = true;
        return this;
    }

    /**
     * Sets whether or not to query for the GPU temperature.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryGpuTemperature() {
        this.queryGpuTemperature = true;
        return this;
    }

    /**
     * Sets whether or not to query for the memory clock.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryMemoryClock() {
        this.queryMemoryClock = true;
        return this;
    }

    /**
     * Sets whether or not to query for the name.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryName() {
        this.queryName = true;
        return this;
    }

    /**
     * Sets whether or not to query for the PCI bus.
     *
     * @return This builder instance.
     */
    public SmiCommandBuilder queryPciBus() {
        this.queryPciBus = true;
        return this;
    }

    /**
     * Creates a command from the builder configuration.
     *
     * @return The command.
     */
    public String[] toCommand() {
        final List<String> segments = new LinkedList<>();
        segments.add("nvidia-smi");

        final List<String> queries = new LinkedList<>();
        if (this.queryName) {
            queries.add("name");
        }
        if (this.queryPciBus) {
            queries.add("pci.bus");
        }
        if (this.queryGpuTemperature) {
            queries.add("temperature.gpu");
        }
        if (this.queryFanSpeed) {
            queries.add("fan.speed");
        }
        if (this.queryCoreClock) {
            queries.add("clocks.max.sm");
        }
        if (this.queryMemoryClock) {
            queries.add("clocks.max.memory");
        }

        if (!queries.isEmpty()) {
            segments.add(
                    "--query-gpu=" +
                            String.join(
                                    ",",
                                    queries));
        }

        final List<String> formatting = new LinkedList<>();
        if (this.csv) {
            formatting.add("csv");
        }
        if (this.nounits) {
            formatting.add("nounits");
        }
        if (this.noheader) {
            formatting.add("noheader");
        }

        if (!formatting.isEmpty()) {
            segments.add(
                    "--format=" +
                            String.join(
                                    ",",
                                    formatting));
        }

        final String[] segmentsArray = new String[segments.size()];
        return segments.toArray(segmentsArray);
    }
}