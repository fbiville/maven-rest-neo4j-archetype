#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.google.gson.Gson;
import ${package}.configuration.ClientConfiguration;
import ${package}.cypher.CypherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

@Component
public class CypherRestClientExample {

    private final Invocation.Builder cypherEndpoint;
    private final Gson gson;

    @Autowired
    public CypherRestClientExample(Gson gson, WebTarget cypherEndpoint) {
        this.gson = gson;
        this.cypherEndpoint = cypherEndpoint
                .path("db/data/cypher")
                .request(APPLICATION_JSON_TYPE);
    }

    public String helloWorld(CypherQuery query) throws IOException {
        Entity<String> entity = entity(gson.toJson(query), APPLICATION_JSON_TYPE);
        return cypherEndpoint.buildPost(entity).invoke(String.class);
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
        CypherRestClientExample client = context.getBean(CypherRestClientExample.class);

        CypherQuery query = new CypherQuery(
                "CREATE (w:Word {value:{value1}})-[:IS_FOLLOWED_BY]->(w2:Word {value:{value2}}) " +
                "RETURN w,w2"
        ).param("value1", "Hello").param("value2","World!");
        System.out.println(client.helloWorld(query));
    }
}
