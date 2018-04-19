package com.spade.nrc.network;

import com.androidnetworking.common.Priority;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.spade.nrc.ui.ads.AdsResponse;
import com.spade.nrc.ui.channel.model.ChannelsDataResponse;
import com.spade.nrc.ui.channel.model.ChannelsResponse;
import com.spade.nrc.ui.channel.model.ChannelsSearchResponse;
import com.spade.nrc.ui.explore.model.LiveShowsResponse;
import com.spade.nrc.ui.explore.model.SlideBannerResponse;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.news.model.InnerNewsResponse;
import com.spade.nrc.ui.news.model.NewsResponse;
import com.spade.nrc.ui.news.model.RelatedNewsResponse;
import com.spade.nrc.ui.presenters.model.PresenterDetailsResponse;
import com.spade.nrc.ui.presenters.model.PresentersResponse;
import com.spade.nrc.ui.register.RegistrationResponse;
import com.spade.nrc.ui.search.view.NewsSearch.model.NewsSearchResponse;
import com.spade.nrc.ui.shows.model.AddChannelToFavouriteResponse;
import com.spade.nrc.ui.shows.model.AddShowToFavouriteResponse;
import com.spade.nrc.ui.shows.model.CurrentAndNextShowsResponse;
import com.spade.nrc.ui.shows.model.ShowDetailsResponse;
import com.spade.nrc.ui.shows.model.ShowsPagesResponse;
import com.spade.nrc.ui.shows.model.ShowsResponse;
import com.spade.sociallogin.SocialUser;

import java.io.File;

import io.reactivex.Observable;

import static com.spade.nrc.ui.login.presenter.LoginPresenterImpl.GOOGLE_TYPE;

/**
 * Created by Ayman Abouzeid on 11/2/17.
 */

public class ApiHelper {

    // DEV URL
    private static final String BASE_URL = "http://dev.spade.studio/nrc/public/api/v1/{lang}/";

    // LIVE UR:
//    private static final String BASE_URL = "http://admin.naghamfm1053.com/api/v1/{lang}/";

    private static final String PRESENTERS_LIST_URL = BASE_URL + "presenters";
    private static final String PRESENTERS_DETAILS_URL = BASE_URL + "presenters/{id}";
    private static final String SHOW_DETAILS_URL = BASE_URL + "shows/{id}";
    private static final String SHOWS_LIST_URL = BASE_URL + "shows";
    private static final String NEWS_LIST_URL = BASE_URL + "news";
    private static final String FEATURED_SHOWS_LIST_URL = BASE_URL + "getFeaturedShows";
    private static final String CURRENT_AND_NEXT_SHOWS = BASE_URL + "getCurrentAndNextShowsForChannel";
    private static final String CHANNELS_URL = BASE_URL + "channels";
    private static final String LIVE_SHOWS_LIST_URL = BASE_URL + "getLiveShows";
    private static final String SLIDING_BANNERS_URL = BASE_URL + "slidingBanners";
    private static final String SHOWS_BY_DAY = BASE_URL + "getShowsByDay/{day}";
    private static final String REGISTER_URL = BASE_URL + "auth/register";
    private static final String LOGIN_URL = BASE_URL + "auth/login";
    private static final String ACTIVATE_URL = BASE_URL + "email/activate";
    private static final String SOCIAL_LOGIN_USER_URL = BASE_URL + "auth/social";
    public static final String ADD_CHANNEL_TO_FAV = BASE_URL + "channel/{id}/favorite";
    public static final String ADD_SHOW_TO_FAV = BASE_URL + "show/{id}/favorite";
    private static final String EDIT_PROFILE_URL = BASE_URL + "profile/edit";
    private static final String ADS_URL = BASE_URL + "advertisements";
    public static final String SHOWS_SEARCH_URL = BASE_URL + "search/shows";
    public static final String CHANNELS_SEARCH_URL = BASE_URL + "search/channels";
    public static final String PRESENTERS_SEARCH_URL = BASE_URL + "search/presenters";
    public static final String NEWS_SEARCH_URL = BASE_URL + "search/news";
    private static final String USER_CHANNELS = BASE_URL + "channel/favorite";
    private static final String USER_SHOWS = BASE_URL + "show/favorite";
    private static final String INNER_NEWS_Url = BASE_URL + "news/{id}";
    private static final String RELATED_NEWS_URL = BASE_URL + "newsRelated/{id}";

    private static final String LANG_PATH_PARAM = "lang";
    private static final String CHANNEL_PARAM = "channel";
    private static final String DAY_PARAM = "day";
    private static final String PAGE_PARAM = "page";
    private static final String ID_PATH_PARAM = "id";
    private static final String AUTH_TOKEN = "Authorization";
    private static final String BEARER = "bearer";
    private static final String KEY = "search";


