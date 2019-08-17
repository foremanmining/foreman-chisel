package mn.foreman.chisel.service.hive;

import mn.foreman.chisel.model.Gpu;
import mn.foreman.chisel.service.GpuService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@link HiveService} provides a service for getting GPU metrics from
 * HiveOS.
 */
@Service
@Profile("hive")
public class HiveService
        implements GpuService {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(HiveService.class);

    /** The object mapper. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Gpu> getGpuStats() {
        final List<Gpu> gpus = new LinkedList<>();

        final List<DetectedGpu> detectedGpus =
                getStats(
                        new TypeReference<List<DetectedGpu>>() {
                        },
                        "/run/hive/gpu-detect.json")
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .filter(gpu -> !"cpu".equals(gpu.brand))
                        .collect(Collectors.toList());
        final GpuStats gpuStats =
                getStats(
                        new TypeReference<GpuStats>() {
                        },
                        "/run/hive/gpu-stats.json")
                        .orElseGet(GpuStats::new);

        for (int i = 0; i < detectedGpus.size(); i++) {
            toGpu(
                    i,
                    detectedGpus.get(i),
                    gpuStats).ifPresent(gpus::add);
        }

        return gpus;
    }

    /**
     * Converts the provided {@link DetectedGpu} to a {@link Gpu}.
     *
     * @param detectedGpu The {@link DetectedGpu}.
     * @param gpuStats    The stats.
     *
     * @return The new {@link Gpu}.
     */
    private static Optional<Gpu> toGpu(
            final int index,
            final DetectedGpu detectedGpu,
            final GpuStats gpuStats) {
        Gpu gpu = null;

        final String busId = detectedGpu.busId;
        final int statsIndex = gpuStats.busIds.indexOf(busId);
        if (statsIndex >= 0) {
            gpu =
                    new Gpu.Builder()
                            .setId(index)
                            .setName(detectedGpu.name)
                            .setBusId(Integer.parseInt(busId.split(":")[0], 16))
                            .setTemp(
                                    Iterables.get(
                                            gpuStats.temps,
                                            statsIndex,
                                            "0"))
                            .setFan(
                                    Iterables.get(
                                            gpuStats.fans,
                                            statsIndex,
                                            "0"))
                            .build();
        }

        return Optional.ofNullable(gpu);
    }

    /**
     * Reads the provided file and parses the response.
     *
     * @param reference The class of the response.
     * @param path      The path.
     * @param <T>       The response class.
     *
     * @return The parsed response.
     */
    private <T> Optional<T> getStats(
            final TypeReference<T> reference,
            final String path) {
        T response = null;

        final File file = new File(path);
        if (file.exists() && file.canRead()) {
            try {
                response =
                        this.objectMapper.readValue(
                                file,
                                reference);
            } catch (final IOException ioe) {
                LOG.warn("Exception occurred while getting stats", ioe);
            }
        }

        return Optional.ofNullable(response);
    }
}