package org.dmarshaq.kubix.core.audio;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.dmarshaq.kubix.core.util.FileUtils.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_memory;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {
    private int bufferID;
    private int sourceId;

    private boolean isPlaying = false;

    public Sound(String path, boolean loops, float volume, float pitch) {
        // Allocate space to store the return information from stb
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_memory(loadAsByteBuffer(path), channelsBuffer, sampleRateBuffer);

        if (rawAudioBuffer == null) {
            System.out.println("Could not load sound + \""+ path +"\".");
            stackPop();
            stackPop();
            return;
        }

        // Retrieve the extra information that was stored in the buffers by stb
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        // Free
        stackPop();
        stackPop();

        // Find the correct openAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
//            System.out.println("Mono!");
        }
        else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
//            System.out.println("Stereo!");
        }

        bufferID = alGenBuffers();
        alBufferData(bufferID, format, rawAudioBuffer, sampleRate);

        // Generate the source
        sourceId = alGenSources();

        alSourcei(sourceId, AL_BUFFER, bufferID);
        alSourcei(sourceId, AL_LOOPING, loops ? 1 : 0);
        alSource3f(sourceId, AL_POSITION, 0.0f, 0.0f, 0.0f);
        alSourcef(sourceId, AL_GAIN, volume);
        alSourcef(sourceId, AL_PITCH, pitch);


        // Free stb raw audio buffer
        free(rawAudioBuffer);
    }

    public void delete() {
        alDeleteSources(sourceId);
        alDeleteBuffers(bufferID);
    }

    public void play() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
            alSource3f(sourceId, AL_POSITION, 0.0f, 0.0f, 0.0f);
        }

        if (!isPlaying) {
            alSourcePlay(sourceId);
            isPlaying = true;
        }
    }

    public void stop() {
        if (isPlaying) {
            alSourceStop(sourceId);
            isPlaying = false;
        }
    }

    public void setPosition(float x, float y, float z) {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }

    public void setVolume(float volume) {
        alSourcef(sourceId, AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceId, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }
        return isPlaying;
    }


}
