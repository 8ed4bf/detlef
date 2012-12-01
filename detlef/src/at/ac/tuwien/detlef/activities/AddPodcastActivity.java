
package at.ac.tuwien.detlef.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import at.ac.tuwien.detlef.R;
import at.ac.tuwien.detlef.domain.Podcast;

import com.commonsware.cwac.merge.MergeAdapter;

public class AddPodcastActivity extends Activity {

    private final MergeAdapter mergeAdapter = new MergeAdapter();
    private PodcastListAdapter resultAdapter;
    private PodcastListAdapter toplistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_podcast_activity);
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        /* Set up our merged list. */

        TextView tv = new TextView(this);
        tv.setText(R.string.search_results);
        mergeAdapter.addView(tv);

        resultAdapter = new PodcastListAdapter(this, android.R.layout.simple_list_item_1,
                new ArrayList<Podcast>());
        mergeAdapter.addAdapter(resultAdapter);

        tv = new TextView(this);
        tv.setText(R.string.popular_podcasts);
        mergeAdapter.addView(tv);

        toplistAdapter = new PodcastListAdapter(this, android.R.layout.simple_list_item_1,
                new ArrayList<Podcast>());
        fillToplistWithDummies();
        mergeAdapter.addAdapter(toplistAdapter);

        ListView lv = (ListView) findViewById(R.id.result_list);
        lv.setAdapter(mergeAdapter);
    }

    private void fillToplistWithDummies() {
        Podcast p = new Podcast();
        p.setTitle("Bestest podcast evar");
        p.setDescription("This is the bestest bestest bestest bestest bestest bestest bestest bestest podcast evar");
        toplistAdapter.add(p);

        p = new Podcast();
        p.setTitle("Somebody set me up the bomb");
        p.setDescription("This is the bestest bestest bestest bestest bestest bestest bestest bestest podcast evar");
        toplistAdapter.add(p);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This adapter displays a list of podcasts which the user can subscribe to.
     */
    private static class PodcastListAdapter extends ArrayAdapter<Podcast> {

        private final List<Podcast> podcasts;

        public PodcastListAdapter(Context context, int textViewResourceId, List<Podcast> objects) {
            super(context, textViewResourceId, objects);
            podcasts = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Podcast podcast = podcasts.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.add_podcast_list_layout, null);
            }

            v.setTag(podcast);

            TextView podcastName = (TextView) v.findViewById(R.id.podcast_name);
            podcastName.setText(podcast.getTitle());

            TextView podcastDesc = (TextView) v.findViewById(R.id.podcast_description);
            podcastDesc.setText(podcast.getDescription());

            return v;

        }
    }
}