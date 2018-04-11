package com.spade.nrc.utils;

import android.util.Log;

import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.shows.model.Schedule;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/24/18.
 */

public class TextUtils {

    public static String getPresentersNames(List<Presenter> presenters) {
        String presenterNames = "";
        try {
            if (presenters != null && !presenters.isEmpty()) {
                for (int i = 0; i < presenters.size(); i++) {
                    if (i == presenters.size() - 1) {
                        presenterNames = presenterNames.concat(presenters.get(i).getName());
                    } else {
                        presenterNames = presenterNames.concat(presenters.get(i).getName()).concat(" - ");
                    }
                }
            }
            return presenterNames;
        } catch (Exception e) {
            Log.e("TextUtils", "Error ----->" + e.getMessage());
            return presenterNames;
        }


    }

    public static String getShowNames(List<Show> shows) {
        String presenterNames = "";

        if (shows != null && !shows.isEmpty()) {
            for (int i = 0; i < shows.size(); i++) {
                if (i == shows.size() - 1) {
                    presenterNames = presenterNames.concat(shows.get(i).getTitle());
                } else {
                    presenterNames = presenterNames.concat(shows.get(i).getTitle()).concat(" - ");
                }
            }
        }
        return presenterNames;
    }

    public static String getScheduleTimes(List<Schedule> schedules) {
        String scheduleTimes = "";
        if (schedules != null && !schedules.isEmpty()) {
            Schedule schedule = schedules.get(0);
            scheduleTimes = schedule.getStartsAt().concat(" - ").concat(schedule.getEndsAt());
        }
        return scheduleTimes;
    }

}
