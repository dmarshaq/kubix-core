package org.dmarshaq.kubix.core.serialization;

import java.util.List;

public interface ResourceManager {
    void loadResources(List<String> resources);
    void loadResourcesJar();
    static List<String> extractResourcesOfFiletype(List<String> resources, String fileType) {
        return resources.stream().filter(s -> s.endsWith(fileType)).toList();
    }
}
