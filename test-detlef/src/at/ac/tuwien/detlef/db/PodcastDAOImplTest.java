
package at.ac.tuwien.detlef.db;

import android.test.AndroidTestCase;
import at.ac.tuwien.detlef.domain.Podcast;

/**
 * Testcases for PodcastDAOImpl
 *
 * @author Lacky
 */
public class PodcastDAOImplTest extends AndroidTestCase {

    Podcast p1;

    @Override
    protected void setUp() throws Exception {
        p1 = new Podcast();
        p1.setDescription("description");
        p1.setLastUpdate(111);
        p1.setLogoFilePath("logoFilePath");
        p1.setLogoUrl("logoUrl");
        p1.setTitle("title");
        p1.setUrl("url");
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * test the getAllPodcast functionality
     */
    public void testGetAllPodcasts() {
        PodcastDAOImpl dao = PodcastDAOImpl.i(this.mContext);
        assertNotNull("List of podcasts shouldn't be null", dao.getAllPodcasts());
    }

    /**
     * tests the insertPodcast functionality
     */
    public void testInsertPodcast() {
        PodcastDAOImpl pdao = PodcastDAOImpl.i(this.mContext);
        int countBeforeInsert = pdao.getAllPodcasts().size();
        long newKey = pdao.insertPodcast(p1);
        int countAfterInsert = pdao.getAllPodcasts().size();
        assertEquals(countBeforeInsert + 1, countAfterInsert);
        assertTrue(newKey > 0);
    }

    /**
     * tests the deletePodcast functionality
     */
    public void testDeletePodcast() {
        PodcastDAOImpl pdao = PodcastDAOImpl.i(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        int countBeforeDelete = pdao.getAllPodcasts().size();
        int ret = pdao.deletePodcast(p1);
        int countAfterDelete = pdao.getAllPodcasts().size();
        assertEquals(1, ret);
        assertNotSame(countBeforeDelete, countAfterDelete);
    }

    /**
     * tests the getPodcastById functionality
     */
    public void testGetPodcastById() {
        PodcastDAOImpl pdao = PodcastDAOImpl.i(this.mContext);
        p1.setTitle("expected title");
        p1.setId(pdao.insertPodcast(p1));
        Podcast pod = pdao.getPodcastById(p1.getId());
        assertEquals("expected title", pod.getTitle());
        assertEquals(p1.getLastUpdate(), pod.getLastUpdate());
    }

    /**
     * tests the updateLastUpdate functionality
     */
    public void testUpdateLastUpdate() {
        PodcastDAOImpl pdao = PodcastDAOImpl.i(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        long currentMilis = System.currentTimeMillis();
        p1.setLastUpdate(currentMilis);
        assertEquals(1, pdao.updateLastUpdate(p1));
        Podcast pod = pdao.getPodcastById(p1.getId());
        assertEquals(currentMilis, pod.getLastUpdate());
    }

    /**
     * tests the updateLogoFilePath functionality
     */
    public void testUpdateLogoFilePath() {
        PodcastDAOImpl pdao = PodcastDAOImpl.i(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        String newFilePath = "new path haha";
        p1.setLogoFilePath(newFilePath);
        assertEquals(1, pdao.updateLogoFilePath(p1));
        Podcast pod = pdao.getPodcastById(p1.getId());
        assertEquals(newFilePath, pod.getLogoFilePath());
    }

}