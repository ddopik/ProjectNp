package com.spade.nrc.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.nrc.ui.explore.model.SlideBannerResponse;
import com.spade.nrc.ui.presenters.model.PresentersResponse;
import com.spade.nrc.ui.shows.model.ShowsResponse;

import io.reactivex.Observable;

/**
 * Created by Ayman Abouzeid on 11/2/17.
 */

public class ApiHelper {

    private static final String BASE_URL = "http://dev.spade.studio/nrc/public/api/v1/{lang}/";
    private static final String PRESENTERS_LIST_URL = BASE_URL + "presenters";
    private static final String SHOWS_LIST_URL = BASE_URL + "shows";
    private static final String FEATURED_SHOWS_LIST_URL = BASE_URL + "getFeaturedShows";
    private static final String LIVE_SHOWS_LIST_URL = BASE_URL + "getLiveShows";
    private static final String SLIDING_BANNERS_URL = BASE_URL + "slidingBanners";
    private static final String SHOWS_BY_DAY = BASE_URL + "getShowsByDay/{day}";
    private static final String REGISTER_URL = BASE_URL + "register";
    private static final String LOGIN_URL = BASE_URL + "authenticate";
    private static final String ACTIVATE_URL = BASE_URL + "email/activate";
    private static final String LANG_PATH_PARAM = "lang";
    private static final String CHANNEL_PARAM = "channel";
    private static final String DAY_PARAM = "day";

    public static Observable<PresentersResponse> getPresenters(String appLang, String channelID) {
        return Rx2AndroidNetworking.get(PRESENTERS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .build()
                .getObjectObservable(PresentersResponse.class);
    }

    public static Observable<ShowsResponse> getShows(String appLang, String channelID) {
        return Rx2AndroidNetworking.get(SHOWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .build()
                .getObjectObservable(ShowsResponse.class);
    }

    public static Observable<ShowsResponse> getShowsByDay(String appLang, String day, String channelID) {
        return Rx2AndroidNetworking.get(SHOWS_BY_DAY)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addPathParameter(DAY_PARAM, day)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .build()
                .getObjectObservable(ShowsResponse.class);
    }

    public static Observable<ShowsResponse> getFeaturedShows(String appLang) {
        return Rx2AndroidNetworking.get(FEATURED_SHOWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(ShowsResponse.class);
    }

    public static Observable<ShowsResponse> getLiveShows(String appLang) {
        return Rx2AndroidNetworking.get(LIVE_SHOWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(ShowsResponse.class);
    }

    public static Observable<SlideBannerResponse> getSlidingBanners(String appLang) {
        return Rx2AndroidNetworking.get(SLIDING_BANNERS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(SlideBannerResponse.class);
    }

//
//    public static Observable<RegistrationResponse> registerUser(String appLang, String nameString, String emailString, String passwordString, String mobileNumberString,
//                                                                String birthDateString, String chassisString, String motorString, String nationalIdString,
//                                                                int modelId, int yearId, int trimId, int colorId, File... imageFiles) {
//        return Rx2AndroidNetworking.upload(REGISTER_URL)
//                .addPathParameter(LANG_PATH_PARAM, appLang)
//                .addHeaders("Content-Type", "multipart/form-data")
//                .addMultipartParameter("email", emailString)
//                .addMultipartParameter("password", passwordString)
//                .addMultipartParameter("name", nameString)
//                .addMultipartParameter("mobile_number", mobileNumberString)
//                .addMultipartParameter("birth_date", birthDateString)
//                .addMultipartParameter("national_id", nationalIdString)
//                .addMultipartParameter("car_model", String.valueOf(modelId))
//                .addMultipartParameter("car_year", String.valueOf(yearId))
//                .addMultipartParameter("chassis", chassisString)
//                .addMultipartParameter("motor", motorString)
//                .addMultipartParameter("car_trim", String.valueOf(trimId))
//                .addMultipartParameter("car_color", String.valueOf(colorId))
//                .addMultipartFile("national_id_front_image", imageFiles[0])
//                .addMultipartFile("national_id_back_image", imageFiles[1])
//                .build()
//                .getObjectObservable(RegistrationResponse.class);
//    }
//
//    public static void activateUser(String appLang, String code, ActivationActions activationActions) {
//        Rx2AndroidNetworking.post(ACTIVATE_URL)
//                .addPathParameter(LANG_PATH_PARAM, appLang)
//                .addBodyParameter("code", code)
//                .build()
//                .getAsString(new StringRequestListener() {
//                    @Override
//                    public void onResponse(String response) {
//                        activationActions.onActivationSuccess();
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        activationActions.onActivationFail(anError.getMessage());
//                    }
//                });
//    }
//
//    public static Observable<LoginResponse> loginUser(String appLang, String email, String password) {
//        return Rx2AndroidNetworking.post(LOGIN_URL)
//                .addPathParameter(LANG_PATH_PARAM, appLang)
//                .addBodyParameter("email", email)
//                .addBodyParameter("password", password)
//                .build()
//                .getObjectObservable(LoginResponse.class);
//    }
//
//
//    public interface ActivationActions {
//        void onActivationSuccess();
//
//        void onActivationFail(String message);
//    }
}
