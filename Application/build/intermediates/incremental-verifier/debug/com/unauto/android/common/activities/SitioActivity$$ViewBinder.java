// Generated code from Butter Knife. Do not modify!
package com.unauto.android.common.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SitioActivity$$ViewBinder<T extends com.unauto.android.common.activities.SitioActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558555, "field 'mPlaceDetailsText'");
    target.mPlaceDetailsText = finder.castView(view, 2131558555, "field 'mPlaceDetailsText'");
    view = finder.findRequiredView(source, 2131558556, "field 'mPlaceAttribution'");
    target.mPlaceAttribution = finder.castView(view, 2131558556, "field 'mPlaceAttribution'");
    view = finder.findRequiredView(source, 2131558557, "field 'place_Lng'");
    target.place_Lng = finder.castView(view, 2131558557, "field 'place_Lng'");
    view = finder.findRequiredView(source, 2131558554, "field 'LLDatosSeleccionados'");
    target.LLDatosSeleccionados = finder.castView(view, 2131558554, "field 'LLDatosSeleccionados'");
  }

  @Override public void unbind(T target) {
    target.mPlaceDetailsText = null;
    target.mPlaceAttribution = null;
    target.place_Lng = null;
    target.LLDatosSeleccionados = null;
  }
}
