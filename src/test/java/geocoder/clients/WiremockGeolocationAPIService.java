package geocoder.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Collections;
import java.util.Map;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockGeolocationAPIService implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;
    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor(8089);

        stubFor(
                get(urlEqualTo("/search?q=moscow"))
                        .willReturn(
                                aResponse().withStatus(500))
        );
        stubFor(
                get(urlEqualTo("/search?q=kazan+kremlevskaya+18"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("[{\"lon\":49.12976274048962,\"lat\":55.7948636}]")
                                        .withStatus(200))
        );
        stubFor(
                get(urlEqualTo("/search?q=dfjdfjfdk"))
                        .willReturn(
                                aResponse()
                                        .withBody(""))
        );
        stubFor(
          get(urlMatching("/gfg/([a-z1-9]*)"))
                  .atPriority(10)
                  .willReturn(aResponse().proxiedFrom("https://geocode.maps.co"))
        );

        return Collections.singletonMap("quarkus.rest-client.\"geocoder-api\".url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
