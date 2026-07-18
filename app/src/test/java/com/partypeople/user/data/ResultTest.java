package com.partypeople.user.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ResultTest {

    @Test
    public void success_carriesDataAndNoError() {
        Result<String> result = Result.success("hello");
        assertTrue(result.isSuccess());
        assertEquals("hello", result.getData());
        assertNull(result.getError());
    }

    @Test
    public void error_carriesErrorAndNoData() {
        Throwable boom = new RuntimeException("boom");
        Result<String> result = Result.error(boom);
        assertFalse(result.isSuccess());
        assertEquals(boom, result.getError());
        assertNull(result.getData());
    }

    @Test
    public void success_rejectsNull() {
        assertThrows(NullPointerException.class, () -> Result.success(null));
    }

    @Test
    public void error_rejectsNull() {
        assertThrows(NullPointerException.class, () -> Result.error(null));
    }
}
