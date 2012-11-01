
package at.ac.tuwien.detlef.db;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import at.ac.tuwien.detlef.domain.Episode;
import at.ac.tuwien.detlef.domain.Episode.State;
import at.ac.tuwien.detlef.domain.Podcast;

/**
 * tests the episodeDAOImpl
 *
 * @author Lacky
 */
public class EpisodeDAOImplTest extends AndroidTestCase {

    Episode e1, e2;

    Podcast p1;

    @Override
    protected void setUp() throws Exception {
        p1 = new Podcast();
        p1.setDescription("description");
        p1.setLastUpdate(111);
        p1.setLogoFilePath("logo file path");
        p1.setLogoUrl("logo url");
        p1.setTitle("title");
        p1.setUrl("die url halt");

        e1 = new Episode();
        e1.setAuthor("author");
        e1.setDescription("description");
        e1.setFileSize("filesize");
        e1.setGuid("guid");
        e1.setLink("link");
        e1.setMimetype("mimetype");
        e1.setReleased(System.currentTimeMillis());
        e1.setTitle("title");
        e1.setUrl("url");
        e1.setState(State.NEW);
        e1.setFilePath("path");

        e2 = new Episode();
        e2.setAuthor("author");
        e2.setDescription("description");
        e2.setFileSize("filesize");
        e2.setGuid("guid");
        e2.setLink("link");
        e2.setMimetype("mimetype");
        e2.setReleased(System.currentTimeMillis());
        e2.setTitle("title");
        e2.setUrl("url");
        e2.setState(State.NEW);
        e2.setFilePath("path");

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * tests the getAllEpisodes functionality
     */
    public void testGetAllEpisodes() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        assertNotNull("List of episodes shouldn't be null", edao.getAllEpisodes());
    }

    /**
     * tests the insertEpisode functionality
     */
    public void testInsertEpisode() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        int countBeforeInsert = edao.getAllEpisodes().size();
        long newKey = edao.insertEpisode(e1);
        int countAfterInsert = edao.getAllEpisodes().size();
        assertEquals(countBeforeInsert + 1, countAfterInsert);
        assertTrue(newKey > 0);
    }

    /**
     * tests the deleteEpisode functionality
     */
    public void testDeleteEpisode() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        e1.setId(edao.insertEpisode(e1));
        int countBeforeDelete = edao.getAllEpisodes().size();
        int ret = edao.deleteEpisode(e1);
        int countAfterDelete = edao.getAllEpisodes().size();
        assertEquals(1, ret);
        assertEquals(countBeforeDelete - 1, countAfterDelete);
    }

    /**
     * tests the getEpisodes functionality
     */
    public void testGetEpisodes() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        e1.setId(edao.insertEpisode(e1));
        ArrayList<Episode> episodes = (ArrayList<Episode>)edao.getEpisodes(p1);
        assertEquals(1, episodes.size());
        Episode ep = episodes.get(0);
        assertEquals(e1.getFilePath(), ep.getFilePath());
        assertEquals(e1.getMimetype(), ep.getMimetype());
    }

    /**
     * tests the updateState functionality
     */
    public void testUpdateState() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        e1.setId(edao.insertEpisode(e1));
        State newState = State.DOWNLOADED;
        e1.setState(newState);
        assertEquals(1, edao.updateState(e1));
        ArrayList<Episode> eps = (ArrayList<Episode>)edao.getEpisodes(p1);
        Episode ep = eps.get(0);
        assertEquals(newState, ep.getState());
    }

    /**
     * tests the updateFilePath functionality
     */
    public void testUpdateFilePath() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        e1.setId(edao.insertEpisode(e1));
        String newPath = "a wholy shit new path";
        e1.setFilePath(newPath);
        assertEquals(1, edao.updateFilePath(e1));
        ArrayList<Episode> eps = (ArrayList<Episode>)edao.getEpisodes(p1);
        Episode ep = eps.get(0);
        assertEquals(newPath, ep.getFilePath());
    }

    /**
     * tests the deletePodcast functionality which forces a on delete cascade
     * for the episodes
     */
    public void testDeletePodcastCascade() {
        EpisodeDAOImpl edao = new EpisodeDAOImpl(this.mContext);
        PodcastDAOImpl pdao = new PodcastDAOImpl(this.mContext);
        p1.setId(pdao.insertPodcast(p1));
        e1.setPodcast(p1);
        e1.setId(edao.insertEpisode(e1));
        e2.setPodcast(p1);
        e2.setId(edao.insertEpisode(e2));
        assertEquals(1, pdao.deletePodcast(p1));
        ArrayList<Episode> eps = (ArrayList<Episode>)edao.getEpisodes(p1);
        assertEquals(0, eps.size());
    }
}