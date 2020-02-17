package mn.foreman.chisel.service.smi;

import mn.foreman.chisel.model.Clocks;
import mn.foreman.chisel.model.Gpu;
import mn.foreman.chisel.service.GpuService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A {@link NvidiaSmiService} provides a service for getting GPU metrics from
 * nvidia-smi.
 */
@Service
@Profile("smi")
public class NvidiaSmiService
        implements GpuService {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(NvidiaSmiService.class);

    @Override
    public List<Gpu> getGpuStats() {
        final List<Gpu> gpus = new LinkedList<>();
        final ProcessBuilder processBuilder =
                new ProcessBuilder(
                        new SmiCommandBuilder()
                                .queryName()
                                .queryPciBus()
                                .queryGpuTemperature()
                                .queryFanSpeed()
                                .queryCoreClock()
                                .queryMemoryClock()
                                .csv()
                                .nounits()
                                .noheader()
                                .toCommand());
        try {
            final Process process = processBuilder.start();
            try (final InputStream inputStream = process.getInputStream()) {
                final String output =
                        IOUtils.toString(
                                inputStream,
                                Charset.defaultCharset());
                process.waitFor();

                // Parse the output and generate the GPUs
                addGpus(
                        output,
                        gpus);
            } catch (final InterruptedException ie) {
                LOG.warn("Exception occurred while getting stats", ie);
            }
        } catch (final IOException ioe) {
            LOG.warn("Exception occurred while getting stats", ioe);
        }

        return gpus;
    }

    /**
     * Adds the {@link Gpu} extracted from the provided line.
     *
     * @param line  The line to parse.
     * @param gpuId The GPU id.
     * @param dest  The destination.
     */
    private static void addGpu(
            final String line,
            final int gpuId,
            final List<Gpu> dest) {
        final String[] segments = line.split(", ");
        // Should be 6 segments to the nvidia-smi output
        if (segments.length == 6) {
            dest.add(
                    new Gpu.Builder()
                            .setId(gpuId)
                            .setName(segments[0])
                            .setBusId(Integer.decode(segments[1]))
                            .setTemp(toInt(segments[2]))
                            .setFan(toInt(segments[3]))
                            .setClocks(
                                    new Clocks.Builder()
                                            .setCore(toInt(segments[4]))
                                            .setMemory(toInt(segments[5]))
                                            .build())
                            .build());
        }
    }

    /**
     * Parses the output and extracts all of the {@link Gpu GPUs} from it.
     *
     * @param output The output to parse.
     * @param dest   Where to add the extracted {@link Gpu GPUs}.
     */
    private static void addGpus(
            final String output,
            final List<Gpu> dest) {
        int counter = 0;
        final Scanner scanner = new Scanner(output);
        while (scanner.hasNextLine()) {
            try {
                addGpu(
                        scanner.nextLine(),
                        counter++,
                        dest);
            } catch (final Exception e) {
                LOG.warn("Exception occurred while adding GPU", e);
            }
        }
    }

    /**
     * Converts the provided value to an int.
     *
     * @param value The value to convert.
     *
     * @return The int value.
     */
    private static int toInt(final String value) {
        try {
            return Integer.valueOf(value);
        } catch (final Exception e) {
            LOG.warn("Exception occurred while converting {} to int",
                    value,
                    e);
            return 0;
        }
    }
}