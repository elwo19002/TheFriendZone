package com.example.thefriendzone;
import org.junit.Test;
import org.testng.annotations.AfterTest;
//import com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;
public class UsernameUnitTest {
    public static class UsernameValidatorTest {
        @Test
        public void username_isCorrect() {
            assertEquals("username", "username");
        }
    }
}
