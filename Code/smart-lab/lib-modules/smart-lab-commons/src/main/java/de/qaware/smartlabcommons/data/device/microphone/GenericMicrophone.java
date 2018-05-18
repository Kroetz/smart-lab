package de.qaware.smartlabcommons.data.device.microphone;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GenericMicrophone implements AutoCloseable {

    // TODO: Encoding als Param hinzufügen: z.B AudioFormat.Encoding PCM_FLOAT
    public static final AudioFormat DEFAULT_AUDIO_FORMAT = new AudioFormat(16000.0f, 8, 2, true, true);
    private static final Map<String, GenericMicrophone> microphoneCache = new HashMap<>();

    private final AtomicBoolean isMicrophoneClosed;
    private final AtomicBoolean isMicrophoneLocked;
    private final String name;
    private final Mixer mixer;
    private final TargetDataLine targetDataLine;
    private final AudioFormat audioFormat;
    private Path lastRecordedFile;

    private GenericMicrophone(Mixer mixer, TargetDataLine targetDataLine, AudioFormat audioFormat) {
        this.isMicrophoneClosed = new AtomicBoolean(false);
        this.isMicrophoneLocked = new AtomicBoolean(false);
        this.mixer = mixer;
        this.name = mixer.getMixerInfo().getName();
        this.targetDataLine = targetDataLine;
        this.audioFormat = audioFormat;
    }

    public String getName() {
        return this.name;
    }

    public AudioFormat getAudioFormat() {
        return this.audioFormat;
    }

    public CompletableFuture<Void> startRecording(Path path, AudioFileFormat.Type fileType) throws IllegalStateException {
        if(this.isMicrophoneClosed.get()) {
            throw new IllegalStateException("The microphone was already closed");
        }
        log.info("Trying to lock microphone");
        if(isMicrophoneLocked.compareAndSet(false, true)) {
            log.info("Microphone locked");
            return CompletableFuture.supplyAsync(() -> blockingStartRecording(path, fileType));
        }
        log.info("Microphone could not be locked");
        throw new IllegalStateException("Microphone is already in use");
    }

    private Void blockingStartRecording(Path path, AudioFileFormat.Type fileType) {
        try {
            log.info("Starting recording");
            this.lastRecordedFile = path;
            this.targetDataLine.open(this.audioFormat);
            this.targetDataLine.start();
            AudioInputStream audioInputStream = new AudioInputStream(this.targetDataLine);
            AudioSystem.write(audioInputStream, fileType, path.toFile());
        }
        catch (LineUnavailableException e) {
            log.error("Could not start recording since target data line is not available", e);
        }
        catch (IOException e) {
            log.error("Could not start recording because if I/O error", e);
        }
        catch (Exception e) {
            log.error("Could not start recording because unexpected error", e);
        }
        finally {
            log.info("Unlocking microphone");
            isMicrophoneLocked.set(false);
        }
        return null;
    }

    public Path stopRecording() {
        /*
        Create copy of the path object because just returning it might return the false path if another recording job
        is started right after closing the target data line.
         */
        Path recordedFile = Paths.get(this.lastRecordedFile.toUri());
        this.targetDataLine.stop();
        this.targetDataLine.close();
        log.info("Stopped recording");
        return recordedFile;
    }

    public synchronized static Optional<GenericMicrophone> getMicrophone() {
        return getMicrophone(DEFAULT_AUDIO_FORMAT);
    }

    public synchronized static Optional<GenericMicrophone> getMicrophone(String name) {
        return getMicrophone(name, DEFAULT_AUDIO_FORMAT);
    }

    public synchronized static Optional<GenericMicrophone> getMicrophone(AudioFormat audioFormat) {
        try {
            TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
            Optional<Mixer> mixerOptional = getMixerByTargetDataLine(targetDataLine);
            return mixerOptional.flatMap(mixer -> getFromCache(mixer, targetDataLine, audioFormat));
        }
        catch (LineUnavailableException e) {
            return Optional.empty();
        }
    }

    public synchronized static Optional<GenericMicrophone> getMicrophone(String name, AudioFormat audioFormat) {
        Set<Mixer> mixersWithMatchingName = getMicrophoneMixers().stream()
                .filter(mixer -> mixer.getMixerInfo().getName().equals(name))
                .collect(Collectors.toSet());
        if(mixersWithMatchingName.size() > 1) throw new IllegalStateException("Multiple microphones with the same name exist");
        return mixersWithMatchingName.stream()
                .findFirst()
                .flatMap(mixer -> getMicrophone(mixer, audioFormat));
    }

    public synchronized static Optional<GenericMicrophone> getMicrophone(Mixer mixer, AudioFormat audioFormat) {
        return getFromCache(mixer, audioFormat);
    }

    public synchronized static Set<GenericMicrophone> getMicrophones() {
        // TODO: Filtering out empty Java Optionals is simpler with Java 9 (see http://www.baeldung.com/java-filter-stream-of-optional)
        return getMicrophoneMixers().stream()
                .map(mixer -> getFromCache(mixer, DEFAULT_AUDIO_FORMAT))
                .flatMap(microphone -> microphone.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toSet());
    }

    private static Set<Mixer> getMicrophoneMixers() {
        return getMixers().stream()
                .filter(mixer -> Arrays.stream(mixer.getTargetLineInfo())
                        .anyMatch(lineInfo -> lineInfo.getLineClass().equals(TargetDataLine.class)))
                .collect(Collectors.toSet());
    }

    private static Optional<Mixer> getMixerByTargetDataLine(Line targetDataLine) {
        Set<Mixer> matchingMixers = getMicrophoneMixers().stream()
                .filter(mixer -> Arrays.stream(mixer.getTargetLineInfo())
                        .anyMatch(targetLineInfo -> targetLineInfo.equals(targetDataLine.getLineInfo())))
                .collect(Collectors.toSet());
        if(matchingMixers.size() > 1) throw new IllegalStateException("Multiple microphones with the same target line info exist");
        return matchingMixers.stream().findFirst();
    }

    private static Set<Mixer> getMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo())
                .map(AudioSystem::getMixer)
                .collect(Collectors.toSet());
    }

    private static Optional<GenericMicrophone> getFromCache(Mixer mixer, AudioFormat audioFormat) {
        TargetDataLine targetDataLine;
        try {
            targetDataLine = (TargetDataLine) mixer.getLine(new DataLine.Info(TargetDataLine.class, audioFormat));
        }
        catch (LineUnavailableException e) {
            log.error("Could not create microphone since target data line is not available", e);
            return Optional.empty();
        }
        return getFromCache(mixer, targetDataLine, audioFormat);
    }

    private static Optional<GenericMicrophone> getFromCache(Mixer mixer, TargetDataLine targetDataLine, AudioFormat audioFormat) {
        String microphoneName = mixer.getMixerInfo().getName();
        if(!microphoneCache.containsKey(microphoneName)) {
            microphoneCache.put(microphoneName, new GenericMicrophone(mixer, targetDataLine, audioFormat));
        }
        else if(microphoneCache.containsKey(microphoneName) && !microphoneCache.get(microphoneName).getAudioFormat().matches(audioFormat)) {
            throw new RuntimeException("The microphone is already configured with another audio format which needs to be closed first");
        }
        return Optional.of(microphoneCache.get(microphoneName));
    }

    @Override
    public synchronized void close() {
        log.info("Closing microphone \"{}\"", this.name);
        stopRecording();
        microphoneCache.remove(this.name);
        this.isMicrophoneClosed.set(true);
        log.info("Microphone \"{}\" closed", this.name);
    }
}
