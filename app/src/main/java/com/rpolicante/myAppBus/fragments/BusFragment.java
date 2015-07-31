package com.rpolicante.myAppBus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rpolicante.myAppBus.domain.Bus;
import com.rpolicante.myAppBus.MainActivity;
import com.rpolicante.myAppBus.R;
import com.rpolicante.myAppBus.adapters.BusAdapter;
import com.rpolicante.myAppBus.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

/**
 * Created by policante on 7/29/15.
 */
public class BusFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    private RecyclerView mRecyclerView;
    private List<Bus> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fragment_bus);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                BusAdapter adapter = (BusAdapter) mRecyclerView.getAdapter();

                if (mList.size() == llm.findLastCompletelyVisibleItemPosition() + 1){
                    List<Bus> mListAux = ((MainActivity) getActivity()).getSetBusList(10);

                    for (Bus b : mListAux){
                        adapter.addListItem(b, mList.size());
                    }
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //SetAdapter
        mList = ((MainActivity) getActivity()).getSetBusList(20);
        BusAdapter adapter = new BusAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Position: "+position, Toast.LENGTH_SHORT).show();

        BusAdapter adapter = (BusAdapter) mRecyclerView.getAdapter();
        adapter.removeListItem(position);
    }
}
