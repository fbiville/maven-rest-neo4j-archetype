#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cypher;

import com.google.gson.Gson;
import ${package}.configuration.ClientConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.api.Assertions.assertThat;

public class CypherQueryTest {

    private Gson gson;

    @Before
    public void prepare() {
        this.gson = new ClientConfiguration().gson();
    }

    @Test
    public void properly_serializes_parameterless_queries() {
        CypherQuery query = new CypherQuery("MATCH (w:Word)-[:IS_FOLLOWED_BY]->(w2:Word) RETURN w,w2");

        assertThat(gson.toJson(query))
                .isEqualTo("{" +
                    "${symbol_escape}"query${symbol_escape}":${symbol_escape}"MATCH (w:Word)-[:IS_FOLLOWED_BY]->(w2:Word) RETURN w,w2${symbol_escape}"," +
                    "${symbol_escape}"params${symbol_escape}":{}" +
                "}");
    }

    @Test
    public void properly_serializes_queries_with_parameters() {
        CypherQuery query = new CypherQuery("CREATE (w:Word {value: {value}}) RETURN w")
                                    .param("value", "Hello");

        assertThat(gson.toJson(query))
                .isEqualTo("{" +
                    "${symbol_escape}"query${symbol_escape}":${symbol_escape}"CREATE (w:Word {value: {value}}) RETURN w${symbol_escape}"," +
                    "${symbol_escape}"params${symbol_escape}":{" +
                        "${symbol_escape}"value${symbol_escape}":${symbol_escape}"Hello${symbol_escape}"" +
                    "}" +
                "}");
    }

    private HashMap<String, Object> params(String key, Object value) {
        HashMap<String, Object> parameters = newHashMap();
        parameters.put(key, value);
        return parameters;
    }

}