    public static Observable<PresentersResponse> getPresenters(String appLang, String channelID, String pageNumber) {
        return Rx2AndroidNetworking.get(PRESENTERS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .addQueryParameter(PAGE_PARAM, pageNumber)
                .build()
                .getObjectObservable(PresentersResponse.class);
    }

    public static Observable<PresenterDetailsResponse> getPresenterDetails(String appLang, String presenterID, String channelID) {
        return Rx2AndroidNetworking.get(PRESENTERS_DETAILS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addPathParameter(ID_PATH_PARAM, presenterID)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .build()
                .getObjectObservable(PresenterDetailsResponse.class);
    }

    public static Observable<ShowsPagesResponse> getShows(String appLang, String channelID, String pageNumber) {
        return Rx2AndroidNetworking.get(SHOWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .addQueryParameter(PAGE_PARAM, pageNumber)
                .build()
                .getObjectObservable(ShowsPagesResponse.class);
    }

    public static Observable<NewsResponse> getNews(String appLang) {
        return Rx2AndroidNetworking.get(NEWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(NewsResponse.class);
    }

    public static Observable<CurrentAndNextShowsResponse> getCurrentAndNextShows(String appLang, String channelID) {
        return Rx2AndroidNetworking.get(CURRENT_AND_NEXT_SHOWS)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addQueryParameter(CHANNEL_PARAM, channelID)
                .build()
                .getObjectObservable(CurrentAndNextShowsResponse.class);
    }

    public static Observable<ChannelsDataResponse> getChannels(String appLang, String authToken) {
        return Rx2AndroidNetworking.get(CHANNELS_URL)
                .addHeaders(AUTH_TOKEN, BEARER + " " + authToken)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(ChannelsDataResponse.class);
    }

    public static Observable<ShowDetailsResponse> getShowDetails(String appLang, String showID) {
        return Rx2AndroidNetworking.get(SHOW_DETAILS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addPathParameter(ID_PATH_PARAM, showID)
                .build()
                .getObjectObservable(ShowDetailsResponse.class);
    }

    public static Observable<ShowsResponse> getShowsByDay(String appLang, String day, String channelID, String pageNumber) {
        return Rx2AndroidNetworking.get(SHOWS_BY_DAY)
                .addQueryParameter(PAGE_PARAM, pageNumber)
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

    public static Observable<LiveShowsResponse> getLiveShows(String appLang) {
        return Rx2AndroidNetworking.get(LIVE_SHOWS_LIST_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(LiveShowsResponse.class);
    }

    public static Observable<SlideBannerResponse> getSlidingBanners(String appLang) {
        return Rx2AndroidNetworking.get(SLIDING_BANNERS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(SlideBannerResponse.class);
    }

    public static Observable<AdsResponse> getAds(String appLang) {
        return Rx2AndroidNetworking.get(ADS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .build()
                .getObjectObservable(AdsResponse.class);
    }

    public static Observable<RegistrationResponse> registerUser(UserModel userModel, String notificationToken, String appLang) {
        return Rx2AndroidNetworking.post(REGISTER_URL)
                .addQueryParameter("locale", appLang)
                .addBodyParameter("email", userModel.getUserEmail())
                .addBodyParameter("password", userModel.getPassword())
                .addBodyParameter("first_name", userModel.getFirstName())
                .addBodyParameter("last_name", userModel.getLastName())
                .addBodyParameter("phone_number", userModel.getUserPhone())
                .addBodyParameter("notification_token", notificationToken)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<RegistrationResponse> loginUser(UserModel userModel, String appLang, String notificationToken) {
        return Rx2AndroidNetworking.post(LOGIN_URL)
                .addBodyParameter("email", userModel.getUserEmail())
                .addBodyParameter("password", userModel.getPassword())
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addBodyParameter("notification_token", notificationToken)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<RegistrationResponse> socialLoginUSer(SocialUser socialUser, String type, String lang, String notificationToken) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(SOCIAL_LOGIN_USER_URL);
        if (type.equals(GOOGLE_TYPE)) {
            postRequestBuilder.addBodyParameter("google_id", socialUser.getUserId());
        } else {
            postRequestBuilder.addBodyParameter("facebook_id", socialUser.getUserId());
        }
        return postRequestBuilder
                .addBodyParameter("email", socialUser.getEmailAddress())
                .addBodyParameter("notification_token", notificationToken)
                .addPathParameter(LANG_PATH_PARAM, lang)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<RegistrationResponse> editProfile(UserModel userModel, String userToken, File imageFile, String notificationToken) {
        return Rx2AndroidNetworking.upload(EDIT_PROFILE_URL)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .addMultipartParameter("first_name", userModel.getFirstName())
                .addMultipartParameter("last_name", userModel.getLastName())
                .addMultipartParameter("phone", userModel.getUserPhone())
                .addMultipartParameter("notification_token", notificationToken)
                .addMultipartFile("image", imageFile)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(RegistrationResponse.class);
    }

    public static Observable<AddChannelToFavouriteResponse> addToFavourite(String id, String url, String userToken, String appLang) {
        return Rx2AndroidNetworking.post(url)
                .addPathParameter(ID_PATH_PARAM, id)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(AddChannelToFavouriteResponse.class);
    }

    public static Observable<AddShowToFavouriteResponse> addShowToFavourite(String id, String url,
                                                                            String userToken, String appLang) {
        return Rx2AndroidNetworking.post(url)
                .addPathParameter(ID_PATH_PARAM, id)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(AddShowToFavouriteResponse.class);
    }
//                .getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    boolean success = response.getBoolean("success");
//                    if (success) {
//                        addToFavCallBacks.addToFavSuccess();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    addToFavCallBacks.addToFavFailed(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                addToFavCallBacks.addToFavFailed(ErrorUtils.getErrors(anError));
//            }
//        });
//        AndroidNetworking.post(url)
//                .addPathParameter(ID_PATH_PARAM, id)
//                .addPathParameter(LANG_PATH_PARAM, appLang)
//                .addHeaders(AUTH_TOKEN, BEARER + " " + userToken)
//                .setPriority(Priority.HIGH)
//                .getResponseOnlyFromNetwork()
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                addToFavCallBacks.addToFavSuccess();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            addToFavCallBacks.addToFavFailed(e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        addToFavCallBacks.addToFavFailed(ErrorUtils.getErrors(anError));
//                    }
//                });
//    }

    public static Observable<ShowsPagesResponse> getSearchShows(String appLang, String key) {
        return Rx2AndroidNetworking.post(SHOWS_SEARCH_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addBodyParameter(KEY, key)
                .build()
                .getObjectObservable(ShowsPagesResponse.class);
    }

    public static Observable<ChannelsSearchResponse> getSearchChannels(String appLang, String key) {
        return Rx2AndroidNetworking.post(CHANNELS_SEARCH_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addBodyParameter(KEY, key)
                .build()
                .getObjectObservable(ChannelsSearchResponse.class);
    }

    public static Observable<ChannelsResponse> getUserChannels(String appLang, String authToken) {
        return Rx2AndroidNetworking.get(USER_CHANNELS)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addHeaders(AUTH_TOKEN, BEARER + " " + authToken)
                .build()
                .getObjectObservable(ChannelsResponse.class);
    }

    public static Observable<ShowsResponse> getUserShows(String appLang, String authToken) {
        return Rx2AndroidNetworking.get(USER_SHOWS)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addHeaders(AUTH_TOKEN, BEARER + " " + authToken)
                .build()
                .getObjectObservable(ShowsResponse.class);
    }


    public static Observable<PresentersResponse> getSearchPresenters(String appLang, String key) {
        return Rx2AndroidNetworking.post(PRESENTERS_SEARCH_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addBodyParameter(KEY, key)
                .build()
                .getObjectObservable(PresentersResponse.class);
    }

    public static Observable<NewsSearchResponse> getSearchNews(String appLang, String key) {
        return Rx2AndroidNetworking.post(NEWS_SEARCH_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addBodyParameter(KEY, key)
                .build()
                .getObjectObservable(NewsSearchResponse.class);
    }

    public static Observable<InnerNewsResponse> getInnerNews(String newsID, String appLang) {
        return Rx2AndroidNetworking.get(INNER_NEWS_Url)
                .addPathParameter(ID_PATH_PARAM, newsID)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(InnerNewsResponse.class);
    }

    public static Observable<RelatedNewsResponse> getRelatedNews(String appLang, String newsID) {
        return Rx2AndroidNetworking.get(RELATED_NEWS_URL)
                .addPathParameter(LANG_PATH_PARAM, appLang)
                .addPathParameter(ID_PATH_PARAM, newsID)
                .setPriority(Priority.HIGH)
                .getResponseOnlyFromNetwork()
                .build()
                .getObjectObservable(RelatedNewsResponse.class);
    }

    public interface AddToFavCallBacks {
        void addToFavSuccess();

        void addToFavFailed(String error);
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
