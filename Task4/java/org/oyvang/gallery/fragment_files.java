package org.oyvang.gallery;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by GeirMorten on 24.09.2014.
 */
public class fragment_files extends ListFragment {
    public String selectedImage;
    public ArrayList<String> Images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createGalleryImages();
        return inflater.inflate(R.layout.fragment_files, container, true);
    }

    private void createGalleryImages() {
        ArrayList<String> imageNames = imageNames();
        Images = imageNames;
        this.setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, imageNames));
    }


    private ArrayList<String> imageNames() {
        ArrayList imageNames = new ArrayList(9);
        imageNames.add(getResources().getString(R.string.luffy_name));
        imageNames.add(getResources().getString(R.string.nami_name));
        imageNames.add(getResources().getString(R.string.zoro_name));
        imageNames.add(getResources().getString(R.string.brook_name));
        imageNames.add(getResources().getString(R.string.sanji_name));
        imageNames.add(getResources().getString(R.string.robin_name));
        imageNames.add(getResources().getString(R.string.chopper_name));
        imageNames.add(getResources().getString(R.string.franky_name));
        imageNames.add(getResources().getString(R.string.usopp_name));
        return imageNames;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        changeFragments(pos);
        setSelectedImage(Images.get(pos));
        super.onListItemClick(l, v, pos, id);
    }

    private void changeFragments(int pos) {
        Activity a = getActivity();
        TextView twiv = (TextView) a.findViewById(R.id.fragment_gallery_view_xml);
        TextView tw = (TextView) a.findViewById(R.id.fragment_text_view_xml);
        twiv.setBackground(getResources().getDrawable(findImageId(pos)));
        twiv.setText("");
        tw.setTextColor(Color.BLACK);
        tw.setText(findTextId(pos));
    }

    public void changeImage(String name) {
        setSelectedImage(name);
        changeFragments(imageNames().indexOf(name));
    }

    public String nextImage(String name) {
        if (name == null) {  //If user click on Next picture without an image have been selected it will view the first image
            return imageNames().get(0);
        }
        for (int i = 0; i < imageNames().size(); i++) {
            if (name.equals(imageNames().get(i))) {
                if (i + 1 < imageNames().size()) {
                    return imageNames().get(i + 1);
                }
            }
        }
        return imageNames().get(0);
    }


    public String prevImage(String name) {
        if (name == null) {  //If user click on Next picture without an image have been selected it will view the last image
            return imageNames().get(imageNames().size() - 1);
        }
        for (int i = 0; i < imageNames().size(); i++) {
            if (name.equals(imageNames().get(i))) {
                if (i - 1 > -1) {
                    return imageNames().get(i - 1);
                }
            }
        }
        return imageNames().get(imageNames().size() - 1);
    }

    private void setSelectedImage(String name) {
        if (imageNames().contains(name)) {
            selectedImage = name;
        } else selectedImage = imageNames().get(0);
    }


    private int findImageId(int p) {
        switch (p) {
            case 0:
                return R.drawable.luffy;
            case 1:
                return R.drawable.nami;
            case 2:
                return R.drawable.zoro;
            case 3:
                return R.drawable.brook;
            case 4:
                return R.drawable.sanji;
            case 5:
                return R.drawable.robin;
            case 6:
                return R.drawable.chopper;
            case 7:
                return R.drawable.franky;
            case 8:
                return R.drawable.usopp;
            default:
                return R.drawable.luffy;
        }
    }

    private int findTextId(int p) {
        switch (p) {
            case 0:
                return R.string.luffy_text;
            case 1:
                return R.string.nami_text;
            case 2:
                return R.string.zoro_text;
            case 3:
                return R.string.brook_text;
            case 4:
                return R.string.sanji_text;
            case 5:
                return R.string.robin_text;
            case 6:
                return R.string.chopper_text;
            case 7:
                return R.string.franky_text;
            case 8:
                return R.string.usopp_text;
            default:
                return R.string.luffy_text;
        }
    }


}
