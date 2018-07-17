package air.com.ramshero.QRscanUI.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import air.com.ramshero.QRscanUI.R;
import air.com.ramshero.QRscanUI.activity.ScanQrActivity;
import air.com.ramshero.QRscanUI.manager.Common;
import air.com.ramshero.QRscanUI.manager.IQRCodeAPI;
import air.com.ramshero.QRscanUI.model.login.User;
import air.com.ramshero.QRscanUI.model.menu.MenuModel;
import air.com.ramshero.QRscanUI.model.menu.MenuResultModel;
import air.com.ramshero.QRscanUI.view.IClickFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements View.OnClickListener {

    private ImageView btnScanQr;
    private Button btnHome;
    private Button btnQrList;

    private User user;
    private ArrayList<MenuModel> menuList = new ArrayList<>();
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
        }catch (ClassCastException e){
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
        initData();
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
                        if (response.isSuccessful()) {
                            MenuResultModel menuResultModel = response.body();
                            menuList.clear();
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
        btnHome = rootView.findViewById(R.id.btnHome);
        btnQrList = rootView.findViewById(R.id.btnQrList);

        btnScanQr.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnQrList.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnScanQr:
                Intent intent = new Intent(getActivity(), ScanQrActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
                break;
            case R.id.btnHome:
                clickked = 1;
                mCallback.onClickFragment(clickked);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, WebFragment.newInstance(getUrlMenu(menuList, "home")))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnQrList:
                clickked = 1;
                mCallback.onClickFragment(clickked);
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, WebFragment.newInstance(getUrlMenu(menuList, "qr list")))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    private String getUrlMenu(ArrayList<MenuModel> menuList, String validate) {
        String url = null;
        for (MenuModel menuModel : menuList) {
            if (menuModel.getName().equals(validate)) {
                url = menuModel.getUrl();
            }
        }
        return url;
    }

}
