package io.github.leassets.internal.framework.excel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DeserializationUtils {

    public static InputStream getFileInputStream(byte[] byteArray) {

        return new ByteArrayInputStream(byteArray);
    }
}
