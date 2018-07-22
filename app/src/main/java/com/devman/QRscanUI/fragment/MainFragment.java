package com.devman.QRscanUI.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.devman.QRscanUI.R;
import com.devman.QRscanUI.activity.ScanQrActivity;
import com.devman.QRscanUI.adapter.ButtomAdapter;
import com.devman.QRscanUI.manager.Common;
import com.devman.QRscanUI.manager.IQRCodeAPI;
import com.devman.QRscanUI.model.login.User;
import com.devman.QRscanUI.model.menu.MenuModel;
import com.devman.QRscanUI.model.menu.MenuResultModel;
import com.devman.QRscanUI.view.IClickFragment;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements View.OnClickListener {

    private ImageView btnScanQr;
    private ArrayList<MenuModel> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ButtomAdapter adapter;

    private User user;
    private IQRCodeAPI mService;

    private IClickFragment mCallback;
    private int clickked = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(User user) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IClickFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement IClickFragment");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        user = Parcels.unwrap(getArguments().getParcelable("user"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstance(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        clickked = 0;
    }

    private void initData() {

        mService = Common.getAPI();

        mService.getMenuResult(user.getUId())
                .enqueue(new Callback<MenuResultModel>() {
                    @Override
                    public void onResponse(Call<MenuResultModel> call, Response<MenuResultModel> response) {
                        menuList.clear();
                        if (response.isSuccessful()) {
                            MenuResultModel menuResultModel = response.body();
                            for (MenuModel menuModel : menuResultModel.getMenu()) {
                                menuList.add(menuModel);
                            }
                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MenuResultModel> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initInstance(View rootView) {

        btnScanQr = rootView.findViewById(R.id.btnScanQr);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mService = Common.getAPI();

        mService.getMenuResult(user.getUId())
                .enqueue(new Callback<MenuResultModel>() {
                    @Override
                    public void onResponse(Call<MenuResultModel> call, Response<MenuResultModel> response) {
                        menuList.clear();
                        if (response.isSuccessful()) {
                            MenuResultModel menuResultModel = response.body();
                            for (MenuModel menuModel : menuResultModel.getMenu()) {
                                menuList.add(menuModel);
                            }
                            adapter = new ButtomAdapter(menuList, getActivity());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            adapter.setOnItemClick(new ButtomAdapter.IOnClick() {
                                @Override
                                public void onClick(int position) {
                                    clickked = 1;
                                    mCallback.onClickFragment(clickked);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.contentContainer, WebFragment.newInstance(menuList.get(position).getUrl()))
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MenuResultModel> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        btnScanQr.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnScanQr:
                Intent intent = new Intent(getActivity(), ScanQrActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
                break;
        }
    }

}
