package com.util.datastore;

import com.util.datastore.api.DataStoreOperations;
import com.util.datastore.impl.DataStoreImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;


public class FileOperationsTest {

    private DataStoreOperations dataStoreOperations = new DataStoreImpl();


    @Test
    public void shouldNotReturnError_WhenProperKeyAndValueIsPassedToCreate() throws Exception {
        JSONObject map = new JSONObject();
        map.put("key", "value");

        dataStoreOperations.create(map, "one");
        dataStoreOperations.create(map, "two");

        assertNotNull(dataStoreOperations.read("one"));
        assertEquals(dataStoreOperations.read("one").toMap(), map.toMap());
        assertTrue(dataStoreOperations.delete("two"));
    }


}
