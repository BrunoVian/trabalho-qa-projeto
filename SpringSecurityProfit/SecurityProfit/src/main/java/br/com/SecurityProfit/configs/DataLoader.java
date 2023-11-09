package br.com.SecurityProfit.configs;

import br.com.SecurityProfit.controllers.AuthenticationController;
import br.com.SecurityProfit.controllers.UsuarioController;
import br.com.SecurityProfit.models.escolta.Empresa;
import br.com.SecurityProfit.models.pessoa.FisicaJuridicaEnum;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.pessoa.TipoPessoaEnum;
import br.com.SecurityProfit.models.user.RegisterDTO;
import br.com.SecurityProfit.models.user.Usuario;
import br.com.SecurityProfit.models.user.UsuarioRole;
import br.com.SecurityProfit.repositories.EmpresaRepository;
import br.com.SecurityProfit.repositories.PessoaRepository;
import br.com.SecurityProfit.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(pessoaRepository.count() == 0) {
            Pessoa pessoa = new Pessoa();
            pessoa.setFisicaJurica(FisicaJuridicaEnum.J);
            pessoa.setNomeRazao("Administrador");
            pessoa.setTipo(TipoPessoaEnum.F);
            pessoa.setEmail("email@mail.com");
            pessoaRepository.saveAndFlush(pessoa);
        }

        if(usuarioRepository.count() == 0){
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setLogin("teste@unipar.com");
            registerDTO.setRole(UsuarioRole.ADMIN);
            registerDTO.setStatus(true);
            registerDTO.setPessoaId(Long.parseLong("1"));
            registerDTO.setSenha("1");
            registerDTO.setRepitaSenha("1");


            authenticationController.register(registerDTO);
        }


        if(empresaRepository.count() == 0){
            Empresa empresa = new Empresa();
            empresa.setRazaoSocial("Nova Empresa");
            empresa.setNomeFantasia("Nome Fantasia");
            empresa.setCnpj("00000000000000");
            empresaRepository.saveAndFlush(empresa);
        }


    }
}
