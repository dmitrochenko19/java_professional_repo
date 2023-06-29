package org.example.notes;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
@EqualsAndHashCode
public abstract class AbstractNote {
    @Getter
    protected int value;
}
