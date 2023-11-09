package br.com.securityprofit.ui.relatorio;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.databinding.FragmentRelarotioBinding;
import br.com.securityprofit.report.PdfReportGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatorioFragment extends Fragment {
    private FragmentRelarotioBinding binding;
    private SimpleDateFormat dateFormatter;
    private Long pessoaId;
    private String pessoaNome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRelarotioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        buscarPessoasDestino(binding.autoBuscaDestino);
        buscarPessoasOrigem(binding.autoBuscaOrigem);
        buscarAgente(binding.autoBuscaAgente);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        binding.buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }
        });

        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }
        });


       binding.btnGeraRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Caminho onde o PDF será salvo
                    String pdfFilePath = getContext().getExternalFilesDir(null) + "/relatorio.pdf";

                    // Chama a função para gerar o PDF
                    PdfReportGenerator.generatePdfReport(pdfFilePath);

                    // Abre o PDF após a geração
                    abrirPdf(pdfFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);
                        String selectedDate = dateFormatter.format(selectedCalendar.getTime());
                        binding.editTextDate.setText(selectedDate);

                    }
                },

                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)

        );

        datePickerDialog.show();

    }

    private void buscarPessoasOrigem(AutoCompleteTextView autoCompleteTextView) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            ApiPessoa apiPessoa = Retrofit.GET_ALL_PESSOA();
            Call<List<Pessoa>> call = apiPessoa.GET_ALL_PESSOA("Bearer " + token);

            call.enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {

                        List<Pessoa> pessoaList = response.body();
                        List<String> sugestoesPessoas = new ArrayList<>();

                        for (Pessoa pessoa : pessoaList) {

                            sugestoesPessoas.add(pessoa.getNomeRazao());
                            pessoaId = pessoa.getId();

                        }

                        binding.autoBuscaOrigem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                pessoaId = pessoaSelecionada.getId();
                                pessoaNome = pessoaSelecionada.getNomeRazao();

                            }
                        });

                        ArrayAdapter<String> sugestoesAdapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_dropdown_item_1line, sugestoesPessoas);

                        autoCompleteTextView.setAdapter(sugestoesAdapter);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                Long idPessoa = pessoaSelecionada.getId();

                            }
                        });
                    } else {

                        Toast.makeText(getContext(), "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void buscarPessoasDestino(AutoCompleteTextView autoCompleteTextView) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiPessoa apiPessoa = Retrofit.GET_ALL_PESSOA();
            Call<List<Pessoa>> call = apiPessoa.GET_ALL_PESSOA("Bearer " + token);

            call.enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {

                        List<Pessoa> pessoaList = response.body();
                        List<String> sugestoesPessoas = new ArrayList<>();

                        for (Pessoa pessoa : pessoaList) {

                            sugestoesPessoas.add(pessoa.getNomeRazao());
                            pessoaId = pessoa.getId();

                        }

                        binding.autoBuscaDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                pessoaId = pessoaSelecionada.getId();
                                pessoaNome = pessoaSelecionada.getNomeRazao();

                            }
                        });

                        ArrayAdapter<String> sugestoesAdapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_dropdown_item_1line, sugestoesPessoas);

                        autoCompleteTextView.setAdapter(sugestoesAdapter);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                Long idPessoa = pessoaSelecionada.getId();

                            }
                        });
                    } else {

                        Toast.makeText(getContext(), "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void buscarAgente(AutoCompleteTextView autoCompleteTextView) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiPessoa apiPessoa = Retrofit.GET_ALL_PESSOA();
            Call<List<Pessoa>> call = apiPessoa.GET_ALL_PESSOA("Bearer " + token);

            call.enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {

                        List<Pessoa> pessoaList = response.body();
                        List<String> sugestoesPessoas = new ArrayList<>();

                        for (Pessoa pessoa : pessoaList) {

                            sugestoesPessoas.add(pessoa.getNomeRazao());
                            pessoaId = pessoa.getId();

                        }

                        binding.autoBuscaAgente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                pessoaId = pessoaSelecionada.getId();
                                pessoaNome = pessoaSelecionada.getNomeRazao();

                            }
                        });

                        ArrayAdapter<String> sugestoesAdapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_dropdown_item_1line, sugestoesPessoas);

                        autoCompleteTextView.setAdapter(sugestoesAdapter);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                Long idPessoa = pessoaSelecionada.getId();

                            }
                        });
                    } else {

                        Toast.makeText(getContext(), "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });



        }
    }

    private void abrirPdf(String filePath) {
        File pdfFile = new File(filePath);
        Uri pdfUri = FileProvider.getUriForFile(requireContext(), "br.com.securityprofit.fileprovider", pdfFile);

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(pdfUri, "application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Nenhum aplicativo para abrir PDF encontrado.", Toast.LENGTH_SHORT).show();
        }
    }


}
