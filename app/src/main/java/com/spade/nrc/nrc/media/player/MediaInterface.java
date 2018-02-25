package com.spade.nrc.nrc.media.player;

import com.spade.nrc.ui.shows.model.Show;

/**
 * Created by Ayman Abouzeid on 1/23/18.
 */

public interface MediaInterface {
    void onLiveShowsLoaded();

    void onShowPlayClicked(Show show);
}
