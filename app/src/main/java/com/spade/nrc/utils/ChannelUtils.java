package com.spade.nrc.utils;

import com.spade.nrc.R;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ayman Abouzeid on 1/17/18.
 */

public class ChannelUtils {

    public static final int RADIO_HITS_POS = 0;
    public static final int MEGA_POS = 1;
    public static final int NAGHAM_POS = 2;
    public static final int SH3BY_POS = 3;

    public static final String MEGA_FACEBOOK_URL = "https://www.facebook.com/927megafm/";
    public static final String NAGHAM_FACEBOOK_URL = "https://www.facebook.com/Nagham105.3/";
    public static final String RADIO_HITS_FACEBOOK_URL = "https://www.facebook.com/RadioHits882/";
    public static final String SH3BY_FACEBOOK_URL = "https://www.facebook.com/95fmradio/";

    private static ChannelUtils channelUtils;
    private List<Channel> channelList = new ArrayList<>();

    public static ChannelUtils getInstance() {
        if (channelUtils == null)
            channelUtils = new ChannelUtils();
        return channelUtils;
    }

    public static int getPresenterDefaultImage(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_default_presenter;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_default_presenter;
            case Constants.SH3BY_ID:
                return R.drawable.ic_sh3by_default_presenter;
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radio_default_presenter;
            default:
                return R.drawable.ic_radio_default_presenter;
        }
    }

    public static int getPresenterDefaultImageWhite(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_default_presenter_white;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_default_presenter_white;
            case Constants.SH3BY_ID:
                return R.drawable.ic_sh3by_default_presenter_white;
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radio_default_presenter_white;
            default:
                return R.drawable.ic_radio_default_presenter_white;
        }
    }

    public static int getShowDefaultImage(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_default_show;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_default_show;
            case Constants.SH3BY_ID:
                return R.drawable.ic_sh3by_default_show;
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radio_default_show;
            default:
                return R.drawable.ic_radio_default_show;
        }
    }

    public static int getChannelImage(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_menu;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_menu;
            case Constants.SH3BY_ID:
                return R.drawable.ic_95_menu;
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radiohits_menu;
            default:
                return R.drawable.ic_radiohits_menu;
        }
    }

    public static int getChannelSecondaryColor(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.color.mega_second_color;
            case Constants.NAGHAM_ID:
                return R.color.nagham_second_color;
            case Constants.SH3BY_ID:
                return R.color.sh3by_95_second_color;
            case Constants.RADIO_HITS_ID:
                return R.color.radio_hits_second_color;
            default:
                return R.color.nrc_second_color;
        }
    }

    public static int getChannelPrimaryColor(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.color.mega_primary_color;
            case Constants.NAGHAM_ID:
                return R.color.nagham_primary_color;
            case Constants.SH3BY_ID:
                return R.color.sh3by_95_primary_color;
            case Constants.RADIO_HITS_ID:
                return R.color.radio_hits_primary_color;
            default:
                return R.color.nrc_primary_color;
        }
    }

    public static int getChannelDetailsColorList(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.color.mega_color_list;
            case Constants.NAGHAM_ID:
                return R.color.nagham_color_list;
            case Constants.SH3BY_ID:
                return R.color.sh3by_color_list;
            case Constants.RADIO_HITS_ID:
                return R.color.radio_color_list;
            default:
                return R.color.radio_color_list;
        }
    }

    public static int getChannelSchedulesColorList(int channelID) {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                return R.color.mega_schedule_color_list;
            case Constants.NAGHAM_ID:
                return R.color.nagham_schedule_color_list;
            case Constants.SH3BY_ID:
                return R.color.sh3by_schedule_color_list;
            case Constants.RADIO_HITS_ID:
                return R.color.radio_schedule_color_list;
            default:
                return R.color.radio_schedule_color_list;
        }
    }


    public static int getChannelRightArrow(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radio_right_arrow;
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_right_arrow;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_right_arrow;
            case Constants.SH3BY_ID:
                return R.drawable.ic_sh3by_right_arrow;
            case Constants.SEARCH:
                return R.drawable.ic_sh3by_right_arrow;
            default:
                return R.drawable.ic_radio_right_arrow;
        }
    }

    public static int getChannelBackground(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_hits_gradient_background;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_gradient_background;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_background;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_gradient_background;
            default:
                return R.drawable.radio_hits_gradient_background;
        }
    }

    public static int getChannelIllustration(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_bg;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_bg;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_bg;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_bg;
            default:
                return R.drawable.radio_bg;
        }
    }

    public static int getChannelLogo(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radio_logo;
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_logo;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_logo;
            case Constants.SH3BY_ID:
                return R.drawable.ic_sh3by_logo;
            default:
                return R.drawable.ic_radio_logo;
        }
    }

    public static int getChannelMenuLogo(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_radiohits_menu;
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_mega_menu;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_nagham_menu;
            case Constants.SH3BY_ID:
                return R.drawable.ic_95_menu;
            default:
                return R.drawable.ic_radiohits_menu;
        }
    }

    public static int getChannelTitle(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.string.radio_hits;
            case Constants.MEGA_FM_ID:
                return R.string.mega_fm;
            case Constants.NAGHAM_ID:
                return R.string.nagham;
            case Constants.SH3BY_ID:
                return R.string.sh3by;
            default:
                return R.string.radio_hits;
        }
    }

    public static int getChannelID(int position) {
        switch (position) {
            case RADIO_HITS_POS:
                return Constants.RADIO_HITS_ID;
            case MEGA_POS:
                return Constants.MEGA_FM_ID;
            case NAGHAM_POS:
                return Constants.NAGHAM_ID;
            case SH3BY_POS:
                return Constants.SH3BY_ID;
            default:
                return Constants.RADIO_HITS_ID;
        }
    }

    public static int getChannelPosition(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return RADIO_HITS_POS;
            case Constants.MEGA_FM_ID:
                return MEGA_POS;
            case Constants.NAGHAM_ID:
                return NAGHAM_POS;
            case Constants.SH3BY_ID:
                return SH3BY_POS;
            default:
                return RADIO_HITS_POS;
        }
    }

    public static int getChannelPlayBtn(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_play_btn_radio;
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_play_btn_mega;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_play_btn_nagham;
            case Constants.SH3BY_ID:
                return R.drawable.ic_play_btn_sh3by;
            default:
                return R.drawable.ic_play_btn_radio;
        }
    }

    public static int getChannelPauseBtn(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.ic_pause_btn_radio;
            case Constants.MEGA_FM_ID:
                return R.drawable.ic_pause_btn_mega;
            case Constants.NAGHAM_ID:
                return R.drawable.ic_pause_btn_nagham;
            case Constants.SH3BY_ID:
                return R.drawable.ic_pause_btn_sh3by;
            default:
                return R.drawable.ic_pause_btn_radio;
        }
    }

    public static int getPresenterImageBackground(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_circle;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_circle;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_circle;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_circle;
            default:
                return R.drawable.radio_circle;
        }
    }

    public static int getLiveStreamingDefault(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_live_streaming_bg;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_live_streaming_bg;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_live_streaming_bg;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_live_streaming_bg;
            default:
                return R.drawable.radio_live_streaming_bg;
        }
    }

    public static int getPlayerDefault(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_player_default;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_player_default;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_player_default;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_player_default;
            default:
                return R.drawable.radio_player_default;
        }
    }

    public static int getPlayerBackground(int channelID) {
        switch (channelID) {
            case Constants.RADIO_HITS_ID:
                return R.drawable.radio_player_bg;
            case Constants.MEGA_FM_ID:
                return R.drawable.mega_player_bg;
            case Constants.NAGHAM_ID:
                return R.drawable.nagham_player_bg;
            case Constants.SH3BY_ID:
                return R.drawable.sh3by_player_bg;
            default:
                return R.drawable.radio_player_bg;
        }
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }

    public Observable<Channel> getChannelById(int channelID) {
        return Observable.create(e -> {
            for (Channel channel : getChannelList()) {
                if (channel.getId() == channelID) {
                    e.onNext(channel);
                    e.onComplete();
                }
            }
        });
    }
}
