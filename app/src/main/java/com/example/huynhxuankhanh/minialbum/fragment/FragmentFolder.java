package com.example.huynhxuankhanh.minialbum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.huynhxuankhanh.minialbum.R;
import com.example.huynhxuankhanh.minialbum.activity.MainActivity;
import com.example.huynhxuankhanh.minialbum.adapter.AdapterFolderListView;
import com.example.huynhxuankhanh.minialbum.gallery.InfoFolder;
import com.example.huynhxuankhanh.minialbum.gallery.InfoImage;

import java.util.List;

/**
 * Created by HUYNHXUANKHANH on 11/2/2017.
 */

public class FragmentFolder extends Fragment implements com.example.huynhxuankhanh.minialbum.fragment.FragmentCallBacks {
    private View view;
    private List<InfoFolder> listInfoFolder;
    private ListView listView;
    private FragmentActivity activity;
    private AdapterFolderListView myArrayAdapterGridView;

    public static FragmentFolder newInstance(String StrArg) {
        FragmentFolder fragment = new FragmentFolder();
        Bundle args = new Bundle();
        args.putString("image-bundle", StrArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_folder, container, false);

        listView = (ListView) view.findViewById(R.id.lst_folder);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // value i stands for position of each item.
                FragmentPicture f = new FragmentPicture();
                f.setListImage(listInfoFolder.get(i).getListImage());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_folder, f);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).onMsgFromFragToMain("load-folders");

        if (listInfoFolder != null) {
            myArrayAdapterGridView = new AdapterFolderListView(getActivity(), R.layout.folderview_layout, listInfoFolder);
            listView.setAdapter(myArrayAdapterGridView);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onMsgFromMainToFragmentImage(List<InfoImage> listImage) {

    }

    @Override
    public void onMsgFromMainToFragmentFolder(List<InfoFolder> listFolder) {
        this.listInfoFolder = listFolder;
    }
}
