package com.example.thefriendzone;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginUnitTest {
    @Test
    public void login_isCorrect() {
        assertEquals("username" + "password", "username" + "password");
    }
}