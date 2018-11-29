package mn.foreman.chisel;

import mn.foreman.chisel.model.Gpu;
import mn.foreman.chisel.service.GpuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link StatsController} provides an HTTP API endpoint that returns
 * JSON-encoded GPU statistics.
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

    /** The service capable of providing {@link Gpu} stats. */
    private final GpuService service;

    /**
     * Constructor.
     *
     * @param service The service capable of providing {@link Gpu} stats.
     */
    @Autowired
    public StatsController(final GpuService service) {
        this.service = service;
    }

    /**
     * Returns statistics for every identified {@link Gpu}.
     *
     * @return The statistics.
     */
    @RequestMapping(
            value = "/gpus",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Gpu>> getGpus() {
        return Collections.singletonMap("gpus", this.service.getGpuStats());
    }
}
