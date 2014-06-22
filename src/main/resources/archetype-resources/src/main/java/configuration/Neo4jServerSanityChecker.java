#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.configuration;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ConnectException;

import static java.lang.String.format;

@Component
public class Neo4jServerSanityChecker {

    private final OkHttpClient httpClient;
    private final String serverUri;

    @Autowired
    public Neo4jServerSanityChecker(OkHttpClient httpClient,
                                    @Value("${symbol_dollar}{neo4j.server.uri}") String serverUri) {

        this.httpClient = httpClient;
        this.serverUri = serverUri;
    }

    @PostConstruct
    public void checkServerIsAlive() throws IOException {
        try {
            httpClient.newCall(simpleGetRequest()).execute();
        }
        catch (ConnectException exception) {
            throw new IOException(format("Unreachable Neo4j server at %s. Have you started it?", serverUri), exception);
        }
    }

    private Request simpleGetRequest() {
        return new Request.Builder()
                .url(serverUri)
                .build();
    }
}
