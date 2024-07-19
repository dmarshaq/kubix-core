package org.dmarshaq.kubix.core.audio;

import lombok.Getter;
import org.dmarshaq.kubix.math.vector.Vector3;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

@Getter
public class Audio {
    private final long audioDevice;
    private final long audioContext;


    public Audio(String deviceName) {
        this.audioDevice = alcOpenDevice(deviceName);
        this.audioContext = alcCreateContext(audioDevice, new int[] {0});
    }

    public void makeContextCurrent() {
        alcMakeContextCurrent(audioContext);
    }

    public void createCapabilities() {
        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            System.out.println("Audio library not supported.");
        }
    }

    public void destroyAudio() {
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
    }

    /**
     * Sets listener's position and velocity in sound space.
     */
    public void setListener(Vector3 position, Vector3 velocity) {
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());
        alListener3f(AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
    }
}
