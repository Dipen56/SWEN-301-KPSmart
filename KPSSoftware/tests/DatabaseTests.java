import main.KPSDatabase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DatabaseTests {

    @Test
    public void test1DbAddUser() {
        // create data base
        KPSDatabase db = new KPSDatabase();
        // measure initial size of database
        int initial = db.getLogins().size();
        // add user to data base
        assertTrue(db.addUser("test1","test1","",
                "","","",false));
        // test user was added
        assertEquals(initial+1, db.getLogins().size());
    }
    @Test
    public void test2DbCheckLogin() {
        // create data base
        KPSDatabase db = new KPSDatabase();
        // measure initial size of database
        int initial = db.getLogins().size();
        // add user to data base
        assertTrue(db.addUser("test2","test2","",
                "","","",false));
        // test user was added
        assertEquals(initial+1, db.getLogins().size());
        // test database validates new user
        assertEquals(true, db.checkLogin("test2", "test2"));
    }
    @Test
    public void test3DbRemoveUser() {
        // create data base
        KPSDatabase db = new KPSDatabase();
        // measure initial size of database
        int initial = db.getLogins().size();
        // add user to data base
        assertTrue(db.addUser("test3","test3","",
                "","","",false));
        // test user was added
        assertEquals(initial+1, db.getLogins().size());
        // remove user
        assertTrue(db.removeUser("test3", "test3"));
        // test user was removed
        assertEquals(initial, db.getLogins().size());
        // test database doesnt validate removed user
        assertEquals(false, db.checkLogin("test3", "test3"));
    }
}
