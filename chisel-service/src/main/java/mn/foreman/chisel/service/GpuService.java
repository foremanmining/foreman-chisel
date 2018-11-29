package mn.foreman.chisel.service;

import mn.foreman.chisel.model.Gpu;

import java.util.List;

/** A {@link GpuService} provides a service for getting GPU metrics. */
public interface GpuService {

    /**
     * Gets all of the {@link Gpu GPUs} currently in the system.
     *
     * @return The {@link Gpu GPUs}.
     */
    List<Gpu> getGpuStats();
}
