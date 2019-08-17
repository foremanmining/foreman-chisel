package mn.foreman.chisel.service.hive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A {@link GpuStats} provides a model representation of a gpu-stats response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class GpuStats {

    /** The bus ids. */
    @JsonProperty("busids")
    List<String> busIds;

    /** The fans. */
    @JsonProperty("fan")
    List<String> fans;

    /** The temps. */
    @JsonProperty("temp")
    List<String> temps;
}
