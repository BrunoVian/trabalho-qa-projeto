package br.com.securityprofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.securityprofit.R;


import java.util.List;

import br.com.securityprofit.api.models.escolta.PessoaEscoltaDTO;

public class PessoaEscoltaAdapter extends ArrayAdapter<PessoaEscoltaDTO> {
    public PessoaEscoltaAdapter(Context context, List<PessoaEscoltaDTO> listaPessoas) {

        super(context, R.layout.item_sugestao_pessoa, listaPessoas);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sugestao_pessoa, parent, false);

        TextView textViewNome = convertView.findViewById(R.id.tvNomePessoa);
        TextView textViewCpf = convertView.findViewById(R.id.tvCpfPessoa);

        PessoaEscoltaDTO pessoa = getItem(position);

        if (pessoa != null) {
            textViewNome.setText(pessoa.getNome());
            textViewCpf.setText(pessoa.getCpf());
        }

        return convertView;
    }
}
