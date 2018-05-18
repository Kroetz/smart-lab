package de.qaware.smartlabcommons.data.action;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@NoArgsConstructor
@Slf4j
public class ActionResult<T> implements IActionResult {

    private T value;

    private ActionResult(T value) {
        this.value = value;
    }

    public static <T> ActionResult<T> empty() {
        return new ActionResult<>(null);
    }

    public static <T> ActionResult<T> of(T value) {
        return new ActionResult<>(value);
    }

    private <U> Optional<U> getCastedValue(Class<? extends U> castTargetClass) {
        try {
            return Optional.ofNullable(castTargetClass.cast(this.value));
        }
        catch(ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public Void getVoidValue() {
        return null;
    }

    @Override
    public Optional<String> getStringValue() {
        return getCastedValue(String.class);
    }

    @Override
    public Optional<MultipartFile> getMultipartFileValue() {
        return getCastedValue(MultipartFile.class);
    }
}
