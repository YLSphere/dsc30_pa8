import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    HashTable test;

    @Before
    public void setUp() throws Exception {
        test = new HashTable();

    }


    @Test (expected = NullPointerException.class)
    public void insertEx() {
        test.insert(null);
    }

    @Test
    public void insert() {
        test.insert("hello");
        test.insert("meow");
        test.insert("who");
        test.insert("mmmm");
        assertFalse(test.insert("hello"));

        test.insert("dad");
        assertTrue(test.lookup("dad"));

        test.insert("wowie");
        test.insert("aasdd");
        test.insert("hjklk");
        test.insert("lmononio");
        test.insert("njhkjg");
        test.insert("a");
        test.insert("ab");
        assertEquals(40, test.capacity());





    }


    @Test (expected = NullPointerException.class)
    public void deleteEx() {
        test.delete(null);
    }

    @Test
    public void delete() {
        test.insert("ab");
        test.delete("ab");
        assertFalse(test.lookup("ab"));

        assertFalse(test.delete("anime girls"));

        test.insert("chickens");
        assertFalse(test.delete("chiBkBens"));
    }

    @Test (expected = NullPointerException.class)
    public void lookupEx() {
        test.lookup(null);
    }

    @Test
    public void lookup() {
        assertFalse(test.lookup("does not exist"));

        test.insert("my soul");
        assertTrue(test.lookup("my soul"));

       test.delete("my soul");

       assertFalse(test.lookup("my will to live"));

    }

    @Test
    public void size() {
        assertEquals(0, test.size());
        test.insert("a");
        test.insert("b");
        assertEquals(2, test.size());
        test.delete("b");
        assertEquals(1, test.size());
    }

    @Test
    public void capacity() {
        HashTable test1 = new HashTable(10);
        assertEquals(10, test1.capacity());
        HashTable test2 = new HashTable(13);
        assertEquals(12, test2.capacity());
        test1.insert("a");
        test1.insert("b");
        test1.insert("c");
        test1.insert("d");
        test1.insert("e");
        test1.insert("f");

        test1.insert("g");
        assertEquals(20, test1.capacity());
    }

    @Test
    public void testToString() {
        HashTable test1 = new HashTable(10);
        test1.insert("a");
        test1.insert("b");
        test1.insert("c");
        test1.insert("sdad");
        test1.insert("e");
        test1.insert("f");
        assertEquals("| index | table 1 | table2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | e | [NULL] |\n" +
                "| 2 | f | a |\n" +
                "| 3 | b | [NULL] |\n" +
                "| 4 | sdad | c |\n", test1.toString());

        test1.insert("g");
        assertEquals("| index | table 1 | table2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | e | [NULL] |\n" +
                "| 2 | f | [NULL] |\n" +
                "| 3 | g | [NULL] |\n" +
                "| 4 | sdad | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | a | [NULL] |\n" +
                "| 8 | b | [NULL] |\n" +
                "| 9 | c | [NULL] |\n", test1.toString());
        assertEquals("| index | table 1 | table2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | [NULL] | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | [NULL] | [NULL] |\n" +
                "| 8 | [NULL] | [NULL] |\n" +
                "| 9 | [NULL] | [NULL] |\n",test.toString());
    }

    @Test
    public void getStatsLog() {
       assertEquals("", test.getStatsLog());
       test.insert("a");
       test.insert("b");
       test.insert("c");
       test.insert("d");
        test.insert("gasfsad");
        test.insert("asdgsda");
        test.insert("aer");
        test.insert("qwe");
        test.insert("dafdaf");
       test.insert("e");
       test.insert("f");
       test.insert("g");
       test.insert("h");
       assertEquals("Before rehash # 1: load factor 0.55, 4 evictions.\n", test.getStatsLog());
        test.insert("eqw");
        test.insert("j");
        test.insert("k");
        test.insert("l");
        test.insert("knjkds");
        test.insert("ldasd");
        test.insert("ldas");
        test.insert("ldad");
        test.insert("ldasdw");
        assertEquals("Before rehash # 1: load factor 0.55, 4 evictions.\n" +
                "Before rehash # 2: load factor 0.525, 9 evictions.\n", test.getStatsLog());
    }
}