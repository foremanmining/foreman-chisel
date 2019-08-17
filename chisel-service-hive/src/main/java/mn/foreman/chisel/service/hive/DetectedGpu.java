package mn.foreman.chisel.service.hive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A {@link DetectedGpu} provides a model representation of a GPU in a
 * gpu-detect response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class DetectedGpu {

    /** The brand. */
    @JsonProperty("brand")
    String brand;

    /** The bus ID. */
    @JsonProperty("busid")
    String busId;

    /** The GPU name. */
    @JsonProperty("name")
    String name;
}
