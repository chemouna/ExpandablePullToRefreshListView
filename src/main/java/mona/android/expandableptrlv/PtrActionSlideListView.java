package mona.android.expandableptrlv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView.OnActionClickListener;
import com.tjerkw.slideexpandable.library.WrapperListAdapterImpl;

/**
 * Listview with Pull to refresh and slide action on its items
 *
 *
 */
public class PtrActionSlideListView extends PullToRefreshListView {

	private ActionSlideExpandableListView mActionSlideListView;
	
	public PtrActionSlideListView(Context context) {
		super(context);
		mActionSlideListView = new ActionSlideExpandableListView(context);
	}

	public void setItemActionListener(OnActionClickListener listener, int ... buttonIds) {
		mActionSlideListView.setListener(listener);
		mActionSlideListView.setButtonIds(buttonIds);
	}

	/**
	 * Interface for callback to be invoked whenever an action is clicked in
	 * the expandle area of the list item.
	 */
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(new WrapperListAdapterImpl(adapter) {
			@Override
			public View getView(final int position, View view, ViewGroup viewGroup) {
				final View listView = wrapped.getView(position, view, viewGroup);
				// add the action listeners
				int[] button_ids = mActionSlideListView.getButtonIds();
				if(button_ids != null && listView!=null) {
					for(int id : button_ids) {
						View buttonView = listView.findViewById(id);
						if(buttonView!=null) {
							buttonView.findViewById(id).setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									if(mActionSlideListView.getListener()!=null) {
										mActionSlideListView.getListener().onClick(listView, view, position);
									}
								}
							});
						}
					}
				}
				return listView;
			}
		});
	}
	
	
	public ActionSlideExpandableListView getActionSlideListView() {
		return mActionSlideListView;
	}

	public void setActionSlideListView(
			ActionSlideExpandableListView mActionSlideListView) {

        this.mActionSlideListView = mActionSlideListView;
	}
	
}