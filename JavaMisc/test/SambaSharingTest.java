/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jsie
 */
public class SambaSharingTest {

    private final String path = "smb://ylalsrv01/jsie-home/";
    private final String filename = "android.txt";
    private final String userpwd = "jsie:qsec0fr";

    public SambaSharingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testSambaConnexion() throws Exception {

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(userpwd);
        SmbFile file = new SmbFile(path, auth);
        assert file.canRead();
        assert file.isDirectory();
    }

    @Test
    public void testWrite() throws Exception {

        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(userpwd);
        SmbFile file = new SmbFile(path + filename, auth);
        if (!file.exists()) {
            file.createNewFile();
        }

        assertTrue(file.canRead());
        assertTrue(file.isFile());
        assertFalse(file.isDirectory());
        assertTrue(file.canWrite());

        SmbFileOutputStream sfos = new SmbFileOutputStream(file);
        assertTrue(sfos.isOpen());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - kk:mm:ss");

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> e : System.getenv().entrySet()) {
            sb.append("[").append(e.getKey()).append(" = ").append(e.getValue()).append("]\n");
        }

        for (Map.Entry<Thread, StackTraceElement[]> e : Thread.getAllStackTraces().entrySet()) {
            sb.append("{").append(e.getKey()).append(" = ").append(e.getValue()).append("}\n");
        }

        String message = sb.toString() + "\n" + sdf.format(calendar.getTime()) + "\n";
        sfos.write(message.getBytes());
        sfos.close();

    }

    @Test
    public void testExploreFiles() throws Exception {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(userpwd);
        SmbFile file = new SmbFile(path, auth);

        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        assertFalse(file.isHidden());

        System.out.println(file.getCanonicalPath());

        exploreDirectory(file);
    }

    private void exploreDirectory(SmbFile file) throws Exception {
        assertTrue(file.isDirectory());

        if (file.canRead()) {
            SmbFile[] files = file.listFiles();

            for (SmbFile f : files) {
                System.out.println(f.getCanonicalPath());
                if (f.isDirectory() && !f.isHidden()) {
                    exploreDirectory(f);
                }
            }
        }
    }
}
