package com.mohsinmonad.newsviews.util;



public class Utils {

    //@SuppressWarnings("ConstantConditions")
    //private void searAction() {

        /*search_News.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchProgressBar.setVisibility(View.VISIBLE);
                if (criteria.equalsIgnoreCase("SPECIFIC_NEWS")) {
                    String source_id = getIntent().getExtras().getString("SOURCE_ID");
                    NewsActivityViewPresenter.getSearchArticle(source_id, search_News.getText().toString());
                }
                handled = true;
            }

            return handled;

        });

        search_News.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void afterTextChanged(Editable s) {

                searchProgressBar.setVisibility(View.VISIBLE);
                String inputs = search_News.getText().toString();
                Log.d("After", "afterTextChanged: " + inputs);

                if (inputs.length() == 0) {
                    initSwipeToRefresh();
                    return;
                }

                if (criteria.equalsIgnoreCase("SPECIFIC_NEWS")) {
                    String source_id = getIntent().getExtras().getString("SOURCE_ID");
                    NewsActivityViewPresenter.getSearchArticle(source_id, search_News.getText().toString());
                }
            }
        });
*/
   // }

}
