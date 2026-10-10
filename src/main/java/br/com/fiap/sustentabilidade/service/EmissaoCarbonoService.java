package br.com.fiap.sustentabilidade.service;

import br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoRequest;
import br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoResponse;
import br.com.fiap.sustentabilidade.exception.EmpresaNaoEncontradaException;
import br.com.fiap.sustentabilidade.model.EmissaoCarbono;
import br.com.fiap.sustentabilidade.model.Empresa;
import br.com.fiap.sustentabilidade.repository.EmissaoCarbonoRepository;
import br.com.fiap.sustentabilidade.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoResponse.toResponse;

@Service
@RequiredArgsConstructor
public class EmissaoCarbonoService {

    private final EmissaoCarbonoRepository emissaoCarbonoRepository;
    private final EmpresaRepository empresaRepository;


    public List<EmissaoCarbonoResponse> listar() {
        return emissaoCarbonoRepository.findAll()
                .stream()
                .map(EmissaoCarbonoResponse::toResponse)
                .toList();
    }

    public EmissaoCarbonoResponse cadastrar(EmissaoCarbonoRequest request) {
        Empresa empresa = empresaRepository.findById(request.idEmpresa())
                .orElseThrow(() -> new EmpresaNaoEncontradaException(request.idEmpresa()));

        validarEmpresaDaEmissao(empresa);

        EmissaoCarbono emissao = new EmissaoCarbono();
        emissao.setEmpresa(empresa);
        emissao.setFonteEmissao(request.fonteEmissao());
        emissao.setDataRegistro(request.dataRegistro());
        emissao.setQuantidadeCo2(request.quantidadeCo2());

        EmissaoCarbono emissaoSalva = emissaoCarbonoRepository.save(emissao);

        return toResponse(emissaoSalva);

    }

    private void validarEmpresaDaEmissao(Empresa empresa) {

        Empresa empresaLogada = obterEmpresaLogada();

        if (!empresaLogada.getId().equals(empresa.getId())) {
            throw new RuntimeException(
                    "Você não pode cadastrar emissão para outra empresa."
            );
        }
    }

    private Empresa obterEmpresaLogada() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Empresa) authentication.getPrincipal();
    }

}
