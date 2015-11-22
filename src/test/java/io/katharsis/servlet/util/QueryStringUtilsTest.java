package io.katharsis.servlet.util;

import io.katharsis.invoker.KatharsisInvokerContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public abstract class QueryStringUtilsTest {

    protected static final String[] FOO_PARAM_VALUES = {"bar", "foo"};
    protected static final String[] LUX_PARAM_VALUES = {"bar"};

    protected KatharsisInvokerContext invokerContext;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @Test
    public void testParseQueryStringAsMultiValuesMap() throws Exception {
        Map<String, String[]> parsedQueryStringMap = QueryStringUtils.parseQueryStringAsMultiValuesMap(invokerContext);
        assertTrue("parsedQueryStringMap must contain foo[asd].", parsedQueryStringMap.containsKey("foo[asd]"));
        assertTrue("parsedQueryStringMap must have 2 values for foo[asd].",
            parsedQueryStringMap.get("foo[asd]").length == 2);
        assertEquals(FOO_PARAM_VALUES[0], parsedQueryStringMap.get("foo[asd]")[0]);
        assertEquals(FOO_PARAM_VALUES[1], parsedQueryStringMap.get("foo[asd]")[1]);
        assertTrue("parsedQueryStringMap must contain lux.", parsedQueryStringMap.containsKey("lux"));
        assertTrue("parsedQueryStringMap must have 1 value for lux.", parsedQueryStringMap.get("lux").length == 1);
        assertEquals(LUX_PARAM_VALUES[0], parsedQueryStringMap.get("lux")[0]);
        assertFalse(parsedQueryStringMap.containsKey("nameonly"));
    }

    @Test
    public void testParseQueryStringAsSingleValueMap() throws Exception {
        Map<String, Set<String>> parsedQueryStringMap = QueryStringUtils.parseQueryStringAsSingleValueMap(invokerContext);
        assertTrue("parsedQueryStringMap must contain foo[asd].", parsedQueryStringMap.containsKey("foo[asd]"));
        assertThat(parsedQueryStringMap.get("foo[asd]")).containsOnly(FOO_PARAM_VALUES[0]);
        assertTrue("parsedQueryStringMap must contain lux.", parsedQueryStringMap.containsKey("lux"));
        assertThat(parsedQueryStringMap.get("lux")).containsOnly(FOO_PARAM_VALUES[0]);
        assertFalse(parsedQueryStringMap.containsKey("nameonly"));
    }

    @Test
    public void testParseQueryStringWithBlankQueryString() throws Exception {
        request.setQueryString(null);
        Map<String, Set<String>> parsedQueryStringMap = QueryStringUtils.parseQueryStringAsSingleValueMap(invokerContext);
        assertNotNull(parsedQueryStringMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());
        Map<String, String[]> parsedQueryStringsMap = QueryStringUtils.parseQueryStringAsMultiValuesMap(invokerContext);
        assertNotNull(parsedQueryStringsMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());
        assertFalse(parsedQueryStringMap.containsKey("nameonly"));

        request.setQueryString("");
        parsedQueryStringMap = QueryStringUtils.parseQueryStringAsSingleValueMap(invokerContext);
        assertNotNull(parsedQueryStringMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());
        parsedQueryStringsMap = QueryStringUtils.parseQueryStringAsMultiValuesMap(invokerContext);
        assertNotNull(parsedQueryStringsMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());

        request.setQueryString("    ");
        parsedQueryStringMap = QueryStringUtils.parseQueryStringAsSingleValueMap(invokerContext);
        assertNotNull(parsedQueryStringMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());
        parsedQueryStringsMap = QueryStringUtils.parseQueryStringAsMultiValuesMap(invokerContext);
        assertNotNull(parsedQueryStringsMap);
        assertTrue("parsedQueryStringMap must be empty: " + parsedQueryStringMap, parsedQueryStringMap.isEmpty());
    }
}
