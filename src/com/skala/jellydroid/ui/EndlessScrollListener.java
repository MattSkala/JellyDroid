package com.skala.jellydroid.ui;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	/**
	 * The minimum amount of items to have below current scroll position, before loading more.
	 */
	private int mVisibleThreshold = 1;

	/**
	 * The current page of data we have loaded.
	 */
	private int mCurrentPage = -1;

	/**
	 * The total number of items in the dataset after the last load.
	 */
	private int mPreviousTotal = 0;

	/**
	 * True if we are still waiting for the last set of data to load.
	 */
	private boolean mLoading = true;

	public void setVisibleThreshold(int visibleThreshold) {
		mVisibleThreshold = visibleThreshold;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mLoading) {
			if (totalItemCount > mPreviousTotal) {
				mLoading = false;
				mPreviousTotal = totalItemCount;
				mCurrentPage++;
			}
		} else if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
			// Load the next page of items using a background task
			loadMoreItems(mCurrentPage + 1);
			mLoading = true;
		}
	}

	/**
	 * The method is called when the next page needs to be loaded.
	 * @param page The page that should be loaded. Initially loaded page is 0.
	 */
	public abstract void loadMoreItems(int page);

	/**
	 * Called when all items are fetched. It can be used to remove loading view from footer.
	 */
	public void onComplete() {
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// We do not need to implement
	}
}
