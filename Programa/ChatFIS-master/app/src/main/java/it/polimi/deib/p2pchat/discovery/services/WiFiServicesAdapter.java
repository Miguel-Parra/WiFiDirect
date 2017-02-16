package it.polimi.deib.p2pchat.discovery.services;

import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.polimi.deib.p2pchat.R;

public class WiFiServicesAdapter extends RecyclerView.Adapter<WiFiServicesAdapter.ViewHolder> {

    private final ItemClickListener itemClickListener;

    public WiFiServicesAdapter(@NonNull ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public interface ItemClickListener {
        void itemClicked(final View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView nameText;
        private final TextView statusText;
        private final TextView macAddressText;

        public ViewHolder(View view) {
            super(view);

            this.parent = view;

            nameText = (TextView) view.findViewById(R.id.message);
            macAddressText = (TextView) view.findViewById(R.id.text2);
            statusText = (TextView) view.findViewById(R.id.text3);
        }


        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.service_row, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        WiFiP2pService service = ServiceList.getInstance().getElementByPosition(position);
        if (service != null) {
            viewHolder.nameText.setText(service.getDevice().deviceName + " - " + service.getInstanceName());
            viewHolder.macAddressText.setText(service.getDevice().deviceAddress);
            viewHolder.statusText.setText(getDeviceStatus(service.getDevice().status));
        }

        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(v);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ServiceList.getInstance().getSize();
    }

    private static String getDeviceStatus(int statusCode) {
        switch (statusCode) {
            case WifiP2pDevice.CONNECTED:
                return "Conectado";
            case WifiP2pDevice.INVITED:
                return "Invitado";
            case WifiP2pDevice.FAILED:
                return "Ha fallado...!!!";
            case WifiP2pDevice.AVAILABLE:
                return "Disponible";
            case WifiP2pDevice.UNAVAILABLE:
                return "Indisponible";
            default:
                return "Desconocido";

        }
    }
}
