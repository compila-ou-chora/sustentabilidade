package br.com.fiap.sustentabilidade.service;

import br.com.fiap.sustentabilidade.dto.empresa.EmpresaRequest;
import br.com.fiap.sustentabilidade.dto.empresa.EmpresaResponse;
import br.com.fiap.sustentabilidade.exception.EmpresaNaoEncontradaException;
import br.com.fiap.sustentabilidade.model.Empresa;
import br.com.fiap.sustentabilidade.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<EmpresaResponse> listar() {

        return empresaRepository.findAll()
                .stream()
                .map(EmpresaResponse::toResponse)
                .toList();
    }

    public EmpresaResponse cadastrar(EmpresaRequest request) {

        String senhaCriptografada =
                new BCryptPasswordEncoder().encode(request.senha());

        Empresa empresa = new Empresa();

        empresa.setNomeEmpresa(request.nomeEmpresa());
        empresa.setNomeCidade(request.nomeCidade());
        empresa.setSiglaEstado(request.siglaEstado());
        empresa.setEmail(request.email());
        empresa.setSenha(senhaCriptografada);

        Empresa empresaSalva = empresaRepository.save(empresa);

        return EmpresaResponse.toResponse(empresaSalva);
    }


    public EmpresaResponse buscarPorId(Long id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() ->
                        new EmpresaNaoEncontradaException(id)
                );

        return EmpresaResponse.toResponse(empresa);
    }
}
