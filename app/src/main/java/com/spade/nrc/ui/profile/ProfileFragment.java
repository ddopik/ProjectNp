package com.spade.nrc.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.profile.view.EditProfileActivity;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.PrefUtils;
import com.vansuita.gaussianblur.GaussianBlur;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public class ProfileFragment extends BaseFragment {
    private static final int REQUEST_EDIT_PROFILE = 10;
    private View view;
    private TextView userName, userEmail, userPhone;
    private ImageView userImageView, userImageBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews();
        return view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        ViewPager viewPager = view.findViewById(R.id.fragments_viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ImageView backBtn = toolbar.findViewById(R.id.back_button);
        ImageView editProfileBtn = toolbar.findViewById(R.id.edit_profile);

        userImageView = view.findViewById(R.id.profile_image);
        userImageBackground = view.findViewById(R.id.profile_image_background);

//        ImageView editProfile = view.findViewById(R.id.edit_profile);
        userName = view.findViewById(R.id.user_name_text_view);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        editProfileBtn.setOnClickListener(v -> startEditProfileActivity());
        setUserData();
    }

    private void startEditProfileActivity() {
        startActivityForResult(EditProfileActivity.getLaunchIntent(getContext()), REQUEST_EDIT_PROFILE);
    }

    private void setupViewPager(ViewPager viewPager) {
        PagingAdapter adapter = new PagingAdapter(getChildFragmentManager());
        List<Fragment> fragments = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();


        fragments.add(getFavouriteChannelsFragment());
        fragments.add(getFavouriteShowsFragment());
        fragmentTitles.add(getString(R.string.my_channels));
        fragmentTitles.add(getString(R.string.followed_shows));

        adapter.addFragment(fragments, fragmentTitles);

        viewPager.setAdapter(adapter);
    }

    private FavouriteChannelFragment getFavouriteChannelsFragment() {
        return new FavouriteChannelFragment();
    }

    private FavouriteShowFragment getFavouriteShowsFragment() {
        return new FavouriteShowFragment();
    }

    private void setUserData() {
        RealmDbHelper realmDbHelper = new RealmDbImpl();
        User user = realmDbHelper.getUser(PrefUtils.getUserId(getContext()));
        if (user != null) {
            if (user.getFirstName() != null && user.getLastName() != null) {
                userName.setText(user.getFirstName().concat(" ").concat(user.getLastName()));
            } else if (user.getFirstName() != null) {
                userName.setText(user.getFirstName());
            } else if (user.getLastName() != null) {
                userName.setText(user.getLastName());
            } else {
                userName.setVisibility(View.GONE);
            }

            if (user.getUserPhoto() != null) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CircleCrop());
                GlideApp.with(getContext()).load(user.getUserPhoto()).apply(requestOptions)
                        .placeholder(R.drawable.ic_profile_default).apply(requestOptions).into(userImageView);

                GlideApp.with(getContext()).asBitmap().listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap blurredBitmap = GaussianBlur.with(getContext()).render(resource);
                        userImageBackground.setImageBitmap(blurredBitmap);
                        return true;
                    }
                }).load(user.getUserPhoto()).into(userImageBackground);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_EDIT_PROFILE) {
                setUserData();
            }
        }
    }
}
