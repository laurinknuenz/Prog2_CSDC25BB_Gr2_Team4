package at.ac.fhcampuswien.fhmdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private HomeController sut;

    @BeforeEach
    void setup() {
        sut = new HomeController();
    }

    @Test
    void test1() {
        assertNotNull(sut);
    }
}