package mixture.hutech.backend.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClinicStatusEnum {
    PENDING(1),
    COMPLETED(2),
    NO_SHOW(3);

    private final int value;
}
