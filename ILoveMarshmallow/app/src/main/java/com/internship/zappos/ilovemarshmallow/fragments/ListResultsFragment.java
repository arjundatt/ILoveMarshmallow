package com.internship.zappos.ilovemarshmallow.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.internship.zappos.ilovemarshmallow.MainActivity;
import com.internship.zappos.ilovemarshmallow.R;
import com.internship.zappos.ilovemarshmallow.controllers.RecyclerListViewAdapter;
import com.internship.zappos.ilovemarshmallow.model.QueryResultItem;

import java.util.ArrayList;

/* This fragment holds the RecyclerView which shows
* the items in the list */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListResultsFragment.OnRecyclerInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_LIST = "item_list";

    // TODO: Rename and change types of parameters
    private ArrayList<QueryResultItem> mItemsList;

    private View mView;
    private RecyclerView mRecyclerView;
    private RecyclerListViewAdapter mAdapter;

    private OnRecyclerInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @return A new instance of fragment ListResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListResultsFragment newInstance(Bundle list) {
        ListResultsFragment fragment = new ListResultsFragment();
        fragment.setArguments(list);
        return fragment;
    }

    public ListResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemsList = getArguments().getParcelableArrayList(HeadlessFragment.KEY_FETCHED_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list_results, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i(MainActivity.TAG,"onCreateView");
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(MainActivity.TAG, "onActivityCreated setting adapter");
        if(mItemsList!=null){
            mAdapter = RecyclerListViewAdapter.getInstance(getContext(), mItemsList);
            mRecyclerView.setAdapter(mAdapter);
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecyclerInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecyclerInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
