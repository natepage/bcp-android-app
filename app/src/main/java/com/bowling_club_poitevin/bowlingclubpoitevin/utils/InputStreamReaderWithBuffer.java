package com.bowling_club_poitevin.bowlingclubpoitevin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class InputStreamReaderWithBuffer {
    public static String getInputStreamContent(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        StringBuilder str = new StringBuilder();
        CharBuffer buf = CharBuffer.allocate(2048);

        while(reader.read(buf) != -1) {
            buf.flip();
            str.append(buf);
            buf.clear();
        }

        return str.toString();
    }
}
