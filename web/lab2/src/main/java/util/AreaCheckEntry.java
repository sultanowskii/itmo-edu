package util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public record AreaCheckEntry(Integer x, Double y, Integer r, boolean isInside, Instant timestamp) {
    public AreaCheckEntry(Integer x, Double y, Integer r, boolean isInside) {
        this(x, y, r, isInside, Instant.now());
    }

    public String toJSON() {
        var gson = new Gson();
        Map<String, Object> json = new HashMap<>();
        json.put("x", this.x());
        json.put("y", this.y());
        json.put("r", this.r());
        json.put("isInside", this.isInside());
        json.put("timestamp", this.timestamp().getEpochSecond());
        return gson.toJson(json);
    }
}