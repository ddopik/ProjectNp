package com.spade.nrc.utils;

import com.spade.nrc.R;

/**
 * Created by Ayman Abouzeid on 1/17/18.
 */

public class ImageUtils {

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
}
