package br.com.securityprofit.ui.pessoa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.securityprofit.ButtonMenuActivity;
import br.com.securityprofit.api.models.pessoa.EnderecoDTO;
import br.com.securityprofit.api.models.pessoa.PessoaDTO;
import br.com.securityprofit.page.PessoaPageAdapter;
import br.com.securityprofit.api.Api.Retrofit;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.models.pessoa.Endereco;
import br.com.securityprofit.api.models.pessoa.FisicaJuridicaEnum;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.api.models.pessoa.TipoPessoaEnum;
import br.com.securityprofit.databinding.FragmentCadPessoaBinding;
import br.com.securityprofit.databinding.FragmentMenuEnderecoBinding;
import br.com.securityprofit.databinding.FragmentMenuObservacaoBinding;
import br.com.securityprofit.viewmodel.PessoaViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadPessoaFragment extends Fragment {
    private FragmentCadPessoaBinding binding;
    private FisicaJuridicaEnum pessoFisicaJuridica;
    private PessoaViewModel viewModel;
    private int limiteCaracteres;



    private TipoPessoaEnum valorSelecionado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(PessoaViewModel.class);


        binding = FragmentCadPessoaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        EditText edtCpfPessoa = view.findViewById(R.id.edtCpfPessoa);
        edtCpfPessoa.addTextChangedListener(insertMascaraCpfCnpj(edtCpfPessoa));

        EditText edtTelefone = view.findViewById(R.id.edtTelefonePessoa);
        EditText edtCelular = view.findViewById(R.id.edtCelularPessoa);

        edtTelefone.addTextChangedListener(insertMascaraCelular(edtTelefone));
        edtCelular.addTextChangedListener(insertMascaraCelular(edtCelular));

        binding.ckClientePessoa.setChecked(true);






        int[][] states = new int[][]{
                    new int[]{android.R.attr.state_checked},
                    new int[]{-android.R.attr.state_checked}
            };

            int[] colors = new int[]{
                    ContextCompat.getColor(requireContext(), R.color.toolbar_padrao),
                    ContextCompat.getColor(requireContext(), R.color.toolbar_padrao)
            };


            ColorStateList colorStateList = new ColorStateList(states, colors);
            pessoFisicaJuridica = FisicaJuridicaEnum.F;
            viewModel.setFisicaJuridica(pessoFisicaJuridica);

            ViewCompat.setBackgroundTintList(binding.btnFisica, colorStateList);
            ViewCompat.setBackgroundTintList(binding.btnJuridica, null);





        binding.btnFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[][] states = new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}
                };

                int[] colors = new int[]{
                        ContextCompat.getColor(requireContext(), R.color.toolbar_padrao),
                        ContextCompat.getColor(requireContext(), R.color.toolbar_padrao)
                };

                ColorStateList colorStateList = new ColorStateList(states, colors);

                pessoFisicaJuridica = FisicaJuridicaEnum.F;
                viewModel.setFisicaJuridica(pessoFisicaJuridica);

                ViewCompat.setBackgroundTintList(binding.btnFisica, colorStateList);
                ViewCompat.setBackgroundTintList(binding.btnJuridica, null);

                binding.tvApelido.setText("Apelido:");
                binding.tvCpf.setText("CPF:");
                binding.edtApelidoPessoa.setHint("Informe o apelido...");
                binding.edtCpfPessoa.setHint("Informe o cpf...");

                binding.edtCpfPessoa.setText("");

            }
        });

        binding.btnJuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[][] states = new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}
                };

                int[] colors = new int[]{
                        ContextCompat.getColor(requireContext(), R.color.toolbar_padrao),
                        ContextCompat.getColor(requireContext(), R.color.toolbar_padrao)
                };

                ColorStateList colorStateList = new ColorStateList(states, colors);
                pessoFisicaJuridica = FisicaJuridicaEnum.J;
                viewModel.setFisicaJuridica(pessoFisicaJuridica);

                ViewCompat.setBackgroundTintList(binding.btnJuridica, colorStateList);
                ViewCompat.setBackgroundTintList(binding.btnFisica, null);

                binding.tvApelido.setText("Fantasia:");
                binding.tvCpf.setText("CNPJ:");
                binding.edtApelidoPessoa.setHint("Informe a fantasia...");
                binding.edtCpfPessoa.setHint("Informe o cnpj...");
                binding.edtCpfPessoa.setText("");


            }
        });



        String nome = String.valueOf(viewModel.getNomeRazao().getValue());

        Log.e("","CadPessoa "+nome);

        binding.edtNomePessoa.setText(nome);




        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey("id")) {
            Long id = arguments.getLong("id");


           SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ApiPessoa apiPessoa = Retrofit.GET_PESSOA();

            Call<Pessoa> call = apiPessoa.GET_PESSOA("Bearer " + token, 16L);
            call.enqueue(new Callback<Pessoa>() {
                @Override
                public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                    if (response.isSuccessful()) {
                        Pessoa pessoa = response.body();

                        viewModel.setNomeRazao(pessoa.getNomeRazao().toString());
                        viewModel.setApelidoFantasia(pessoa.getApelidoFantasia().toString());
                        viewModel.setCpfCnpj(pessoa.getCpfCnpj());
                        viewModel.setEmail(pessoa.getEmail());
                        viewModel.setTelefone(pessoa.getTelefone());
                        viewModel.setCelular(pessoa.getCelular());
                        viewModel.setTipo(pessoa.getTipo());



                    } else {

                        Toast.makeText(getContext(), "Pessoa não encontrada!", Toast.LENGTH_SHORT).show();

                    }


                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();

                }


                @Override
                public void onFailure(Call<Pessoa> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();

                }
            });
        }






        if (binding.ckClientePessoa.isChecked() && binding.ckFuncionarioPessoa.isChecked()) {

            valorSelecionado = TipoPessoaEnum.A;

        } else if (binding.ckFuncionarioPessoa.isChecked()) {

            valorSelecionado = TipoPessoaEnum.F;

        } else if (binding.ckClientePessoa.isChecked()) {

            valorSelecionado = TipoPessoaEnum.C;

        } else {

        }



        binding.btnSalvarPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.ckClientePessoa.isChecked() && binding.ckFuncionarioPessoa.isChecked()) {

                    valorSelecionado = TipoPessoaEnum.A;

                } else if (binding.ckFuncionarioPessoa.isChecked()) {

                    valorSelecionado = TipoPessoaEnum.F;

                } else if (binding.ckClientePessoa.isChecked()) {

                    valorSelecionado = TipoPessoaEnum.C;

                } else {

                }

                PessoaViewModel viewModel = new ViewModelProvider(getActivity()).get(PessoaViewModel.class);

                FisicaJuridicaEnum fisicaJuridica = viewModel.getFisicaJuridica().getValue();
                String nomeRazao = viewModel.getNomeRazao().getValue();
                String apelidoFantasia = viewModel.getApelidoFantasia().getValue();
                String cpfCnpjMascara = viewModel.getCpfCnpj().getValue();
                String email = viewModel.getEmail().getValue();
                String telefoneMascara = viewModel.getTelefone().getValue();
                String celulaMascara = viewModel.getCelular().getValue();
                TipoPessoaEnum tipo = viewModel.getTipo().getValue();
                Boolean status = viewModel.getStatus().getValue();
                String observacao = viewModel.getObservacao().getValue();


                String cep = viewModel.getCep().getValue();
                Integer numero = viewModel.getNumero().getValue();
                String rua = viewModel.getRua().getValue();
                String bairro = viewModel.getBairro().getValue();
                Integer cidade = viewModel.getCidade().getValue();
                String complemento = viewModel.getComplemento().getValue();



               String celular = celulaMascara.replaceAll("[^0-9]", "");
               String telefone = telefoneMascara.replaceAll("[^0-9]", "");
               String cpfCnpj = cpfCnpjMascara.replaceAll("[^0-9]", "");

                PessoaDTO pessoaDTO = new PessoaDTO();

                pessoaDTO.setFisicaJuridica(fisicaJuridica);
                pessoaDTO.setNomeRazao(nomeRazao);
                pessoaDTO.setApelidoFantasia(apelidoFantasia);
                pessoaDTO.setCpfCnpj(cpfCnpj);
                pessoaDTO.setEmail(email);
                pessoaDTO.setTelefone(telefone);
                pessoaDTO.setCelular(celular);
                pessoaDTO.setTipo(tipo);
                pessoaDTO.setStatus(status);
                pessoaDTO.setObservacao(observacao);


                EnderecoDTO enderecoDTO = new EnderecoDTO();
                enderecoDTO.setCep(cep);
                enderecoDTO.setRua(rua);
                enderecoDTO.setNumero(numero);
                enderecoDTO.setBairro(bairro);
                enderecoDTO.setComplemento(complemento);
                enderecoDTO.setCidadeIBGE(String.valueOf(cidade));

                pessoaDTO.setEndereco(enderecoDTO);

                Log.e("","nomeRazao ="+nomeRazao);
                Log.e("","apelidoFantasia ="+ apelidoFantasia);
                Log.e("","cpfCnpj ="+ cpfCnpjMascara);
                Log.e("","email ="+ email);
                Log.e("","celular ="+  celulaMascara);
                Log.e("","telefone ="+ telefoneMascara);
                Log.e("","tipo ="+ tipo);
                Log.e("","status ="+ status);
                Log.e("","observacao ="+ observacao);
                Log.e("","cep ="+ cep);
                Log.e("","bairro ="+ bairro);
                Log.e("","rua ="+ rua);
                Log.e("","numero ="+ numero);
                Log.e("","cidade ="+ cidade);
                Log.e("","complemento ="+ complemento);
                Log.e("",""+pessoaDTO.getNomeRazao());
                Log.e("",""+pessoaDTO.getApelidoFantasia());
                Log.e("",""+pessoaDTO.getCelular());
                Log.e("",""+pessoaDTO.getTelefone());
                Log.e("",""+pessoaDTO.getEmail());
                Log.e("",""+pessoaDTO.getTipo());
                Log.e("",""+pessoaDTO.getStatus());
                Log.e("",""+pessoaDTO.getObservacao());
                Log.e("",""+pessoaDTO.getCpfCnpj());

                Log.e("","1"+enderecoDTO.getCep());
                Log.e("","2"+enderecoDTO.getBairro());
                Log.e("","3"+enderecoDTO.getComplemento());
                Log.e("","4"+enderecoDTO.getRua());
                Log.e("","5"+enderecoDTO.getCidadeIBGE());
               // pessoaDTO.setEndereco(enderecoDTO);












                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                if (!token.isEmpty()) {
                    ApiPessoa apiPessoa = Retrofit.REGISTER_PESSOA();

                    Log.e("","pessoadto ="+ pessoaDTO);


                    Call<PessoaDTO> call = apiPessoa.REGISTER_PESSOA("Bearer " + token, pessoaDTO);
                    call.enqueue(new Callback<PessoaDTO>() {
                        @Override
                        public void onResponse(Call<PessoaDTO> call, Response<PessoaDTO> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(getContext(), "Salvo com sucesso!.", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();

                                Log.e("","Message ="+ response.code());
                                Log.e("","Body ="+ response.body());
                                Log.e("","ErroBody ="+ response.errorBody());
                                Log.e("","response ="+ response);

                            }
                        }

                        @Override
                        public void onFailure(Call<PessoaDTO> call, Throwable t) {

                            Toast.makeText(getContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        binding.edtNomePessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setNomeRazao(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtApelidoPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setApelidoFantasia(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtCpfPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setCpfCnpj(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtEmailPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setEmail(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtTelefonePessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setTelefone(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtCelularPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCelular(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setCelular(s.toString());

            }
        });

        binding.ckClientePessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setTipo(TipoPessoaEnum.A);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.switchAtivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setStatus(binding.switchAtivo.isChecked());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void validateField(EditText editText) {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
            editText.setBackground(errorBackground);
            editText.setError("Campo obrigatório");

        } else if (text.length() <= 11) {

            boolean isCpfValid = isValidCpf(text);

            if (!isCpfValid) {
                Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
                editText.setBackground(errorBackground);
                editText.setError("CPF inválido");

            } else {
                Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
                editText.setBackground(validBackground);
                editText.setError(null);
            }

        } else if (text.length() == 13) {

            boolean isCnpjValid = isValidCnpj(text);

            if (!isCnpjValid) {
                Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
                editText.setBackground(errorBackground);
                editText.setError("CNPJ inválido");

            } else {
                Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
                editText.setBackground(validBackground);
                editText.setError(null);

            }

        } else {

            Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
            editText.setBackground(errorBackground);
            editText.setError("Cnpj inválido");

        }
    }

    private boolean isValidCpf(String cpf) {

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        } else {

        }
        return true;

    }

    private boolean isValidCnpj(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        } else {

        }
        return true;

    }

    private TextWatcher insertMascaraCpfCnpj(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String s = charSequence.toString().replaceAll("[^\\d]", "");

                String mascara = "";


                if (pessoFisicaJuridica == FisicaJuridicaEnum.F) {
                    mascara = "###.###.###-##";
                    limiteCaracteres = 11;
                } else if (pessoFisicaJuridica == FisicaJuridicaEnum.J) {
                    mascara = "##.###.###/####-##";
                    limiteCaracteres = 14;
                }

                String mascaraAtual = "";
                if (isUpdating) {
                    mascaraAtual = s;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mascara.toCharArray()) {
                    if (m != '#' && i < s.length()) {
                        mascaraAtual += m;
                        continue;
                    }

                    try {
                        mascaraAtual += s.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;


                if (s.length() > limiteCaracteres) {
                    editText.setText(s.substring(0, limiteCaracteres));
                    editText.setSelection(limiteCaracteres);
                } else {
                    editText.setText(mascaraAtual);
                    editText.setSelection(mascaraAtual.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }


    private TextWatcher insertMascaraCelular(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String s = charSequence.toString().replaceAll("[^\\d]", "");

                String mascaraNum = "(##) #####-####";

                String mascaraAtual = "";
                if (isUpdating) {
                    mascaraAtual = s;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mascaraNum.toCharArray()) {
                    if (m != '#' && s.length() > mascaraAtual.length()) {
                        mascaraAtual += m;
                        continue;
                    }
                    try {
                        mascaraAtual += s.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                editText.setText(mascaraAtual);
                editText.setSelection(mascaraAtual.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }


}
