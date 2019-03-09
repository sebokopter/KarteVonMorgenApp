package de.heilsen.kartevonmorgen.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

class ServerSpecification {

    private RequestSpecification requestSpec;

    @BeforeEach
    void setUp() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept("application/json");
        requestSpecBuilder.setContentType("application/json");
        requestSpecBuilder.setBaseUri("https://api.ofdb.io/v0/");
        requestSpec = requestSpecBuilder.build();
    }

    @Test
    void search_bbox() {
        given().
                spec(requestSpec).
        when().
                get("/search?bbox={latMin},{lngMin},{latMax},{lngMax}",
                        47.29541440362851, 2.3431777954101567, 53.97012306226697, 17.80094146728516).
        thenReturn()
                .as(SearchResult.class, new MoshiObjectMapper<>(SearchResult.class));
    }

    @Test
    void search_textAndBbox() {
        given().
                spec(requestSpec).
        when().
                get("/search?text={text}&bbox={latMin},{lngMin},{latMax},{lngMax}",
                        "lebensmittel",
                        47.29541440362851, 2.3431777954101567, 53.97012306226697, 17.80094146728516).
        thenReturn()
                .as(SearchResult.class, new MoshiObjectMapper<>(SearchResult.class));
    }

    @Test
    void search_textAndBboxAndCategories() {
        given().
                spec(requestSpec).
        when().
                get("/search?text={text}&bbox={latMin},{lngMin},{latMax},{lngMax}&categories={category}",
                        "lebensmittel",
                        47.29541440362851, 2.3431777954101567, 53.97012306226697, 17.80094146728516,
                        "77b3c33a92554bcf8e8c2c86cedd6f6f"
                ).
        thenReturn()
                .as(SearchResult.class, new MoshiObjectMapper<>(SearchResult.class));
    }

    @Test
    void getServerVersion() {
        RestAssured.registerParser("text/plain", Parser.TEXT);
        given().
                spec(requestSpec).
        when().
                get("/server/version").
        then().
                statusCode(HttpURLConnection.HTTP_OK).
                contentType(ContentType.TEXT).
                body(Matchers.matchesPattern("^\\d+\\.\\d+\\.\\d+$"));
    }

    class MoshiObjectMapper<T> implements ObjectMapper {
        private Class<T> clazz;
        private Moshi moshi;

        MoshiObjectMapper(Class<T> clazz) {
            this.clazz = clazz;
            moshi = new Moshi.Builder().build();
        }

        public Object deserialize(ObjectMapperDeserializationContext context) {
            final String toDeserialize = context.getDataToDeserialize().asString();
            try {
                return moshi.adapter(clazz).fromJson(toDeserialize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Object serialize(ObjectMapperSerializationContext context) {
            final T objectToSerialize = context.getObjectToSerializeAs(clazz);
            JsonAdapter<T> jsonAdapter = moshi.adapter(clazz);
            return jsonAdapter.toJson(objectToSerialize);
        }
    }
}