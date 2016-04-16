// Generated code from Butter Knife. Do not modify!
package com.unauto.android.common.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MisSitiosActivity$$ViewBinder<T extends com.unauto.android.common.activities.MisSitiosActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558551, "field 'floatingActionButton'");
    target.floatingActionButton = finder.castView(view, 2131558551, "field 'floatingActionButton'");
  }

  @Override public void unbind(T target) {
    target.floatingActionButton = null;
  }
}
