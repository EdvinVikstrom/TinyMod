package net.fabricmc.tiny.utils.exceptions;

import java.io.IOException;

public class ArrayLengthNotEqualException extends IOException {

    public ArrayLengthNotEqualException(String message)
    {
        super(message);
    }
}
