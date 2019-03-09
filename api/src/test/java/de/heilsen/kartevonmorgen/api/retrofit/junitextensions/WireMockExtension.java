package de.heilsen.kartevonmorgen.api.retrofit.junitextensions;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WireMockExtension implements BeforeAllCallback, AfterAllCallback {
    private WireMockServer wireMockServer;

    public String url(String path) {
        return wireMockServer.url(path);
    }

    /**
     * @return baseUrl (i.e. protocol://host:port/) with a trailing "/"
     */
    public String url() {
        return url("/");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (wireMockServer == null) {
            wireMockServer = new WireMockServer();
        }
        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
        }
    }
}
