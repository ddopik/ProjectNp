package com.spade.nrc.media.player;

import com.spade.nrc.base.BaseView;

/**
 * Created by Ayman Abouzeid on 1/20/18.
 */

public interface MediaPlayerView extends BaseView {

    void mediaIsPaused();

    void mediaIsPlayed();
}
