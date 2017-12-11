package com.example.huynhxuankhanh.minialbum.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.ImageActivity;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterImageGridView;
import com.example.huynhxuankhanh.minialbum.database.Database;
import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentPicture extends Fragment implements com.example.huynhxuankhanh.minialbum.fragment.FragmentCallBacks {
    private View view;
    private AdapterImageGridView myArrayAdapterGridView;
    private GridView gridView;
    private Intent fragPictureIntent;
    private FragmentActivity activity;
    private List<InfoImage> listImage;
    private int currentPos = 0;
    private int lastSelected = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean scrollEnabled;
    public static FragmentPicture newInstance(String StrArg) {
        FragmentPicture fragment = new FragmentPicture();
        Bundle args = new Bundle();
        args.putString("image-bundle", StrArg);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListImage(List<InfoImage> listImage) {
        this.listImage = listImage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picture, container, false);
        gridView = (GridView) view.findViewById(R.id.grd_Image);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send data to activity2: view image full screen
                InfoImage temp = listImage.get(position);
                lastSelected = position;
                fragPictureIntent.putExtra("image-info", (Parcelable) temp);
                // check putExtra is it ok or position is ok ?
                currentPos = gridView.getFirstVisiblePosition();
                startActivityForResult(fragPictureIntent,0);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(scrollEnabled);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ((MainActivity) getActivity()).onMsgFromFragToMain("re-load-images");
                        myArrayAdapterGridView = new AdapterImageGridView(getActivity(), R.layout.imageview_layout, listImage);
                        gridView.setAdapter(myArrayAdapterGridView);
                        gridView.setSelection(currentPos);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (gridView == null || gridView.getChildCount() == 0) ?
                                0 : gridView.getChildAt(0).getTop();

                boolean newScrollEnabled =
                        (firstVisibleItem == 0 && topRowVerticalPosition >= 0) ?
                                true : false;

                if (null != swipeRefreshLayout && scrollEnabled != newScrollEnabled) {
                    // Start refreshing....
                    swipeRefreshLayout.setEnabled(newScrollEnabled);
                    scrollEnabled = newScrollEnabled;
                }

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragPictureIntent = new Intent(getActivity(), ImageActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) { // means reload data: An image is removed from image activity.
            // listImage = new ArrayList<>();
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(activity, "Deleted...", Toast.LENGTH_SHORT).show();
                // remove an image from list image loaded from database.
                listImage.remove(lastSelected);
            }
            if(resultCode==222){
                Intent result = new Intent();
                int num = 0;
                num = data.getIntExtra("crop-image",num);
                if(num!=0)
                    ((MainActivity) getActivity()).onMsgFromFragToMain(Integer.toString(num));
            }
            if(resultCode==223){
                String[] pack = data.getStringArrayExtra("rotate-image");
                // update lai info tam anh ma dc rotate tu view image activity
                for(int i=0;i<listImage.size();++i){
                    if(listImage.get(i).getiD() == Integer.parseInt(pack[0])){
                        listImage.get(i).setOrientaion(pack[1]);
                    }
                }
                // update lai database cua app
                String sql = "UPDATE Favorite SET Orientation = '"+pack[1]+"' WHERE Id = "+Integer.parseInt(pack[0]);
                Database database = new Database(getActivity());
                database.QuerySQL(sql);

                

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // reload data
        if (listImage == null)
            ((MainActivity) getActivity()).onMsgFromFragToMain("load-images");
        myArrayAdapterGridView = new AdapterImageGridView(getActivity(), R.layout.imageview_layout, listImage);
        gridView.setAdapter(myArrayAdapterGridView);
        gridView.setSelection(currentPos);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onMsgFromMainToFragmentImage(List<InfoImage> listImage) {
        if (listImage != null) {
            this.listImage = listImage;
        }
    }

    @Override
    public void onMsgFromMainToFragmentFolder(List<InfoFolder> listFolder) {
    }
}
