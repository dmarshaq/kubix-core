package org.dmarshaq.serialization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Packet {
    private final String header;
    private final byte[] data;
}
