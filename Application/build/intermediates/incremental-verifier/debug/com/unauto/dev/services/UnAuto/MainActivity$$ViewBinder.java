// Generated code from Butter Knife. Do not modify!
package com.unauto.dev.services.UnAuto;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.unauto.dev.services.UnAuto.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558547, "field 'txtusuario'");
    target.txtusuario = finder.castView(view, 2131558547, "field 'txtusuario'");
    view = finder.findRequiredView(source, 2131558549, "field 'txtContrasena'");
    target.txtContrasena = finder.castView(view, 2131558549, "field 'txtContrasena'");
    view = finder.findRequiredView(source, 2131558545, "field 'btnIngresar' and method 'onClick'");
    target.btnIngresar = finder.castView(view, 2131558545, "field 'btnIngresar'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.txtusuario = null;
    target.txtContrasena = null;
    target.btnIngresar = null;
  }
}
