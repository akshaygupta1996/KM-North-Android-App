package connect.shopping.akshay.kmnorth.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import connect.shopping.akshay.kmnorth.MenuAdaptor;
import connect.shopping.akshay.kmnorth.R;
import connect.shopping.akshay.kmnorth.bean.response.ArrayInsideMainResponse;

/**
 * Created by CHAITNYA on 7/1/2017.
 */

public class FragmentA extends Fragment {


    private List<ArrayInsideMainResponse> submenu;
    private static final int SPAN_SIZE = 2;
    private int SECTIONS;
    private ArrayList<Integer> counts = new ArrayList<>();
    private RecyclerView mRecycler;
    private StickyHeaderGridLayoutManager mLayoutManager;

    public FragmentA(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            submenu = (List<ArrayInsideMainResponse>) getArguments().getSerializable("submenu");

            SECTIONS = submenu.size();
            for(int i=1;i<=SECTIONS;i++){
                counts.add(submenu.get(i-1).getItems().size());
            }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a,container,false);
        mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(getResources().getDimensionPixelSize(R.dimen.header_shadow_size));

        mRecycler.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });

        mRecycler.setLayoutManager(mLayoutManager);

        showData();




        return  view;
    }

    private void showData() {
        mRecycler.setAdapter(new MenuAdaptor(getActivity().getApplicationContext(),SECTIONS, counts, submenu));

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("RESUME", "On Resume Called");
        showData();
    }
}
