package mn.foreman.chisel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** A {@link Gpu} provides a POJO representation of a GPU. */
public class Gpu {

    /** The bus ID. */
    private final int busId;

    /** The clock rates. */
    private final Clocks clocks;

    /** The fan speed. */
    private final int fan;

    /** The ID. */
    private final int id;

    /** The name. */
    private final String name;

    /** The running processes. */
    private final List<String> processes;

    /** The temperature. */
    private final int temp;

    /**
     * Constructor.
     *
     * @param builder The builder.
     */
    private Gpu(final Builder builder) {
        this.busId = builder.busId;
        this.clocks = builder.clocks;
        this.fan = builder.fan;
        this.id = builder.id;
        this.name = builder.name;
        this.processes = new ArrayList<>(builder.processes);
        this.temp = builder.temp;
    }

    /**
     * Returns the bus ID.
     *
     * @return The bus ID.
     */
    public int getBusId() {
        return this.busId;
    }

    /**
     * Returns the clocks.
     *
     * @return The clocks.
     */
    public Clocks getClocks() {
        return this.clocks;
    }

    /**
     * Returns the fan.
     *
     * @return The fan.
     */
    public int getFan() {
        return this.fan;
    }

    /**
     * Returns the id.
     *
     * @return The id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name.
     *
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the processes.
     *
     * @return The processes.
     */
    public List<String> getProcesses() {
        return Collections.unmodifiableList(this.processes);
    }

    /**
     * Returns the temp.
     *
     * @return The temp.
     */
    public int getTemp() {
        return this.temp;
    }

    /** A builder for creating new {@link Gpu GPUs}. */
    public static class Builder {

        /** The bus ID. */
        private int busId;

        /** The clock rates. */
        private Clocks clocks = new Clocks.Builder().build();

        /** The fan speed. */
        private int fan;

        /** The ID. */
        private int id;

        /** The name. */
        private String name;

        /** The running processes. */
        private List<String> processes = new LinkedList<>();

        /** The temperature. */
        private int temp;

        /**
         * Adds a process.
         *
         * @param process The process.
         *
         * @return This builder instance.
         */
        public Builder addProcess(final String process) {
            this.processes.add(process);
            return this;
        }

        /**
         * Builds the new {@link Gpu}.
         *
         * @return The new {@link Gpu}.
         */
        public Gpu build() {
            return new Gpu(this);
        }

        /**
         * Sets the bus id.
         *
         * @param busId The bus id.
         *
         * @return This builder instance.
         */
        public Builder setBusId(final int busId) {
            this.busId = busId;
            return this;
        }

        /**
         * Sets the clocks.
         *
         * @param clocks The clocks.
         *
         * @return This builder instance.
         */
        public Builder setClocks(final Clocks clocks) {
            this.clocks = clocks;
            return this;
        }

        /**
         * Sets the fan.
         *
         * @param fan The fan.
         *
         * @return This builder instance.
         */
        public Builder setFan(final int fan) {
            this.fan = fan;
            return this;
        }

        /**
         * Sets the fan.
         *
         * @param fan The fan.
         *
         * @return This builder instance.
         */
        public Builder setFan(final String fan) {
            return setFan(Integer.parseInt(fan));
        }

        /**
         * Sets the id.
         *
         * @param id The id.
         *
         * @return This builder instance.
         */
        public Builder setId(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return This builder instance.
         */
        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the temp.
         *
         * @param temp The temp.
         *
         * @return This builder instance.
         */
        public Builder setTemp(final int temp) {
            this.temp = temp;
            return this;
        }

        /**
         * Sets the temp.
         *
         * @param temp The temp.
         *
         * @return This builder instance.
         */
        public Builder setTemp(final String temp) {
            return setTemp(Integer.parseInt(temp));
        }
    }
}
