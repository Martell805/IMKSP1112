package org.example.anylogicspringboot.http.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpServletCacheWrapper extends HttpServletRequestWrapper {
    private final byte[] cachedBytes;

    public HttpServletCacheWrapper(HttpServletRequest request) throws IOException {
        super(request);
        cachedBytes = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachesInputStream(new ByteArrayInputStream(cachedBytes));
    }

    public static class CachesInputStream extends ServletInputStream {
        private final InputStream inputStream;

        public CachesInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            try {
                return inputStream.available() == 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
