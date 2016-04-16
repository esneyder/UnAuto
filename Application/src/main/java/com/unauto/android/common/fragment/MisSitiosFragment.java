package com.unauto.android.common.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.unauto.android.common.activities.DetallesActivity;
import com.unauto.android.common.clases.MisSitios;
import com.unauto.android.common.logger.Log;
import com.unauto.android.common.util.DialogFactory;
import com.unauto.dev.services.UnAuto.R;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisSitiosFragment extends Fragment {
private ListView mSitios;
private List<MisSitios> mLIstaSitios;
AdaptadorSitios adaptadorMisSitios;
ParseUser obtenerUsuario=ParseUser.getCurrentUser();

public MisSitiosFragment() {
    // Required empty public constructor
}


@Override
public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View viewFragmento = inflater.inflate(R.layout.fragment_mis_sitios, container, false);

    mSitios = (ListView) viewFragmento.findViewById(R.id.list_mis_sitios);
    mLIstaSitios = new ArrayList<>();
    adaptadorMisSitios=new AdaptadorSitios();
    this.mSitios.setItemsCanFocus(false);
    this.mSitios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MisSitios sitios=mLIstaSitios.get(position);
            Intent intent=new Intent(getContext(), DetallesActivity.class);
            intent.putExtra("nombreSitio",sitios.getNombre());
            intent.putExtra("objectId",sitios.getIdSitio());

            startActivity(intent);


        }
    });

    this.mSitios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
            MisSitios sitios=mLIstaSitios.get(pos);
            // TODO ir a la clase DialogFactory
            DialogFactory.OptionPlaces(getContext(),sitios.getIdSitio());
            adaptadorMisSitios.notifyDataSetChanged();
            return true;
        }
    });

    //capturar el usuario en session
    String userName=obtenerUsuario.getUsername();// usuario capturado
    ParseQuery<ParseObject> query = ParseQuery.getQuery("MisSitios");
    query.whereEqualTo("username", userName);
    query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> favoritoList, ParseException e) {
            if (e == null) {
                Log.d("Mis sitios", "Sitios " + favoritoList.size() + " Sitios");

                for (ParseObject object:favoritoList)
                {
                    String idSitio=(String)object.getObjectId();
                    String usename=(String)object.get("username");
                    String nombre=(String) object.get("Nombre");
                    String direccion=(String) object.get("Direccion");
                    String Lat=(String) object.get("Latitude");
                    String Lng=(String) object.get("Longitude");
                    MisSitios misSitios=new MisSitios(idSitio,usename,nombre,direccion,Lat,Lng);

                    mLIstaSitios.add(misSitios);
                }
                mSitios.setAdapter(adaptadorMisSitios);
                adaptadorMisSitios.notifyDataSetChanged();
            } else {
                Log.d("Mis sitios", "Sitios: " + e.getMessage());
            }
        }
    });

    return viewFragmento;
}

private class AdaptadorSitios extends BaseAdapter {

    @Override
    public int getCount() {
        return mLIstaSitios.size();
    }

    @Override
    public Object getItem(int position) {
        return mLIstaSitios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View filaView=getActivity().getLayoutInflater().inflate(R.layout.item_mis_sitios,null);
        MisSitios misSitios=mLIstaSitios.get(position);
        TextView txtNombreMiSitio=(TextView) filaView.findViewById(R.id.txtNombreSitio);
        txtNombreMiSitio.setText(misSitios.getNombre());
        return filaView;
    }

}

}
