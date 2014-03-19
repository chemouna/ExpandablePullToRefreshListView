package mona.android.expandableptrlv;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

/**
 * A fragment using PtrActionSlideListView : inludes a listview that uses pulltorefresh and
 * has menu in form of slidable containers
 */
public abstract class ItemExpandListFragment<E> extends ListFragment
        implements OnRefreshListener2<ListView> {

    private View mRootLayout;
    private PtrActionSlideListView mPtrSlideActionListView;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootLayout = super.onCreateView(inflater, container);
        mPtrSlideActionListView = new PtrActionSlideListView(getActivity());

        mListView = (ListView) mRootLayout.findViewById(android.R.id.list);
        ViewGroup parent = (ViewGroup) mListView.getParent();

        // Remove ListView and add our mPtrSlideActionListView(PulltoRefresh with
        // slide action listview) in its place
        int lvIndex = parent.indexOfChild(mListView);
        parent.removeViewAt(lvIndex);
        parent.addView(mPtrSlideActionListView, lvIndex,
                mListView.getLayoutParams());
        return mRootLayout;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        setMode(Mode.BOTH);
        mPtrSlideActionListView.setOnRefreshListener(this);
        mPtrSlideActionListView.setAdapter(createExpandableListAdapter());
        setListItemMenuAction(state);
    }

    protected void setMode(Mode mode) {
        mPtrSlideActionListView.setMode(mode);
    }

    public ListView getListView() {
        return mListView;
    }

    public PtrActionSlideListView getPtrSlideActionListView() {
        return mPtrSlideActionListView;
    }

    @Override
    protected ListView createListView() {
        mListsView = mPtrSlideActionListView.getRefreshableView();
        return mListView;
    }

    //create a list adapter for mPtrSlideActionListView
    protected abstract SlideExpandableListAdapter createExpandableListAdapter();

    //set the action that should be run when an item expand btn is clicked
    protected abstract void setListItemMenuAction(Bundle state);

}