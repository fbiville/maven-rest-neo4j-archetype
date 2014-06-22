#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.cypher;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CypherQuery {

    @SerializedName("query")
    private final String query;
    @SerializedName("params")
    private final Map<String,Object> parameters;

    public CypherQuery(String query) {

        this.query = query;
        this.parameters = Maps.<String,Object>newHashMap();
    }

    public CypherQuery param(String key, Object value) {
        parameters.put(key, value);
        return this;
    }

}
