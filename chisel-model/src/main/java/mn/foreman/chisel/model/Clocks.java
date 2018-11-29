package mn.foreman.chisel.model;

/**
 * A {@link Clocks} provides a POJO representation of the clock rates of a GPU.
 */
public class Clocks {

    /** The core clock. */
    private final int core;

    /** The memory clock. */
    private final int memory;

    /**
     * Constructor.
     *
     * @param builder The builder.
     */
    private Clocks(final Builder builder) {
        this.core = builder.core;
        this.memory = builder.memory;
    }

    /**
     * Returns the core.
     *
     * @return The core.
     */
    public int getCore() {
        return this.core;
    }

    /**
     * Returns the memory.
     *
     * @return The memory.
     */
    public int getMemory() {
        return this.memory;
    }

    /** A builder for creating new {@link Clocks}. */
    public static class Builder {

        /** The core clock. */
        private int core = 0;

        /** The memory clock. */
        private int memory = 0;

        /**
         * Builds a new {@link Clocks}.
         *
         * @return The new {@link Clocks}.
         */
        public Clocks build() {
            return new Clocks(this);
        }

        /**
         * Sets the core.
         *
         * @param core The core.
         *
         * @return This builder instance.
         */
        public Builder setCore(final int core) {
            this.core = core;
            return this;
        }

        /**
         * Sets the memory.
         *
         * @param memory The memory.
         *
         * @return This builder instance.
         */
        public Builder setMemory(final int memory) {
            this.memory = memory;
            return this;
        }
    }
}
