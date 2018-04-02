package com.spade.nrc.ui.contact_us.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.utils.Utils;


/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class ContactUsFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {

    private View contactUsView;
    private ProgressBar progressBar;
    private String facebookUrl, instagramUrl, twitterUrl, linkedInUrl, mobileNumber, email;
    private NestedScrollView mainScroll;
    private SupportMapFragment mapFragment;
    private NavigationManager.OnMenuOpenClicked onMenuOpenClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactUsView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initViews();
        return contactUsView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void initPresenter() {
        sendAnalytics();
    }

    private void sendAnalytics() {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(getContext().getString(R.string.contact_us_analytics));
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void initViews() {
        Button sendMessage = contactUsView.findViewById(R.id.message_us_btn);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        ImageView transparentImage = contactUsView.findViewById(R.id.transparent_image);
        ImageView menuImageView = contactUsView.findViewById(R.id.menu_image_view);
        RelativeLayout callLayout = contactUsView.findViewById(R.id.call_layout);
        RelativeLayout emailAddressLayout = contactUsView.findViewById(R.id.send_mail_layout);
        RelativeLayout sendMessageLayout = contactUsView.findViewById(R.id.send_sms_layout);
        RelativeLayout websiteLayout = contactUsView.findViewById(R.id.website_layout);

        ImageView facebook = contactUsView.findViewById(R.id.facebook_image);
        ImageView instagram = contactUsView.findViewById(R.id.instagram_image);
        ImageView twitter = contactUsView.findViewById(R.id.twitter_image);
        ImageView linkedIn = contactUsView.findViewById(R.id.linkedin_image);
        mainScroll = contactUsView.findViewById(R.id.main_scroll);
        progressBar = contactUsView.findViewById(R.id.progress_bar);


        menuImageView.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        sendMessageLayout.setOnClickListener(this);
        callLayout.setOnClickListener(this);
        emailAddressLayout.setOnClickListener(this);
        websiteLayout.setOnClickListener(this);
        facebook.setOnClickListener(this);
        instagram.setOnClickListener(this);
        twitter.setOnClickListener(this);
        linkedIn.setOnClickListener(this);

        setContactData();

        transparentImage.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    return false;

                case MotionEvent.ACTION_UP:
                    mainScroll.requestDisallowInterceptTouchEvent(false);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    mainScroll.requestDisallowInterceptTouchEvent(true);
                    return false;
                default:
                    return true;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.map_style));
        LatLng latLng = new LatLng(29.9657458, 31.0140652);
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_address)).title(getString(R.string.app_name)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }


    public void setContactData() {
        mobileNumber = getString(R.string.nrc_mobile);
        email = getString(R.string.nrc_email);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_layout:
                Utils.dial(mobileNumber, getContext());
                break;
            case R.id.send_mail_layout:
                Utils.openMail(email, getContext());
                break;
            case R.id.send_sms_layout:
                Utils.sendSms(mobileNumber, getContext());
                break;
            case R.id.facebook_image:
                break;
            case R.id.instagram_image:
                break;
            case R.id.twitter_image:
                break;
            case R.id.linkedin_image:
                break;
            case R.id.menu_image_view:
                onMenuOpenClicked.onMenuImageClicked();
                break;
        }
    }

    public void setOnMenuOpenClicked(NavigationManager.OnMenuOpenClicked onMenuOpenClicked) {
        this.onMenuOpenClicked = onMenuOpenClicked;
    }
}
